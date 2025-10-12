import asyncio
from cryptography.hazmat.primitives.ciphers.aead import AESGCM
import os

conn_cnt = 0
conn_port = 7777
max_msg_size = 9999
key = b"balasebolinhos"

class ServerWorker(object):
    """ Classe que implementa a funcionalidade do SERVIDOR. """
    def __init__(self, cnt, addr=None):
        """ Construtor da classe. """
        self.id = cnt
        self.tag = cnt
        self.addr = addr
        self.msg_cnt = 0

    def process(self, msg):
        """ Processa uma mensagem (`bytestring`) enviada pelo CLIENTE.
            Retorna a mensagem a transmitir como resposta (`None` para
            finalizar ligação) """
        self.msg_cnt += 1
        password = b"1234567890123456"
        nonce = msg[:12]
        encrypted_data = msg[12:]

        aesgcm = AESGCM(password)

        try:
            decrypted_msg = aesgcm.decrypt(nonce, encrypted_data, None)
            decrypted_txt = decrypted_msg.decode()
            print('[{}] : {}'.format(self.tag, decrypted_txt))  # Exibe a tag do cliente
            new_msg = decrypted_txt.upper().encode()
            
            return new_msg if len(new_msg) > 0 else None

        except Exception as e:
            print("Error during decryption:", type(e), e)
            return None

async def handle_echo(reader, writer):
    global conn_cnt
    conn_cnt +=1
    addr = writer.get_extra_info('peername')
    srvwrk = ServerWorker(conn_cnt, addr)
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
    print('Serving on {}'.format(server.sockets[0].getsockname()))
    print('  (type ^C to finish)\n')
    try:
        loop.run_forever()
    except KeyboardInterrupt:
        pass
    server.close()
    loop.run_until_complete(server.wait_closed())
    loop.close()
    print('\nFINISHED!')

run_server()
