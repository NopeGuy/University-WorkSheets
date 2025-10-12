# TP1

**Autor:** Luís Ferreira, a91672
## Parser simples de csv para tratamento de dados usando python.
Este projeto desenvolve um programa Python dedicado à **análise de dados** armazenados em **CSV**. A estrutura do código é dividida em três partes fundamentais: **Parser, DataManagement** e o programa principal.

O **processo de parsing** interpreta e organiza dados de um arquivo **CSV**. Utilizando Python, o código abre um ficheiro com a biblioteca `os`, indicando o caminho do arquivo **CSV** e assegurando o uso de **UTF-8** para lidar com **caracteres especiais**. Em seguida, o código percorre o arquivo linha a linha, ignorando a primeira linha que contém o cabeçalho. Cada linha subsequente é dividida em campos individuais com base no separador de vírgula, e esses campos são armazenados numa lista. Para cada linha, é construído um dicionário Python associando cada campo a uma chave no dicionário. Por fim, esses dicionários são adicionados a um array `athletes`, representando cada atleta como um dicionário estruturado.

 Depois no **DataManagement** temos as funções **sortSport** que ordena alfabeticamente e imprime as **modalidades desportivas**, enquanto **aptAthletes** calcula a **percentagem de atletas aptos**. A função **ageDistribution** classifica os atletas por **escalões etários** e apresenta as **percentagens** correspondentes.

## Instruções

1. Clone este repositório para o seu ambiente local.
2. Guarde os seus ficheiros csv no formato do exemplo em data.
3. Abra o python e corra o script `Manager.py`.
