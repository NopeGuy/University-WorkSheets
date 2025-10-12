import sys
import os
from os import urandom

def generateKey(length, filename):
    key = urandom(length)
    with open(filename, 'wb') as key_file:
        key_file.write(key)

def xorData(data, key):
    return bytes([b ^ k for b, k in zip(data, key)])

def processFile(filename, key_filename, mode):
    with open(filename, 'rb') as file:
        data = file.read()
    with open(key_filename, 'rb') as key_file:
        key = key_file.read()
    
    if mode == 'enc':
        processed = xorData(data, key)
        output_filename = filename + '.enc'
    elif mode == 'dec':
        processed = xorData(data, key)
        output_filename = filename + '.dec'
    else:
        print("Invalid mode!")
        return
    
    with open(output_filename, 'wb') as output_file:
        output_file.write(processed)

def main():
    if len(sys.argv) < 4:
        print("Usage: python otp.py [setup|enc|dec] <arg2> <arg3>")
        return

    command = sys.argv[1]

    if command == 'setup':
        length = int(sys.argv[2])
        filename = sys.argv[3]
        generateKey(length, filename)
    elif command == 'enc' or command == 'dec':
        filename = sys.argv[2]
        key_filename = sys.argv[3]
        processFile(filename, key_filename, command)
    else:
        print("Invalid command!")

if __name__ == '__main__':
    main()
