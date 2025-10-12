# Respostas das Questões

## Q1

Não é possível observar diferenças no comportamento dos programas **otp.py** e **bad_otp.py**, apenas morfologicamente. Ambos geram chaves OTP (One-Time Pad) para cifragem. No entanto, há uma diferença fundamental na maneira como as chaves são geradas, o que pode ter implicações significativas na segurança do sistema.

No ficheiro **otp.py**, a chave é gerada de forma segura, utilizando aleatoriedade fornecida pelo Sistema Operativo. Isto significa que a chave é verdadeiramente aleatória e imprevisível, tornando-a altamente segura para uso na cifragem devido à sua entropia genuína.

Por outro lado, **bad_otp.py** gera uma chave utilizando uma pseudo-aleatoriedade fornecida por uma API. Essa pseudo-aleatoriedade é determinística e baseada num algoritmo, o que significa que, embora possa parecer aleatória, a sequência de números gerada é previsível se alguém conhecer o algoritmo subjacente e o estado atual da semente aleatória. Isto compromete seriamente a segurança do sistema, dado que um atacante pode potencialmente prever ou recriar a chave utilizada para cifrar a mensagem.

Embora ambos os programas aparentemente realizem a mesma função, a diferença crucial reside na confiabilidade da aleatoriedade da chave gerada. Enquanto **otp.py** oferece segurança devido à sua aleatoriedade verdadeira, **bad_otp.py** introduz uma vulnerabilidade significativa ao utilizar uma pseudo-aleatoriedade, tornando-o inadequado para uso em aplicações sensíveis à segurança.

## Q2

O ataque realizado no ponto anterior não contradiz o resultado que estabelece a "segurança absoluta" da cifra _one-time pad_. Isto verifica-se dado que o aspecto comprometido é a geração da chave, que utiliza métodos de geração de números pseudo-aleatórios. A metodologia de encriptação em si continua a ser sólida e não comprometida pelo ataque mencionado.

# Relatório do Guião da Semana 3

## Descrição dos Programas Criados

### Cesar.py

**Cesar.py** implementa uma cifra de César, que é um tipo simples de cifra de substituição, onde cada letra do texto é deslocada um número fixo de posições no alfabeto. Possui as seguintes funcionalidades:

Função cesar: Esta função é o ponto de entrada principal do programa. Recebe três argumentos: opType (tipo de operação: "dec" para decifrar e "enc" para cifrar), secKey (chave de segurança) e msg (mensagem a ser cifrada ou decifrada). Dependendo do tipo de operação especificado, chama a função apropriada: dec para decifrar ou enc para cifrar.

Funções dec e enc: Essas funções realizam as operações de decifragem e cifragem, respectivamente. Ambas recebem a mensagem (msg) e a chave de segurança (secKey). A função dec primeiro pré-processa a mensagem, removendo caracteres não alfabéticos e convertendo todas as letras para maiúsculas. Seguidamente, realiza o deslocamento inverso para cada caractere alfabético da mensagem. A função enc realiza o processo inverso, realizando o deslocamento para cada caractere alfabético da mensagem.

Funções auxiliares preproc, shiftMsg e unshiftMsg: A função preproc pré-processa a mensagem, removendo caracteres não alfabéticos e convertendo todas as letras para maiúsculas. As funções shiftMsg e unshiftMsg realizam o deslocamento e o deslocamento inverso, respectivamente, para cada caractere alfabético da mensagem.

Fluxo de controlo principal: O programa verifica se foram passados os argumentos corretos na linha de comando. Se não forem passados corretamente, exibe uma mensagem que demonstra o tipo de input válido e encerra o programa. Caso contrário, extrai os argumentos da linha de comando (opType, secKey e msg) e chama a função cesar com esses argumentos.

### Cesar_attack.py

**cesar_attack.py** implementa uma função para descriptografar um texto cifrado utilizando a cifra de César e tenta encontrar o texto-limpo que contém uma ou mais palavras fornecidas. Aqui está uma descrição rápida do programa:

Função descriptografar: Esta função recebe o texto cifrado (ciphertext) e uma lista de palavras (palavras_chave). Itera sobre todas as possíveis chaves (0 a 25) e tenta descriptografar o texto utilizando cada chave. Se uma das palavras estiver presente no texto descriptografado, a função retorna a chave e o texto descriptografado. Caso contrário, retorna None e uma string vazia.

Fluxo de controlo principal: O programa verifica se os argumentos foram passados corretamente no terminal. De seguida, extrai o texto cifrado e as palavras-chave fornecidas. Chama a função descriptografar com esses argumentos e, se a chave não for None, imprime a chave e o texto descriptografado.

### Vigenere.py

**vigenere.py** implementa a cifra de Vigenère, que é uma extensão da cifra de César, utilizando uma chave de tamanho variável. Aqui está uma descrição rápida do programa:

Função vigenere: Esta função é o ponto de entrada principal do programa. Recebe três argumentos: opType (tipo de operação: "dec" para decifrar e "enc" para cifrar), secKey (chave de segurança) e msg (mensagem a ser cifrada ou decifrada). Dependendo do tipo de operação especificado, chama a função apropriada: dec para decifrar ou enc para cifrar.

Funções dec e enc: Essas funções realizam as operações de decifragem e cifragem, respectivamente. Ambas recebem a mensagem (msg) e a chave de segurança (secKey). Para a cifragem (enc), cada caractere da mensagem é deslocado usando a letra correspondente da chave (ciclicamente). Para a decifragem (dec), o processo inverso é realizado.

Funções auxiliares preproc, shiftMsg e unshiftMsg: A função preproc pré-processa a mensagem, removendo caracteres não alfabéticos e convertendo todas as letras para maiúsculas. As funções shiftMsg e unshiftMsg realizam o deslocamento e o deslocamento inverso, respectivamente, para cada caractere alfabético da mensagem, utilizando a chave de segurança fornecida.

Fluxo de controlo principal: O programa verifica se foram passados os argumentos corretos no terminal. Se não forem passados corretamente, exibe uma mensagem que indica os argumentos a serem introduzidos e encerra o programa. Caso contrário, extrai os argumentos da terminal (opType, secKey e msg) e chama a função vigenere com esses argumentos.

### Vigenere_attack.py

**vigenere_attack.py** é uma implementação de um ataque de força bruta à cifra de Vigenère para encontrar a chave usada para criptografar uma mensagem. Aqui está uma descrição rápida do funcionamento do programa:

Função decifrar: Esta função decifra o criptograma utilizando uma chave específica. Ela itera sobre cada caractere do criptograma e, para cada caractere, calcula o deslocamento necessário para decifrá-lo usando a chave correspondente. De seguida, adiciona o caractere decifrado ao texto decifrado.

Função main: Esta função é o ponto de entrada do programa. Verifica se os argumentos foram fornecidos corretamente no terminal. O primeiro argumento é o tamanho da chave, o segundo é o criptograma, e os argumentos subsequentes são as palavras-chave que esperamos encontrar no texto decifrado.

Ele inicializa uma lista vazia matches para armazenar as correspondências encontradas.

Seguidamente, itera por todas as combinações possíveis de letras do alfabeto, cada uma com o tamanho da chave fornecida. Para cada combinação de chave, ele tenta decifrar o criptograma e verifica se alguma das palavras-chave fornecidas está presente no texto decifrado. Se encontrar uma correspondência, adiciona a chave e o texto decifrado à lista de correspondências.

Depois de tentar todas as combinações de chaves, se pelo menos uma correspondência for encontrada, ordena as correspondências com base na frequência de algumas letras comuns em textos em inglês, como 'A', 'E', 'O', 'S', 'R', 'I'. Isso é feito usando a função sort com uma função de chave que conta o número de ocorrências de cada letra e ordena com base nessa contagem. A correspondência mais provável é aquela que tem o maior número de ocorrências destas letras comuns.

Por fim, se houver correspondências, imprime a chave e o texto decifrado correspondentes à correspondência mais provável. Se não houver correspondências, imprime uma mensagem informando que nenhuma chave válida foi encontrada.

### Otp.py

**otp.py** implementa um sistema de criptografia de fluxo usando a operação de "ou exclusivo" (XOR) com uma chave aleatória gerada. Aqui está uma descrição rápida do seu funcionamento:

Função generateKey: Gera uma chave aleatória de um determinado comprimento e guarda num ficheiro especificado.

Função xorData: Realiza a operação XOR entre os dados e a chave. Isso é feito combinando byte a byte os dados e a chave.

Função processFile: Lê os dados de um ficheiro e a chave de outro ficheiro. De seguida, chama a função xorData para criptografar ou descriptografar os dados, dependendo do modo fornecido. O resultado é guardado num ficheiro com uma extensão apropriada adicionada ao nome do ficheiro original.

Função main: É o ponto de entrada do programa. Verifica se o número correto de argumentos foi passado no terminal e determina qual comando foi fornecido. Se o comando for "setup", chama generateKey para gerar uma chave aleatória. Se for "enc" ou "dec", chama processFile para criptografar ou descriptografar um ficheiro.

### Bad_otp.py

**bad_otp.py** implementa um sistema de criptografia de fluxo usando a operação de "ou exclusivo" (XOR) com uma chave gerada por um gerador de números pseudoaleatórios (PRNG). No entanto, o PRNG utilizado (bad_prng) é inseguro e não deve ser utilizado para fins criptográficos. Aqui está uma descrição rápida do funcionamento do programa:

Função bad_prng: Implementa um gerador de números pseudo-aleatórios inseguro. Utiliza a função random.seed para inicializar a semente com dois bytes aleatórios e, em seguida, chama random.randbytes para gerar n bytes de dados pseudoaleatórios.

Função generate_key: Utiliza o PRNG inseguro para gerar uma chave aleatória de um determinado comprimento e guarda-a num ficheiro especificado.

Funções encrypt e decrypt: Lê os dados de um ficheiro e a chave de outro ficheiro. De seguida, chama a função xor_data para criptografar ou descriptografar os dados, respectivamente. Os dados criptografados ou descriptografados são guardados noutro ficheiro com uma extensão apropriada adicionada ao nome do ficheiro original.

Função main: É o ponto de entrada do programa. Verifica se o número correto de argumentos foi passado no terminal e determina qual comando foi fornecido. Se o comando for "setup", chama generate_key para gerar uma chave aleatória. Se for "enc" ou "dec", chama a função correspondente para criptografar ou descriptografar um arquivo.

### Bad_otp_attack.py

**bad_otp_attack.py** é uma implementação de um ataque de força bruta a um sistema de criptografia baseado em XOR com uma chave gerada aleatoriamente. Aqui está uma descrição rápida do funcionamento do programa:

Função xor_data: Realiza a operação XOR entre os dados e a chave, retornando o resultado.

Função try_decrypt: Tenta decifrar o criptograma gerando chaves aleatórias utilizando uma semente de dois bytes. Itera sobre todas as combinações possíveis de semente (um total de 256^2 combinações), gera uma chave aleatória com base nessa semente e tenta decifrar o criptograma com essa chave. Se encontrar pelo menos uma das palavras fornecidas no texto decifrado, retorna o texto decifrado. Caso contrário, retorna None.

Função main: É o ponto de entrada do programa. Verifica se o número correto de argumentos foi passado no terminal (o ficheiro de criptograma e a lista de palavras para procurar no texto decifrado). Em seguida, lê o ficheiro de criptograma e chama try_decrypt para tentar decifrar o criptograma. Se conseguir decifrar com sucesso, imprime o texto decifrado. Caso contrário, imprime uma mensagem indicando que a decifração falhou.
