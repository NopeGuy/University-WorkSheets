# Guiões das aulas práticas


## Semanas:

 - [Semana 2](S2.md) - Ambiente de Desenvolvimento
 - [Semana 3](S3.md) - Cifras clássicas
 - [Semana 4](S4.md) - Cifras simétricas
 - [Semana 5](S5.md) - MACs; Cifra autenticada
 - [Semana 6](S6.md) - Acordo de Chaves DH
 - [Semana 7](S7.md) - Manipulação de Certificados e Assinaturas Digitais
 - [Semana 8](S8.md) - Controlo de Acesso ao Sistema de Ficheiros em Linux

 ---

 ## Instruções para a resolução dos guiões


Os guiões apresentam uma parte descritiva que contextualizam os problemas, e dois tipos de "questão" que devem ser resolvidos pelo grupo de trabalho:

 - **QUESTÃO: Qx** -- questão que deverá ser respondida no ficheiro `README.md` referente ao guião(ver abaixo);
 - **PROG: `xxx.py`** -- programa que deve realizar a funcionalidade especificada no guião.

A resolução do guião da `Semana X` deve ser submetida na directoria `Guioes/SX` (em que `X` denota o número da semana), e deve incluir pelo menos:

 1. O Ficheiro `README.md`, com a seguinte estrutura:
    - A primeira secção do documento deverá ser dedicado à resposta de questões colocadas no guião. 
    - Uma segunda secção dedicada ao relatório propriamente dito, contendo informação que entendam pertinente sobre a realização do guião (ausências a assinalar; justificação das opções tomadas; instruções de uso; dificuldades encontradas; etc.) 

    Assim, a estrutura de um ficheiro `README.md` de resposta a um guião será algo como:

        ```Markdown
        # Respostas das Questões
        ## Q1
        Resposta a Q1
        ## Q2
        Resposta a Q2
        ...
        # Relatório do Guião da Semana X
        ... (informação sobre a realização do guião) ...
        ```

 2. Ficheiros com código fonte dos programas solicitados no guião.
 3. Outros ficheiros de suporte (e.g. ficheiros de teste, etc.)

A linguagem de programação assumida nos guiões é o *Python*, mas admite-se que os programas pedidos sejam realizados noutras linguagens. Nesse caso, devem ajustar os nomes dos ficheiros em conformidade.

Dado que parte da avaliação será automatizada, é **muito importante** que se cumpra de forma estrita as indicações do guião, nomeadamente no que respeita:
 - ao nome do programa;
 - os argumentos de "linha de comando" (nome; ordem; significado)
 - formatos dos dados de entrada/saída

---

## ORGANIZAÇÃO DO REPOSITÓRIO

### Arrumação do repositório

Por forma a permitir um acesso ao repositório mais efectivo, devem proceder à seguinte organização de directorias:

```
+-- README.md: ficheiro contendo: (i) composição do grupo (número, nome e login github de cada
|              membro); (ii) aspectos que entenderem relevante salientar (e.g. dar nota de
|              algum guião que tenha ficado por realizar ou incompleto; um ou outro guião
|              que tenha sido realizado apenas por um dos membros do grupo; etc.)
+-- Guioes
|        |
|        +-- S2
|        |    +-- README.md: resposta às questões colocadas e
|        |    |              notas sobre a realização (justificação das opções
|        |    |              tomadas; instruções de uso; dificuldades encontradas; etc.)
|        |    +-- ...
|        |
|        +-- S3
|        |   ...
...      ...
|
+-- TPs
|   |
|   +-- TP1: Trabalho prático nº1 
|   |
|   +-- TP2: Trabalho prático nº2
|   ...
|
...
```


---
