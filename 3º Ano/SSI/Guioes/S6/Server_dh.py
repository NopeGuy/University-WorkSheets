import asyncio
import os
from cryptography.fernet import Fernet
from cryptography.hazmat.backends import default_backend
from cryptography.hazmat.primitives import hashes
from cryptography.hazmat.primitives.kdf.pbkdf2 import PBKDF2HMAC
import base64

conn_cnt = 0
conn_port = 7777
max_msg_size = 9999

class ServerWorker(object):
    """ Classe que implementa a funcionalidade do SERVIDOR. """
    def __init__(self, cnt, addr=None):
        """ Construtor da classe. """
        self.id = cnt
        self.addr = addr
        self.msg_cnt = 0
        self.p = "0xFFFFFFFFFFFFFFFFC90FDAA22168C234C4C6628B80DC1CD129024E088A67CC74020BBEA63B139B22514A08798E3404DDEF9519B3CD3A431B302B0A6DF25F14374FE1356D6D51C245E485B576625E7EC6F44C42E9A637ED6B0BFF5CB6F406B7EDEE386BFB5A899FA5AE9F24117C4B1FE649286651ECE45B3DC2007CB8A163BF0598DA48361C55D39A69163FA8FD24CF5F83655D23DCA3AD961C62F356208552BB9ED529077096966D670C354E4ABC9804F1746C08CA18217C32905E462E36CE3BE39E772C180E86039B2783A2EC07A28FB5C55DF06F4C52C9DE2BCBF6955817183995497CEA956AE515D2261898FA051015728E5A8AACAA68FFFFFFFFFFFFFFFF"
        self.g = 2
        self.private_key = int.from_bytes(os.urandom(256), byteorder='big')
        self.public_key = pow(self.g, self.private_key, int(self.p, 0))
        self.shared_key = None
        self.fernet = None  # Fernet key will be set after deriving shared_key

    async def receive_public_key(self, reader):
        public_key = int(await reader.readline())
        return public_key

    async def send_public_key(self, writer):
        writer.write(str(self.public_key).encode() + b"\n")
        await writer.drain()

    async def derive_shared_key(self, reader, writer):
        peer_public_key = await self.receive_public_key(reader)
        self.shared_key = pow(peer_public_key, self.private_key, int(self.p, 0))
        # Derive Fernet key from shared key
        kdf = PBKDF2HMAC(algorithm=hashes.SHA256(), length=32, salt=b'', iterations=100000, backend=default_backend())
        self.fernet = Fernet(base64.urlsafe_b64encode(kdf.derive(str(self.shared_key).encode())))
        await writer.drain()

    def process(self, msg):
        """ Processa uma mensagem (`bytestring`) enviada pelo CLIENTE.
            Retorna a mensagem a transmitir como resposta (`None` para finalizar ligação) """
        self.msg_cnt += 1
        decrypted_msg = self.fernet.decrypt(msg)  # Decrypt incoming message
        txt = decrypted_msg.decode()
        print('%d : %r' % (self.id, txt))
        response = "Processed: " + txt.upper()  # Example processing
        encrypted_response = self.fernet.encrypt(response.encode())  # Encrypt the response
        return encrypted_response if len(encrypted_response) > 0 else None

async def handle_echo(reader, writer):
    global conn_cnt
    conn_cnt +=1
    addr = writer.get_extra_info('peername')
    srvwrk = ServerWorker(conn_cnt, addr)
    
    await srvwrk.send_public_key(writer)
    await srvwrk.derive_shared_key(reader, writer)

    data = await reader.read(max_msg_size)
    while True:
        if not data: continue
        if data[:1]==b'\n': break
        data = srvwrk.process(data)
        if not data: break
        writer.write(data)
        await writer.drain()
        data = await reader.read(max_msg_size)
    print("[%d]" % srvwrk.id)
    writer.close()

def run_server():
    loop = asyncio.new_event_loop()
    coro = asyncio.start_server(handle_echo, '127.0.0.1', conn_port)
    server = loop.run_until_complete(coro)
    # Serve requests until Ctrl+C is pressed
    print('Serving on {}'.format(server.sockets[0].getsockname()))
    print('  (press enter with nothing to finish)\n')
    try:
        loop.run_forever()
    except KeyboardInterrupt:
        pass
    # Close the server
    server.close()
    loop.run_until_complete(server.wait_closed())
    loop.close()
    print('\nFINISHED!')

run_server()