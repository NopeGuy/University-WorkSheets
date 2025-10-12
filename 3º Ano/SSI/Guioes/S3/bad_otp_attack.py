import sys
import random
from itertools import product

def xor_data(data, key):
    return bytes([b ^ k for b, k in zip(data, key)])

def try_decrypt(ciphertext, words):
    encoded_words = [word.encode('utf-8') for word in words]
    for seed in product(range(256), repeat=2):        
        random.seed(bytes(seed))
        key = random.randbytes(len(ciphertext))
        plaintext = xor_data(ciphertext, key)
        
        if any(encoded_word in plaintext for encoded_word in encoded_words):
            return plaintext.decode('utf-8', errors='ignore')
    return None

def main():
    if len(sys.argv) < 3:
        print("Usage: python3 bad_otp_attack.py <cryptogram file> <word list>")
        sys.exit(1)

    cryptogram_file = sys.argv[1]
    words = sys.argv[2:]

    with open(cryptogram_file, 'rb') as cf:
        ciphertext = cf.read()

    plaintext = try_decrypt(ciphertext, words)
    if plaintext:
        print(plaintext)
    else:
        print("Failed to decrypt the message.")

if __name__ == '__main__':
    main()
