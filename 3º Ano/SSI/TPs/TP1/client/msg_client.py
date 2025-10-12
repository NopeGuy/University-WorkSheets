import argparse
import asyncio
import os
from cryptography import x509
from cryptography.hazmat.backends import default_backend
from cryptography.hazmat.primitives import hashes
from cryptography.hazmat.primitives.serialization import pkcs12
from cryptography.hazmat.primitives import serialization
from cryptography.hazmat.primitives.asymmetric import padding
from cryptography.exceptions import InvalidSignature
from cryptography.hazmat.primitives.asymmetric import rsa
from cryptography.hazmat.primitives.ciphers import Cipher, algorithms, modes
from datetime import datetime

conn_port = 7777
max_msg_size = 9999

def gerar_chave_aes(tamanho_chave=256):
    chave_aes = os.urandom(tamanho_chave // 8)  # Gera uma chave AES aleatória
    return chave_aes

def aes_crypto(chave_aes, chave_publica):
    chave_aes_criptografada = chave_publica.encrypt(
        chave_aes,
        padding.OAEP(
            mgf=padding.MGF1(algorithm=hashes.SHA256()),
            algorithm=hashes.SHA256(),
            label=None
        )
    )
    return chave_aes_criptografada

def aes_decrypto(chave_aes_criptografada, chave_privada):
    chave_aes = chave_privada.decrypt(
        chave_aes_criptografada,
        padding.OAEP(
            mgf=padding.MGF1(algorithm=hashes.SHA256()),
            algorithm=hashes.SHA256(),
            label=None
        )
    )
    return chave_aes

def aes_encrypt(data, aes_key):
    iv = os.urandom(16)  # Generate a random IV
    cipher = Cipher(algorithms.AES(aes_key), modes.CBC(iv), backend=default_backend())
    encryptor = cipher.encryptor()
    # Ensure the length of the data is a multiple of the block length
    if len(data) % 16 != 0:
        # Pad the data to make its length a multiple of the block length
        data += b'\0' * (16 - len(data) % 16)
    ciphertext = encryptor.update(data) + encryptor.finalize()
    return iv + ciphertext

def rsa_encrypt(message, public_key):
    ciphertext = public_key.encrypt(
        message,
        padding.OAEP(
            mgf=padding.MGF1(algorithm=hashes.SHA256()),
            algorithm=hashes.SHA256(),
            label=None
        )
    )
    return ciphertext

def rsa_decrypt(ciphertext, private_key):
    plaintext = private_key.decrypt(
        ciphertext,
        padding.OAEP(
            mgf=padding.MGF1(algorithm=hashes.SHA256()),
            algorithm=hashes.SHA256(),
            label=None
        )
    )
    return plaintext

def aes_decrypt(ciphertext, key):
    iv = b'\x00' * 16  # Initialization vector
    cipher = Cipher(algorithms.AES(key), modes.CFB(iv), backend=default_backend())
    decryptor = cipher.decryptor()
    plaintext = decryptor.update(ciphertext) + decryptor.finalize()
    return plaintext

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
    
def aes_encrypt(message, key):
    iv = b'\x00' * 16  # Initialization vector
    cipher = Cipher(algorithms.AES(key), modes.CFB(iv), backend=default_backend())
    encryptor = cipher.encryptor()
    ciphertext = encryptor.update(message) + encryptor.finalize()
    return ciphertext

class Client:
    """ Classe que implementa a funcionalidade de um CLIENTE. """
    def __init__(self, sckt=None, private_key=None, user_cert=None, ca_cert=None, uid=None):
        """ Construtor da classe. """
        self.uid = uid
        self.sckt = sckt
        self.msg_cnt = 0
        self.private_key = private_key
        self.user_cert= user_cert
        self.ca_cert = ca_cert
        self.public_key = private_key.public_key()
        self.fernet = None 
        self.peer_public_key = None
        self.aes_key = gerar_chave_aes()

    def get_userdata(p12_fname):
        with open(p12_fname, "rb") as f:
            p12 = f.read()
        password = None 
        (private_key, user_cert, [ca_cert]) = pkcs12.load_key_and_certificates(p12, password)
        return (private_key, user_cert, ca_cert)

    async def send_certificate(self, writer):
        cert_pem = self.user_cert.public_bytes(encoding=serialization.Encoding.PEM)
        writer.write( cert_pem + b'\n' )
        await writer.drain()

    async def ler_linhas(self, reader):
        linhas = ""
        while True:
            linha = await reader.readline()
            # Verifica se a linha está vazia (fim do stream)
            if len(linha)==0 or linha == b'\n':
                break
            # Decodifica a linha (se necessário) e adiciona à lista de linhas
            #linhas.append(linha.decode('utf-8').rstrip('\n'))
            #linhas.append(linha)
            linhas = linhas + linha.decode("utf-8")
        return linhas.encode()

    async def receive_certificate(self, reader):
        #str_public_key = await self.ler_linhas(reader)
        #public_key = load_pem_public_key( str_public_key, backend=default_backend())
        client_cert_pem = await self.ler_linhas(reader)
        self.peer_certificate = x509.load_pem_x509_certificate(client_cert_pem, default_backend())

    def process(self, msg=b""):
        """ Processa uma mensagem (`bytestring`) enviada pelo SERVIDOR.
            Retorna a mensagem a transmitir como resposta (`None` para
            finalizar ligação) """
        if msg:
            try:
                message = msg.decode()
                split_msg = message.split('|')
                print(" ")
                for i in split_msg:
                    print(i)
            except Exception as e:
                print(f"Error: {e}")
        
        while True:
            print('\nEmpty to finish or "help" for instructions:', end=' ', flush=True)
            new_msg = input().encode()

            if b'help' in new_msg.lower():
                print("\n_____________________________________________________________________________________________________________________")
                print("Instructions for using the program:\n\n")
                print("• `user <FNAME>` - optional argument (which should always come first) specifying the file with user data. By default, it will be assumed that this file is `userdata.p12`.\n")
                print("• `send <UID> <SUBJECT>` - sends a message with subject `<SUBJECT>` to the user with identifier `<UID>`. The message content will be read from stdin, and the size should be limited to 1000 bytes.\n")
                print("• `askqueue` - requests the server to send you the list of unread messages from the user's queue. For each message in the queue, a line is returned containing: `<NUM>:<SENDER>:<TIME>:<SUBJECT>`, where `<NUM>` is the order number of the message in the queue and `<TIME>` is a timestamp added by the server that records when the message was received.\n")
                print("• `getmsg <NUM>` - requests the server to send the message from your queue with number `<NUM>`. In case of success, the message will be printed on stdout. Once sent, this message will be marked as read, so it will not be listed in the next `askqueue` command (but it can be requested again by the client).\n")
                print("• `help` - prints usage instructions of the program.\n")
                print("_____________________________________________________________________________________________________________________\n")
            elif b'send' in new_msg.lower() and len(new_msg.split()) >= 3:
                new_msg_parts = new_msg.split()
                uid = new_msg_parts[1]
                subject = new_msg_parts[2]
                msg_body = input(f"\nInput message to send to {uid} with subject {subject} (max 1000 bytes): ").encode()
                aes_key = os.urandom(32)
                encrypted_message = aes_encrypt(msg_body, aes_key)
                encrypted_aes_key = rsa_encrypt(aes_key, self.public_key)
                if len(msg_body) > 1000:
                    print("Error: message too long. Max 1000 bytes.")
                    continue
                new_msg = b'send;' + uid + b';' + subject + b';' + encrypted_message + b';' + encrypted_aes_key
                self.msg_cnt += 1
                break
            elif b'askqueue' in new_msg.lower():
                new_msg = b'askqueue'
                break
            elif b'getmsg' in new_msg.lower() and len(new_msg.split()) == 2:
                new_msg_parts = new_msg.split()
                uid = new_msg_parts[1]
                encrypted_message = new_msg_parts[3]  
                encrypted_key = new_msg_parts[4]    
                
                decrypted_aes_key = rsa_decrypt(encrypted_key, self.private_key) 
                
                decrypted_message = aes_decrypt(encrypted_message, decrypted_aes_key)
                
                print("Decrypted message:", decrypted_message.decode()) 

            elif b'' == new_msg:
                print("Closing connection...")
                return None
            else:
                print("Error: invalid command. Type 'help' for instructions.")
        
        
        return new_msg if len(new_msg) > 0 else None


async def tcp_echo_client(private_key, user_cert, ca_cert, uid):
    response = validate_certificate(user_cert, ca_cert)
    if response == True:
        reader, writer = await asyncio.open_connection('127.0.0.1', conn_port)
        addr = writer.get_extra_info('peername')
        client = Client(addr, private_key, user_cert, ca_cert, uid)
        await client.send_certificate(writer)
        await client.receive_certificate(reader)
        # Validar certificado...
        certResult = validate_certificate(client.peer_certificate, ca_cert)
        #Validar se o certificado é válido
        if not certResult:
            raise ValueError("invalid certificate received from server")

        print("Certificate accepted")
        # Enviar o UID como a primeira mensagem
        writer.write(uid.encode() + b'\n')
        await writer.drain()

        msg = client.process()
        while msg:
            writer.write(msg)
            msg = await reader.read(max_msg_size)
            if msg:
                msg = client.process(msg)
            else:
                break
        writer.write(b'\n')
        print('Socket closed!')
        writer.close()
    else:
        print("Certificado rejeitado")
        writer.close()
        await writer.wait_closed()
        return


def run_client():
    parser = argparse.ArgumentParser(description="TCP Echo Client")
    parser.add_argument('-user', metavar='<FNAME>', type=str, help="Specify the file with user data.")
    args = parser.parse_args()

    if args.user:
        p12_filename = args.user
    else:
        p12_filename = './MSG_CLI1.p12'
    
    if os.path.exists(p12_filename):
        private_key, user_cert, ca_cert = Client.get_userdata(p12_filename)
        uid = user_cert.subject.get_attributes_for_oid(x509.OID_PSEUDONYM)[0].value
        print(f"\nConnected to server with uid: {uid}")
    else:
        print("Error: 'MSG_CLI1.p12' file not found.")
        return 
    
    asyncio.run(tcp_echo_client(private_key, user_cert, ca_cert, uid))


run_client()