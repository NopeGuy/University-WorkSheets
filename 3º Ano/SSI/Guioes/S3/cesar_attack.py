import sys

def descriptografar(ciphertext, palavras_chave):
    # Tentar cada possível chave
    for chave in range(26):
        texto_plano = ""
        # Descriptografar cada caractere no texto cifrado
        for char in ciphertext:
            if char.isalpha():
                # Determinar o deslocamento ASCII com base na caixa (maiúscula ou minúscula)
                ascii_offset = ord('A') if char.isupper() else ord('a')
                # Descriptografar o caractere usando a chave
                char_descriptografado = chr((ord(char) - ascii_offset - chave) % 26 + ascii_offset)
                texto_plano += char_descriptografado
            else:
                # Caracteres não alfabéticos permanecem inalterados
                texto_plano += char
        # Verificar se alguma palavra-chave está presente no texto descriptografado
        if any(word in texto_plano for word in palavras_chave):
            return chave, texto_plano
    # Se nenhuma chave válida for encontrada, retornar None e uma string vazia
    return None, ""

if __name__ == "__main__":
    # Ler o texto cifrado e as palavras-chave a partir dos argumentos da linha de comando
    texto_cifrado = sys.argv[1]
    palavras_chave = sys.argv[2:]

    # Tentar descriptografar e obter a chave e o texto plano
    chave, texto_plano = descriptografar(texto_cifrado, palavras_chave)

    # Imprimir os resultados
    if chave is None:
        None
    else:
        print(chave)
        print(texto_plano)
