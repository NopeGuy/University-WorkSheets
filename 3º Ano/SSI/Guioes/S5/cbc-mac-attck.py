import sys, os, base64
from cryptography.hazmat.primitives.ciphers import Cipher, algorithms, modes
from cryptography.hazmat.primitives import padding


def cbcmac_auth(m_bytes, k):
    # CBC mode requires padding
    padder = padding.PKCS7(128).padder()
    padded_m = padder.update(m_bytes) + padder.finalize()
    iv = bytearray(16) # zero-filled IV
    cipher = Cipher(algorithms.AES(k), modes.CBC(iv))
    encryptor = cipher.encryptor()
    ct = encryptor.update(padded_m) + encryptor.finalize()
    tag = ct[-16:] # last block of ciphertext
    return tag

def cbcmac_verify(tag, m_bytes, k):
    padder = padding.PKCS7(128).padder()
    padded_m = padder.update(m_bytes) + padder.finalize()
    iv = bytearray(16)
    cipher = Cipher(algorithms.AES(k), modes.CBC(iv))
    encryptor = cipher.encryptor()
    ct = encryptor.update(padded_m) + encryptor.finalize()
    newtag = ct[-16:]
    return tag == newtag

def cbcmac_lengthextension_example(m1, m2):
    key = os.urandom(32)
    tag1 = cbcmac_auth(m1,key)
    tag2 = cbcmac_auth(m2,key)
    #print(m1, end=" ; tag : ")
    #print(base64.b64encode(tag1))
    #print(m2, end= " ; tag : ")
    #print(base64.b64encode(tag2))
    # check if tag1 verifies with m1, m2 / tag2 with m1, m2 :
    r1 = cbcmac_verify(tag1, m1, key)
    r2 = cbcmac_verify(tag1, m2, key)
    r3 = cbcmac_verify(tag2, m1, key)
    r4 = cbcmac_verify(tag2, m2, key)
    #print("tag1 + m1: " + str(r1))
    #print("tag1 + m2: " + str(r2))6
    #print("tag2 + m1: " + str(r3))
    #print("tag2 + m2: " + str(r4))
    # create a m3 (based on m1 and m2 that verifies with tag2)
    # XOR the first block of m2 with tag1 and concatenate with m1 and the rest of m2
    first_block_m2 = m2[:16]
    xor_result = bytes([_a ^ _b for _a, _b in zip(first_block_m2, tag1)])
    m3 = m1 + xor_result + m2[16:]
    r5 = cbcmac_verify(tag2, m3, key)
    #print("tag2 + m3: " + str(r5))
    return r5

def main(args = sys.argv):
    if len(args) != 3: 
        print("Utilização: python3 cbc-mac.py <msgs1> <msg2>")
    else:
        print(cbcmac_lengthextension_example(args[1].encode('utf-8'), args[2].encode('utf-8')))

if __name__ == '__main__':
    main()