import asyncio
import socket
from cryptography.hazmat.backends import default_backend
from cryptography.hazmat.primitives.ciphers.aead import AESGCM
import os

conn_port = 7777
max_msg_size = 9999

class Client:
    
    """ Classe que implementa a funcionalidade de um CLIENTE. """
    def __init__(self, sckt=None):
        """ Construtor da classe. """
        self.sckt = sckt
        self.msg_cnt = 0

    def process(self, msg=b""):
        """ Processa uma mensagem (`bytestring`) enviada pelo SERVIDOR.
            Retorna a mensagem a transmitir como resposta (`None` para
            finalizar ligação) """
        password = b"1234567890123456"
        self.msg_cnt +=1
        print("Mensagem do servidor: " + msg.decode())
        print('\nDê input da mensagem (Vazia para acabar):')
        plaintext = input().encode()
        nonce = os.urandom(12)
        aesgcm = AESGCM(password)
        new_msg = aesgcm.encrypt(nonce, plaintext, None)
        new_msg = nonce + new_msg
        return new_msg if len(new_msg)>0 else None
#
#
# Funcionalidade Cliente/Servidor
#
# obs: não deverá ser necessário alterar o que se segue
#

async def tcp_echo_client():
    reader, writer = await asyncio.open_connection('127.0.0.1', conn_port)
    addr = writer.get_extra_info('peername')
    client = Client(addr)
    msg = client.process()
    while msg:
        writer.write(msg)
        msg = await reader.read(max_msg_size)
        if msg :
            msg = client.process(msg)
        else:
            break
    writer.write(b'\n')
    print('Socket closed!')
    writer.close()

def run_client():
    loop = asyncio.get_event_loop()
    loop.run_until_complete(tcp_echo_client())


run_client()