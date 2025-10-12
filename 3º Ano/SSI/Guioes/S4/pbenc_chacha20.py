import getpass
import os
import sys
import struct
from cryptography.hazmat.primitives.ciphers import Cipher, algorithms
from cryptography.hazmat.primitives.kdf.pbkdf2 import PBKDF2HMAC
from cryptography.hazmat.primitives import hashes

iterations = 480000

def derive_key(password, salt):
    kdf = PBKDF2HMAC(
        algorithm=hashes.SHA256(),
        length=32,
        salt=salt,
        iterations=iterations,
    )   
    key = kdf.derive(password.encode())
    return key

def process_file(operation, fich, salt):
    if operation.lower() == "enc":
        with open(fich, 'rb') as file:
            plaintext = file.read()

        nonce = os.urandom(8)
        counter = 0
        full_nonce = struct.pack("<Q", counter) + nonce

        password = getpass.getpass("Enter password: ")
        key = derive_key(password, salt)

        algorithm = algorithms.ChaCha20(key, full_nonce)
        cipher = Cipher(algorithm, mode=None)
        encryptor = cipher.encryptor()

        ciphertext = encryptor.update(plaintext)

        output_file = fich + '.enc'
        with open(output_file, 'wb') as encrypted_file:
            encrypted_file.write(nonce)
            encrypted_file.write(salt)
            encrypted_file.write(ciphertext)

    elif operation.lower() == "dec":
        with open(fich, 'rb') as encrypted_file:
            nonce = encrypted_file.read(8)
            salt = encrypted_file.read(16)
            ciphertext = encrypted_file.read()

        full_nonce = struct.pack("<Q", 0) + nonce

        password = getpass.getpass("Enter password: ")
        key = derive_key(password, salt)

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
    if len(sys.argv) != 3:
        print(f"Uso: python3 pbenc_chacha20.py <operação> <ficheiro>")
        sys.exit(1)

    operacao = sys.argv[1]
    ficheiro = sys.argv[2]
    salt = os.urandom(16)

    process_file(operacao, ficheiro, salt)
