import getpass
import os
import sys
import struct
from cryptography.hazmat.primitives.ciphers import Cipher, algorithms, modes
from cryptography.hazmat.primitives.kdf.pbkdf2 import PBKDF2HMAC
from cryptography.hazmat.primitives import hashes, hmac
from cryptography.exceptions import InvalidSignature

iterations = 480000

def derive_key(password, salt, length=64):
    kdf = PBKDF2HMAC(
        algorithm=hashes.SHA256(),
        length=length,
        salt=salt,
        iterations=iterations,
    )   
    key = kdf.derive(password.encode())
    return key[:32], key[32:]

def process_file(operation, fich, salt):
    if operation.lower() == "enc":
        with open(fich, 'rb') as file:
            plaintext = file.read()

        nonce = os.urandom(16)

        password = getpass.getpass("Enter password: ")
        key, hmac_key = derive_key(password, salt)

        cipher = Cipher(algorithms.AES(key), modes.CTR(nonce))
        encryptor = cipher.encryptor()

        ciphertext = encryptor.update(plaintext)

        h = hmac.HMAC(hmac_key, hashes.SHA256())
        h.update(ciphertext)
        mac = h.finalize()

        output_file = fich + '.enc'
        with open(output_file, 'wb') as encrypted_file:
            encrypted_file.write(nonce)
            encrypted_file.write(salt)
            encrypted_file.write(ciphertext)
            encrypted_file.write(mac)

    elif operation.lower() == "dec":
        with open(fich, 'rb') as encrypted_file:
          nonce = encrypted_file.read(16)
          salt = encrypted_file.read(16)
          data = encrypted_file.read()
          ciphertext = data[:-32]
          mac = data[-32:]

        password = getpass.getpass("Enter password: ")
        key, hmac_key = derive_key(password, salt)

        h = hmac.HMAC(hmac_key, hashes.SHA256())
        h.update(ciphertext)
        try:
            h.verify(mac)
        except InvalidSignature:
            print("Invalid MAC")
            sys.exit(1)

        cipher = Cipher(algorithms.AES(key), modes.CTR(nonce))
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
        print(f"Uso: python3 pbenc_aes_ctr_hmac.py <operação> <ficheiro>")
        sys.exit(1)

    operacao = sys.argv[1]
    ficheiro = sys.argv[2]
    salt = os.urandom(16)

    process_file(operacao, ficheiro, salt)