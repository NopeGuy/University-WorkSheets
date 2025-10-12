import getpass
import os
import sys
import struct
from cryptography.hazmat.primitives.ciphers.aead import ChaCha20Poly1305
from cryptography.hazmat.primitives.kdf.pbkdf2 import PBKDF2HMAC
from cryptography.hazmat.primitives import hashes
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

    nonce = os.urandom(12)

    password = getpass.getpass("Enter password: ")
    key_enc, key_auth = derive_key(password, salt)

    cipher = ChaCha20Poly1305(key_enc)
    ciphertext = cipher.encrypt(nonce, plaintext, None)

    output_file = fich + '.enc'
    with open(output_file, 'wb') as encrypted_file:
      encrypted_file.write(nonce)
      encrypted_file.write(salt)
      encrypted_file.write(ciphertext)

  elif operation.lower() == "dec":
    with open(fich, 'rb') as encrypted_file:
      nonce = encrypted_file.read(12)
      salt = encrypted_file.read(16)
      ciphertext = encrypted_file.read()

    password = getpass.getpass("Enter password: ")
    key_enc, key_auth = derive_key(password, salt)

    cipher = ChaCha20Poly1305(key_enc)
    plaintext = cipher.decrypt(nonce, ciphertext, None)

    output_file = fich + '.dec'
    with open(output_file, 'wb') as decrypted_file:
      decrypted_file.write(plaintext)

  else:
    print(f"Operação desconhecida: {operation}")
    sys.exit(1)

if __name__ == "__main__":
  if len(sys.argv) != 3:
    print(f"Uso: python3 pbenc_chacha20_poly1305.py <operação> <ficheiro>")
    sys.exit(1)

  operacao = sys.argv[1]
  ficheiro = sys.argv[2]
  salt = os.urandom(16)

  process_file(operacao, ficheiro, salt)
