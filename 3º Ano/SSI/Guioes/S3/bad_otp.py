import sys
import os
from os import urandom
import random

def bad_prng(n):
    """ an INSECURE pseudo-random number generator """
    random.seed(random.randbytes(2))
    return random.randbytes(n)

def generate_key(length, filename):
    key = bad_prng(length)
    with open(filename, 'wb') as key_file:
        key_file.write(key)

def xor_data(data, key):
    return bytes([b ^ k for b, k in zip(data, key)])

def encrypt(filename, key_filename):
    with open(filename, 'rb') as file:
        data = file.read()
    with open(key_filename, 'rb') as key_file:
        key = key_file.read()
    
    encrypted = xor_data(data, key)
    with open(filename + '.enc', 'wb') as enc_file:
        enc_file.write(encrypted)

def decrypt(filename, key_filename):
    with open(filename, 'rb') as file:
        data = file.read()
    with open(key_filename, 'rb') as key_file:
        key = key_file.read()
    
    decrypted = xor_data(data, key)
    with open(filename + '.dec', 'wb') as dec_file:
        dec_file.write(decrypted)

def main():
    if len(sys.argv) < 4:
        print("Uso: python otp.py [setup|enc|dec] <arg2> <arg3>")
        return

    command = sys.argv[1]

    if command == 'setup':
        length = int(sys.argv[2])
        filename = sys.argv[3]
        generate_key(length, filename)
    elif command == 'enc':
        encrypt(sys.argv[2], sys.argv[3])
    elif command == 'dec':
        decrypt(sys.argv[2], sys.argv[3])
    else:
        print("Comando inválido!")

if __name__ == '__main__':
    main()
