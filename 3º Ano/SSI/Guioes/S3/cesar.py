import sys

def cesar(opType, secKey, msg):
    if opType == "dec":
        dec(msg, secKey)
    elif opType == "enc":
        enc(msg, secKey)
    else:
        print("Opção inválida")

def dec(msg, secKey):
    processed_msg = preproc(msg)
    unshifted_msg = unshiftMsg(processed_msg, secKey)
    print(unshifted_msg)

def enc(msg, secKey):
    shifted_msg = shiftMsg(msg, secKey)
    print(shifted_msg)

def preproc(str):
    l = []
    for c in str:
        if c.isalpha():
            l.append(c.upper())
    return "".join(l)

def shiftMsg(msg, secKey):
    shifted_msg = ""
    for c in msg:
        if c.isalpha():
            ascii_offset = ord(secKey.upper()) - ord('A')
            shifted_char = chr((ord(c.upper()) - ord('A') + ascii_offset) % 26 + ord('A'))
            shifted_msg += shifted_char
        else:
            shifted_msg += c
    return shifted_msg

def unshiftMsg(msg, secKey):
    unshifted_msg = ""
    for c in msg:
        if c.isalpha():
            ascii_offset = ord(secKey.upper()) - ord('A')
            unshifted_char = chr((ord(c.upper()) - ord('A') - ascii_offset) % 26 + ord('A'))
            unshifted_msg += unshifted_char
        else:
            unshifted_msg += c
    return unshifted_msg

if __name__ == "__main__":
    if len(sys.argv) != 4:
        print("Uso: python script.py <opType> <secKey> <msg>")
        sys.exit(1)

    opType = sys.argv[1]
    opType = opType.lower().strip()
    secKey = sys.argv[2]
    msg = sys.argv[3]

    cesar(opType, secKey, msg)
