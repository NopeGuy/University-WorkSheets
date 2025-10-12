import asyncio
import os
from cryptography.hazmat.backends import default_backend
from cryptography.hazmat.primitives import hashes
from cryptography.hazmat.primitives.serialization import pkcs12
from cryptography.hazmat.primitives import serialization
from cryptography.hazmat.primitives.asymmetric import padding
from functools import partial
from cryptography.exceptions import InvalidSignature
from cryptography.hazmat.backends import default_backend
from cryptography import x509
from datetime import datetime

conn_cnt = 0
conn_port = 7777
max_msg_size = 9999
listcounter = 1

messages = []

def validate_certificate(certificate, ca_certificate):
    public_key = ca_certificate.public_key()
    try:
        # Verifica a assinatura do certificado
        signature = certificate.signature
        tbs_certificate_bytes = certificate.tbs_certificate_bytes
        ca_public_key = ca_certificate.public_key()
        ca_public_key.verify(
            signature,
            tbs_certificate_bytes,
            padding.PKCS1v15(),  # Padding comum para assinaturas RSA
            certificate.signature_hash_algorithm  # O algoritmo de hash usado na assinatura
        )

        # Verifica a validade do certificado pela data
        current_time = datetime.utcnow()
        if current_time < certificate.not_valid_before or current_time > certificate.not_valid_after:
            print("O certificado está fora do período de validade.")
            return False

        return True
    except InvalidSignature:
        print("A assinatura do certificado é inválida.")
        return False
    except Exception as e:
        print(f"Ocorreu um erro durante a validação do certificado: {e}")
        return False

class ServerWorker(object):
    """ Classe que implementa a funcionalidade do SERVIDOR. """
    def __init__(self, cnt, addr=None, private_key=None, user_cert=None, ca_cert=None, uid=None):
        """ Construtor da classe. """
        self.id = cnt
        self.addr = addr
        self.msg_cnt = 0
        self.private_key = private_key
        self.user_cert = user_cert
        self.ca_cert = ca_cert
        self.uid = uid
        self.public_key = private_key.public_key()
        self.fernet = None  
        self.peer_certificate = None
        

    async def ler_linhas(self, reader):
        linhas = ""
        while True:
            linha = await reader.readline()
            # Verifica se a linha está vazia (fim do stream)
            if len(linha)== 0 or linha == b'\n':
                break
            # Decodifica a linha (se necessário) e adiciona à lista de linhas
            #linhas.append(linha.decode('utf-8').rstrip('\n'))
            #linhas.append(linha)
            linhas = linhas + linha.decode("utf-8")
        return linhas.encode()

    async def receive_certificate(self, reader):
        client_cert_pem = await self.ler_linhas(reader)
        self.peer_certificate = x509.load_pem_x509_certificate(client_cert_pem, default_backend())
    
    def get_userdata(p12_fname):
        with open(p12_fname, "rb") as f:
            p12 = f.read()
        password = None # p12 não está protegido...
        (private_key, user_cert, [ca_cert]) = pkcs12.load_key_and_certificates(p12, password)
        return (private_key, user_cert, ca_cert)

    async def send_certificate(self, writer):
        cert_pem = self.user_cert.public_bytes(encoding=serialization.Encoding.PEM)
        writer.write( cert_pem + b'\n' )
        await writer.drain()

    def process(self, msg):
        """ Processa uma mensagem (`bytestring`) enviada pelo CLIENTE.
            Retorna a mensagem a transmitir como resposta (`None` para finalizar ligação) """
        self.msg_cnt += 1
        txt = str(msg)
        txtlist = txt.split(";")
        typeOMessage = txtlist[0]
        textsender = self.uid
        current_time = datetime.now().strftime("%H:%M:%S %d-%m-%Y")
        if "send" in typeOMessage:
            textreceiver = txtlist[1]
            subject = txtlist[2]
            encrypted_body = txtlist[3]
            used_key = txtlist[4]
            print("Text receiver:" + textreceiver)
            print("Message type: " + typeOMessage)
            response = "Processed: " + txt.upper()  
            encoded_response = response.encode()

            if encoded_response:
                messages.insert(0, (typeOMessage, textreceiver, textsender, listcounter, current_time, subject, encrypted_body, used_key))
            return encoded_response
        elif "getmsg" in typeOMessage:
            print("Message type: " + typeOMessage)
            print(f'{self.uid}: {txt}')  
            response = "Processed: " + txt.upper()  
            encoded_response = response.encode()
            if encoded_response:
                messages.insert(0, (typeOMessage, self.uid, self.uid, "", 0, current_time, None, None, None))
            return encoded_response
        else:
            print("Message type: " + typeOMessage)
            print(f'{self.uid}: {txt}')
            response = "Processed: " + txt.upper()  
            encoded_response = response.encode()
            if encoded_response:
                messages.insert(0, (typeOMessage, self.uid, textsender, "", 0, current_time, None, None, None))
            return encoded_response


async def read_from_client(reader, srvwrk):
    while True:
        data = await reader.read(max_msg_size)
        if not data:
            break
        srvwrk.process(data)


async def write_to_client(writer, srvwrk):
    global listcounter
    while True:
        try:
            if messages and "askqueue" in messages[0][0] and messages[0][1] == srvwrk.uid:
                stringtosend = "History :"
                for msg in messages:
                    if "sent" in msg[0] and msg[1] == srvwrk.uid:
                        stringtosend = stringtosend + "|" + msg[2] + ":" + msg[5] + ":" + msg[4] + ":" + str(msg[3])
                data = stringtosend.encode()
                writer.write(data)
                await writer.drain()
                messages.pop(0)
            elif messages and "send" in messages[0][0] and messages[0][2] == srvwrk.uid:
                sent = b"Message Sent!"
                listcounter += 1
                messages.insert(0, ("sent", messages[0][1], messages[0][2], messages[0][3], messages[0][4], messages[0][5], messages[0][6], messages[0][7]))
                messages.pop(1)
                writer.write(sent)
                await writer.drain()
            elif messages and "getmsg" in messages[0][0] and messages[0][1] == srvwrk.uid:
                    msg_to_send = messages[0][6]
                    writer.write(msg_to_send)
                    await writer.drain()
                    messages.pop(0)
                    
        except Exception as e:
            print(f"Error sending message: {e}")
            writer.close()
            await writer.wait_closed()
            break

        await asyncio.sleep(0.01)



async def handle_echo(reader, writer, private_key, user_cert, ca_cert):
    global conn_cnt
    conn_cnt += 1
    addr = writer.get_extra_info('peername')
    srvwrk = ServerWorker(conn_cnt, addr, private_key, user_cert, ca_cert, uid=None)  # Initialize UID as None
    response = validate_certificate(user_cert, ca_cert)
    if response == True:
        #TODO: send_certificate (apenas com public key)
        await srvwrk.send_certificate(writer)
        await srvwrk.receive_certificate(reader)
        clientCertResult = validate_certificate(srvwrk.peer_certificate, ca_cert)
        #Validar se o certificado é válido
        if not clientCertResult:
            raise ValueError("invalid certificate received from client")

        print("Certificate accepted")
        # Aguardar a primeira mensagem com o UID
        uid_data = await reader.read(max_msg_size)
        srvwrk.uid = uid_data.decode().strip()  # Set UID for the server worker

        await asyncio.gather(
            read_from_client(reader, srvwrk),
            write_to_client(writer, srvwrk)
        )

        print(f'Client {srvwrk.uid} disconnected, closing worker {srvwrk.id}...')
        writer.close()
    else:
        print("Certificado rejeitado")
        writer.close()
        await writer.wait_closed()
        return


def run_server():
    p12_filename = 'MSG_SERVER.p12'
    if os.path.exists(p12_filename):
        private_key, user_cert, ca_cert = ServerWorker.get_userdata(p12_filename)
    else:
        print("Error: '.p12' file not found.")
        return 

    loop = asyncio.new_event_loop()
    asyncio.set_event_loop(loop)

    # Pré-configura a função handle_echo com os argumentos necessários
    handle_echo_with_certs = partial(handle_echo, private_key=private_key, user_cert=user_cert, ca_cert=ca_cert)

    coro = asyncio.start_server(handle_echo_with_certs, '127.0.0.1', conn_port)
    server = loop.run_until_complete(coro)
    print('Serving on {}'.format(server.sockets[0].getsockname()))

    try:
        loop.run_forever()
    except KeyboardInterrupt:
        pass
    finally:
        server.close()
        loop.run_until_complete(server.wait_closed())
        loop.close()
    print('\nFINISHED!')

run_server()