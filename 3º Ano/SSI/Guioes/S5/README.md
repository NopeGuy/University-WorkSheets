# Respostas das Questões

## Q1

Ao executar o programa chacha20_int_attck.py sobre um criptograma produzido por pbenc_chacha20_poly1305.py o impacto mais notório seria a modificação errónica do criptograma e obtenção de uma resposta diferente da correta.

Isto porque, o algoritmo ChaCha20-Poly1305 é um algoritmo de criptografia autenticada que gera um MAC para garantir a integridade e a autenticidade dos dados. 

Qualquer alteração no criptograma, como a realizada pelo chacha20_int_attck.py, faria com que a verificação do MAC falhasse durante o processo de descriptografia, resultando num erro e impedindo a recuperação dos dados originais.

## Q2

A sugestão de usar m2 com mais de 16 bytes é fundamental para explorar a vulnerabilidade do CBC-MAC a alterações nas mensagens quando estas possuem blocos múltiplos.

Em essência, se m2 tem mais de 16 bytes, significa que ela contém pelo menos dois blocos de dados. Isso é importante porque o CBC-MAC de uma mensagem é essencialmente o último bloco da cifra ao operar no modo CBC.

Se o atacante manipula o primeiro bloco de m2 para criar uma nova mensagem m3, a cifração deste bloco alterado sob o mesmo vetor de inicialização (IV) ou bloco anterior levará a uma cascata de alterações em todos os blocos subsequentes, mas o último bloco (o MAC) permanecerá o mesmo se não houver alterações nos blocos seguintes.

Assim, t2 seria um tag válido para m3, realizando com sucesso o ataque.
