import os
import sys
import struct
from cryptography.hazmat.primitives.ciphers import Cipher, algorithms, modes

def setup(fkey):
    key = os.urandom(32)
    with open(fkey + '.key', 'wb') as key_file:
        key_file.write(key)

def process_file(operation, fich, key):
    if operation.lower() == "enc":
        with open(fich, 'rb') as file:
            plaintext = file.read()

        nonce = os.urandom(8)
        counter = 0
        full_nonce = struct.pack("<Q", counter) + nonce

        algorithm = algorithms.ChaCha20(key, full_nonce)
        cipher = Cipher(algorithm, mode=None)
        encryptor = cipher.encryptor()

        ciphertext = encryptor.update(plaintext)

        output_file = fich + '.enc'
        with open(output_file, 'wb') as encrypted_file:
            encrypted_file.write(nonce)
            encrypted_file.write(ciphertext)

    elif operation.lower() == "dec":
        with open(fich, 'rb') as encrypted_file:
            nonce = encrypted_file.read(8)
            ciphertext = encrypted_file.read()

        full_nonce = struct.pack("<Q", 0) + nonce

        algorithm = algorithms.ChaCha20(key, full_nonce)
        cipher = Cipher(algorithm, mode=None)
        decryptor = cipher.decryptor()

        plaintext = decryptor.update(ciphertext)

        output_file = fich + '.dec'
        with open(output_file, 'wb') as decrypted_file:
            decrypted_file.write(plaintext)

    else:
        print(f"Operação desconhecida: {operation}")
        sys.exit(1)

if __name__ == "__main__":
    if len(sys.argv) < 3:
        print("Uso: python3 cfich_chacha20.py <operação> <ficheiro> [<ficheiro_chave>]")
        sys.exit(1)

    operacao = sys.argv[1]
    ficheiro = sys.argv[2]

    if operacao.lower() == "setup":
        if len(sys.argv) != 3:
            print("Uso: python3 cfich_chacha20.py setup <chave>")
            sys.exit(1)
        ficheiro_chave = sys.argv[2]
        setup(ficheiro_chave)

    else:
        if len(sys.argv) != 4:
            print(f"Uso: python3 cfich_chacha20.py {operacao} <ficheiro> <ficheiro_chave>")
            sys.exit(1)
        ficheiro_chave = sys.argv[3]
        with open(ficheiro_chave, 'rb') as key_file:
            key = key_file.read()
        process_file(operacao, ficheiro, key)
