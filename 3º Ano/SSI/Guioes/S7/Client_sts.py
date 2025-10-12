import asyncio
import os
import base64
from cryptography.fernet import Fernet
from cryptography.hazmat.backends import default_backend
from cryptography.hazmat.primitives import hashes
from cryptography.hazmat.primitives.kdf.pbkdf2 import PBKDF2HMAC
from cryptography.hazmat.primitives import serialization
from cryptography.hazmat.primitives.asymmetric import padding
from cryptography.hazmat.primitives import hashes
from cryptography.x509 import load_pem_x509_certificate


conn_port = 7777
max_msg_size = 9999

class Client:
    """ Classe que implementa a funcionalidade de um CLIENTE. """
    def __init__(self, sckt=None):
        """ Construtor da classe. """
        self.sckt = sckt
        self.msg_cnt = 0
        self.p = "0xFFFFFFFFFFFFFFFFC90FDAA22168C234C4C6628B80DC1CD129024E088A67CC74020BBEA63B139B22514A08798E3404DDEF9519B3CD3A431B302B0A6DF25F14374FE1356D6D51C245E485B576625E7EC6F44C42E9A637ED6B0BFF5CB6F406B7EDEE386BFB5A899FA5AE9F24117C4B1FE649286651ECE45B3DC2007CB8A163BF0598DA48361C55D39A69163FA8FD24CF5F83655D23DCA3AD961C62F356208552BB9ED529077096966D670C354E4ABC9804F1746C08CA18217C32905E462E36CE3BE39E772C180E86039B2783A2EC07A28FB5C55DF06F4C52C9DE2BCBF6955817183995497CEA956AE515D2261898FA051015728E5A8AACAA68FFFFFFFFFFFFFFFF"
        self.g = 2
        self.private_key = int.from_bytes(os.urandom(256), byteorder='big')
        self.public_key = pow(self.g, self.private_key, int(self.p, 0))
        self.shared_key = None
        self.fernet = None  
        self.private_key_rsa = None
        self.public_key_rsa = None
        self.load_rsa_keys()

    def load_rsa_keys(self):
        with open('MSG_CLI1.key', 'rb') as key_file:
            self.private_key_rsa = serialization.load_pem_private_key(
                key_file.read(),
                password=b'1234',
                backend=default_backend()
            )

        with open('MSG_CLI1.crt', 'rb') as cert_file:
            cert_data = cert_file.read()
            cert = load_pem_x509_certificate(cert_data, default_backend())
            self.public_key_rsa = cert.public_key()

    def sign_message(self, message):
        signature = self.private_key_rsa.sign(
            message,
            padding.PSS(
                mgf=padding.MGF1(hashes.SHA256()),
                salt_length=padding.PSS.MAX_LENGTH
            ),
            hashes.SHA256()
        )
        return signature

    def verify_signature(self, message, signature):
        try:
            self.public_key_rsa.verify(
                signature,
                message,
                padding.PSS(
                    mgf=padding.MGF1(hashes.SHA256()),
                    salt_length=padding.PSS.MAX_LENGTH
                ),
                hashes.SHA256()
            )
            return True
        except:
            return False

    async def receive_and_verify_message(self, reader):
        message_length_bytes = await reader.readexactly(4)
        message_length = int.from_bytes(message_length_bytes, byteorder='big')
        message = await reader.readexactly(message_length)
        signature = await reader.readexactly(256) 
        if self.verify_signature(message, signature):
            return message
        else:
            return b"Invalid Signature"

    async def send_public_key(self, writer):
        writer.write(str(self.public_key).encode() + b"\n")
        await writer.drain()

    async def receive_public_key(self, reader):
        public_key = int(await reader.readline())
        return public_key

    async def derive_shared_key(self, reader, writer):
        peer_public_key = await self.receive_public_key(reader)
        self.shared_key = pow(peer_public_key, self.private_key, int(self.p, 0))
        kdf = PBKDF2HMAC(algorithm=hashes.SHA256(), length=32, salt=b'', iterations=100000, backend=default_backend())
        self.fernet = Fernet(base64.urlsafe_b64encode(kdf.derive(str(self.shared_key).encode())))
        await writer.drain()

    def process(self, msg=b""):
        """ Processa uma mensagem (`bytestring`) enviada pelo SERVIDOR.
            Retorna a mensagem a transmitir como resposta (`None` para
            finalizar ligação) """
        self.msg_cnt +=1
        if msg:
            decrypted_msg = self.fernet.decrypt(msg)
            print('Received (%d): %r' % (self.msg_cnt, decrypted_msg.decode()))
        
        print('Input message to send (empty to finish)')
        new_msg = input().encode()
        encrypted_msg = self.fernet.encrypt(new_msg) 
        
        return encrypted_msg if len(new_msg) > 0 else None


async def tcp_echo_client():
    reader, writer = await asyncio.open_connection('127.0.0.1', conn_port)
    addr = writer.get_extra_info('peername')
    client = Client(addr)
    await client.send_public_key(writer)
    await client.derive_shared_key(reader, writer)
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

def run_client():
    loop = asyncio.get_event_loop()
    loop.run_until_complete(tcp_echo_client())

run_client()
