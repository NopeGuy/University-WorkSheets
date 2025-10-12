import sys
import re
import csv
import os
# Definição dos padrões de tokens
token_patterns = [
    (r'[ \t\n]+',               None),   # Espaços em branco
    (r'--.*',                   None),   # Comentários
    (r'SELECT|FROM|WHERE',      'KEYWORD'),
    (r'[a-zA-Z_][a-zA-Z0-9_]*', 'IDENTIFIER'),
    (r'>=',                     'OPERATOR'),
    (r'\d+',                    'NUMBER'),
    (r',',                      'COMMA')
]

def lex(characters):
    pos = 0
    tokens = []
    while pos < len(characters):
        match = None
        for token_pattern in token_patterns:
            pattern, tag = token_pattern
            regex = re.compile(pattern)
            match = regex.match(characters, pos)
            if match:
                text = match.group(0)
                if tag:
                    token = {'text': text, 'type': tag}
                    tokens.append(token)
                break
        if not match:
            sys.stderr.write(f'Illegal character: {characters[pos]}\n')
            sys.exit(1)
        else:
            pos = match.end(0)
    return tokens


def main():
    current_directory = os.path.dirname(os.path.abspath(__file__))
    input_file = os.path.join(current_directory, '..', 'input', 'query.txt')
        
    with open(input_file, 'r') as file:
        tokens = lex(file.read().upper())
        print(tokens)

        for token in tokens:
            if token['type'] == 'KEYWORD':
                print(f"Keyword: {token['text']}")
            elif token['type'] == 'IDENTIFIER':
                print(f"Identifier: {token['text']}")
            elif token['type'] == 'OPERATOR':
                print(f"Operator: {token['text']}")
            elif token['type'] == 'NUMBER':
                print(f"Number: {token['text']}")
            elif token['type'] == 'COMMA':
                print(f"Comma: {token['text']}")
            else:
                print(f"Unknown token: {token['text']}")

if __name__ == "__main__":
    main()
    
