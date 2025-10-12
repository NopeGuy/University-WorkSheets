import sys
import itertools

def decifrar(cryptograma, chave):
    decifrado = ''
    for i, c in enumerate(cryptograma):
        deslocamento = ord(chave[i % len(chave)]) - ord('A')
        decifrado += chr((ord(c) - deslocamento - 65) % 26 + 65)
    return decifrado

def main():
    if len(sys.argv) < 4:
        print("Uso: python3 vigenere_attack.py <tamanho_chave> <cryptograma> <palavra1> <palavra2> ...")
        sys.exit(1)

    tamanho_chave = int(sys.argv[1])
    cryptograma = sys.argv[2].upper()
    palavras = [palavra.upper() for palavra in sys.argv[3:]]
    alfabeto = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'


    matches = []

    for chave in itertools.product(alfabeto, repeat=tamanho_chave):
        chave_str = ''.join(chave)
        texto_decifrado = decifrar(cryptograma, chave_str)
        if any(palavra in texto_decifrado for palavra in palavras):
            matches.append((chave_str, texto_decifrado))

    if matches:
        matches.sort(key=lambda x: (x[1].count('A'), x[1].count('E'), x[1].count('O'), x[1].count('S'), x[1].count('R'), x[1].count('I')), reverse=True)

        print(matches[0][0])
        print(matches[0][1])
        
    else:
        print("Nenhuma chave válida encontrada.")

if __name__ == "__main__":
    main()
