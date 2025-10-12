# Respostas das Questões

## Q2

Utilizar um NONCE (Number used only ONCE) fixo representa uma prática altamente insegura na criptografia. Podemos concluir o mesmo porque, ao fixar o NONCE, criptogramas idênticos são gerados para mensagens iguais, o que facilita atacantes no que toca ao decifrar o código e revelar a mensagem original. Por outras palavras, quando usamos um NONCE fixo, comprometemos a confidencialidade das mensagens cifradas.

Utilizar um NONCE fixo é similar a utilizar a mesma chave para abrir todas as portas: É perigoso, pois permite que indivíduos mal-intencionados acedam a informações confidenciais com facilidade.

Quando um NONCE fixo é utilizado, a mesma mensagem cifrada é gerada repetidamente para a mesma mensagem original, possibilitando que um atacante identifique padrões nos dados e até mesmo finja ser o remetente legítimo para o sistema.

## Q3

O programa **chacha20_int_attck.py** foi projetado para manipular ficheiros de texto cifrados usando o algoritmo de cifragem **ChaCha20**. Substitui parte do texto cifrado numa posição específica por um novo texto. Por outro lado, os programas **cfich_aes_cbc.py** e **cfich_aes_ctr.py** foram projetados para cifrar arquivos usando AES em diferentes modos de operação: CBC (Cipher Block Chaining) e CTR (Counter), respectivamente.

O impacto de utilizar o programa **chacha20_int_attck.py** nos criptogramas produzidos pelos programas **cfich_aes_cbc.py** e **cfich_aes_ctr.py** seria que o resultado final não seria o esperado e, possivelmente, não decifrado corretamente. Isto ocorre, porque cada algoritmo de cifragem opera de maneira diferente e possui requisitos específicos de formatação de dados e parâmetros de inicialização.

Especificamente, o ChaCha20 é um algoritmo de fluxo que não requer preenchimento de dados como o AES no modo CBC, que exige preenchimento para garantir que os dados sejam múltiplos do tamanho do bloco. Além disso, o modo CTR do AES opera com um contador, o qual é alterado a cada bloco cifrado. Manipular o texto cifrado num ponto arbitrário pode interferir na sincronização do contador e, portanto, pode levar a um deciframento incorreto ou inutilizável.

Em resumo, o impacto seria a corrupção dos dados cifrados, tornando-os inutilizáveis ou resultando num deciframento incorreto, devido às diferenças fundamentais nos algoritmos e modos de operação entre o ChaCha20 e o AES-CBC/AES-CTR.
