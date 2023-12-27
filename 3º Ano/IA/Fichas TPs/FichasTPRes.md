# Resolução Fichas TP

## Ficha TP1

### I/a) 
A, B, D, H, I, E, J, K, C, F, L <-OBJETIVO
### I/b) 
A, C ,F, L <-OBJETIVO

### II/a) 
A, B, C, D, E, F, G <-OBJETIVO
### II/b) 
A, C, G <-OBJETIVO

### III/a) 
A, B, C, D, G <-OBJETIVO
### III/b) 
A, C, G <-OBJETIVO

### IV/ 
b

### V/ 
b- ocupa menos espaço porque não tem de guardar os pais de cada nodo para saber o caminho como ocorre em BFS.


## Ficha TP2

### I/ a) Gualtar, S. Vitor, S. Vicente, Nogueiró,  (Vai sempre pela menor heuristica possivel)
### I/ b) Vai vendo heurística + custo

        Gualtar

    S. Vitor - (8)+2  | Este S Mamede - (7)+6=13

    É escolhido o S. Vitor e vẽ-se os seus filhos

    S. Vicente - (8+6)+6 =20 (Como é maior que o S. Mamede não é escolhido e verifica-se os filhos do S. Mamede)

    Sobreposta - (6+3)+4=13   | Lamaçães - (8+8)+4=20

    É escolhido o Sobreposta e vẽ-se os seus filhos

    Nogueiró = 6+3+6 = 15 (Como é o objetivo e tem menor valor que outro, não ha outra solução mais ótima que esta e é a escolhida)

    Os nós visitados foram Gualtar, Este S. Mamede, S. Vitor, Sobreposta

    Caminho é Gualtar-> Este S. Mamede ->Sobreposta->Nogueiró