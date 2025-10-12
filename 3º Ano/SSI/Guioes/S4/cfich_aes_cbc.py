import os
import sys
import struct
from cryptography.hazmat.primitives.ciphers import Cipher, algorithms, modes
from cryptography.hazmat.primitives import padding

def setup(fkey):
    key = os.urandom(32)
    with open(fkey + '.key', 'wb') as key_file:
        key_file.write(key)

def process_file(operation, fich, key):
    if operation.lower() == "enc":
        with open(fich, 'rb') as file:
            plaintext = file.read()

        padder = padding.PKCS7(128).padder()
        padded_plaintext = padder.update(plaintext) + padder.finalize()

        iv = os.urandom(16)

        algorithm = algorithms.AES(key)
        cipher = Cipher(algorithm, modes.CBC(iv))
        encryptor = cipher.encryptor()

        ciphertext = encryptor.update(padded_plaintext) + encryptor.finalize()

        output_file = fich + '.enc'
        with open(output_file, 'wb') as encrypted_file:
            encrypted_file.write(iv)
            encrypted_file.write(ciphertext)

    elif operation.lower() == "dec":
        with open(fich, 'rb') as encrypted_file:
            iv = encrypted_file.read(16)
            ciphertext = encrypted_file.read()

        algorithm = algorithms.AES(key)
        cipher = Cipher(algorithm, modes.CBC(iv))
        decryptor = cipher.decryptor()

        padded_plaintext = decryptor.update(ciphertext) + decryptor.finalize()

        unpadder = padding.PKCS7(128).unpadder()
        plaintext = unpadder.update(padded_plaintext) + unpadder.finalize()

        output_file = fich + '.dec'
        with open(output_file, 'wb') as decrypted_file:
            decrypted_file.write(plaintext)

    else:
        print(f"Operação desconhecida: {operation}")
        sys.exit(1)

if __name__ == "__main__":
    if len(sys.argv) < 3:
        print("Uso: python3 cfich_aes_cbc.py <operação> <ficheiro> [<ficheiro_chave>]")
        sys.exit(1)

    operacao = sys.argv[1]
    ficheiro = sys.argv[2]

    if operacao.lower() == "setup":
        if len(sys.argv) != 3:
            print("Uso: python3 cfich_aes_cbc.py setup <chave>")
            sys.exit(1)
        ficheiro_chave = sys.argv[2]
        setup(ficheiro_chave)

    else:
        if len(sys.argv) != 4:
            print(f"Uso: python3 cfich_aes_cbc.py {operacao} <ficheiro> <ficheiro_chave>")
            sys.exit(1)
        ficheiro_chave = sys.argv[3]
        with open(ficheiro_chave, 'rb') as key_file:
            key = key_file.read()
        process_file(operacao, ficheiro, key)
