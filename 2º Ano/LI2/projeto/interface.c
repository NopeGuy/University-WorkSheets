#include <stdio.h>
#include <math.h>
#include "interface.h"
#include "camadadedados.h"
#include <string.h>
#include "logic.h"
#include "lista.h"
#include <stdlib.h>

FILE *fp;

#define BUF_SIZE 1024

#define n 8
bool finish;

void jogadas(ESTADO *e) {

    int i;
    printf("Jogador 1:");
    for (i = 0; i < e->num_jogadas; i++) {
        printf(" %c%c", e->jogadas[i].jogador1.coluna, e->jogadas[i].jogador1.linha);
        i++;
    }
    printf("\nJogador 2:");
    for (i = 1; i < e->num_jogadas; i++) {
        printf(" %c%c", e->jogadas[i].jogador2.coluna, e->jogadas[i].jogador2.linha);
        i++;
    }
    printf("\n");

}

int distancia_euclidiana(char x, char y, ESTADO *e) {
    int value;
    int letra = x - 'a';
    int numero = y - 49;
    if (e->jogador_atual == 1)
        value = (int) sqrt(pow(letra, 2) + pow(numero - 7, 2));
    else value = (int) sqrt(pow(letra - 7, 2) + pow(numero,2));
    return value;
}

void jogadasInFile(ESTADO *e) {

    int k = 0;
    int i = 1;
    while (k < e->num_jogadas) {
        if(i<=9) {
            fprintf(fp, "0%d: %c%c %c%c\n", i, e->jogadas[k].jogador1.coluna, e->jogadas[k].jogador1.linha,
                    e->jogadas[k + 1].jogador2.coluna, e->jogadas[k + 1].jogador2.linha);
            k += 2;
            i++;
        } else if (i>9){
            fprintf(fp, "%d: %c%c %c%c\n", i, e->jogadas[k].jogador1.coluna, e->jogadas[k].jogador1.linha,
                    e->jogadas[k + 1].jogador2.coluna, e->jogadas[k + 1].jogador2.linha);
            k += 2;
            i++;
        }
    }
    fprintf(fp, "\n");
}

void printBoard(ESTADO *e) {

    int k = 0;
    int i;
    int j;
    if (e->num_jogadas > 0)
        printf("\n\n----------------------------------------");
    printf("\n\nJogador atual : %d\n", e->jogador_atual);
    printf("Turno: %d\n\n", (e->num_jogadas / 2) + 1);
    printf(" ");
    while (k < n) {
        printf("   %c", k + 65);
        k++;
    }
    printf("\n  ---------------------------------\n");
    for (i = 0; i < n; i++) {
        printf("%d ", i + 1);
        for (j = 0; j < n; j++) {
            printf("|");
            printf(" %c ", e->tab[i][j]);

            if (j == n - 1)
                printf("|");
        }
        printf("\n  ---------------------------------\n");
    }
    printf("\n");
    jogadas(e);
}

void printInFile(ESTADO *e) {
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            if (e->tab[i][j] == VAZIO)
                fprintf(fp, ". ");
            else if (e->tab[i][j] == PRETA)
                fprintf(fp, "# ");
            else if (e->tab[i][j] == UM)
                fprintf(fp, "1 ");
            else if (e->tab[i][j] == DOIS)
                fprintf(fp, "2 ");
            else fprintf(fp, "* ");
        }
        fprintf(fp, "\n");
    }
    fprintf(fp, "\n");
}

void position(ESTADO *e, int valor) {
    e->num_jogadas = valor * 2;
    e->ultima_jogada.coluna = e->jogadas[e->num_jogadas - 1].jogador2.coluna;
    e->ultima_jogada.linha = e->jogadas[e->num_jogadas - 1].jogador2.linha;

    for (int i = (valor * 2); i < 64; i++) {
        e->jogadas[i].jogador1.linha = ' ';
        e->jogadas[i].jogador1.coluna = ' ';
        e->jogadas[i + 1].jogador2.linha = ' ';
        e->jogadas[i + 1].jogador2.coluna = ' ';
        i++;
    }

    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            e->tab[i][j] = VAZIO;
        }
        e->tab[3][4] = PRETA;
        e->tab[0][7] = DOIS;
        e->tab[7][0] = UM;

    }

    for (int i = 0; i < (valor * 2) - 1; i++) {
        e->tab[e->jogadas[i].jogador1.linha - 49][e->jogadas[i].jogador1.coluna - 'a'] = PRETA;
        e->tab[e->jogadas[i + 1].jogador2.linha - 49][e->jogadas[i + 1].jogador2.coluna - 'a'] = PRETA;
    }
    e->tab[(e->ultima_jogada.linha - 49)][(e->ultima_jogada.coluna - 'a')] = BRANCA;
    e->jogador_atual = 1;
    printBoard(e);

}


int interpretador(ESTADO *e) {
    char linha[BUF_SIZE];
    char col[2], lin[2];
    char filename[100] = "..\\";
    char dir[50];
    char token[3];
    int valor;


    if (fgets(linha, BUF_SIZE, stdin) == NULL)
        return 0;

    if (finish == true) {
        printf("Jogo finalizado por favor escolha outro comando.");
    } else if (finish == false) {
        if (strlen(linha) == 3 && sscanf(linha, "%[a-h]%[1-8]", col, lin) == 2) {
            if (add_position(*col, *lin, e) == 0)
                printf("Jogada invalida\n");
            else if (check_finish(*col, *lin, e) != 0) {
                printBoard(e);
                if (check_finish(*col, *lin, e) == 1) {
                    puts("Player 1 wins!");
                    finish = true;
                    return 2;
                }
                if (check_finish(*col, *lin, e) == 2) {
                    puts("Player 2 wins!");
                    finish = true;
                    return 2;
                }
                if (check_finish(*col, *lin, e) == 3) {
                    printf("\nPlayer %i wins!", e->jogador_atual % 2 + 1);
                    finish = true;
                    return 2;
                }
            } else printBoard(e);
        }

        if (strlen(linha) == 4 && strncmp(linha, "jog", 3) == 0) {
            int k = 0;
            int counter = 0;
            bool played = false;
            LISTA posicoes = criar_lista();
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (e->ultima_jogada.coluna + j >= 'a' && e->ultima_jogada.coluna + j <= 'h') {
                        if (e->ultima_jogada.linha + i >= '1' && e->ultima_jogada.linha + i <= '8') {
                            if (e->tab[e->ultima_jogada.linha + i - '1'][e->ultima_jogada.coluna + j - 'a'] == VAZIO) {
                                char *auxiliar = malloc(sizeof(char) * 5);
                                sprintf(auxiliar, "%c%c", e->ultima_jogada.coluna + j, e->ultima_jogada.linha + i);
                                posicoes = insere_cabeca(posicoes, auxiliar);
                                k++;
                            } else if ((e->tab[e->ultima_jogada.linha + i - '1'][e->ultima_jogada.coluna + j - 'a'] ==
                                        UM &&
                                        e->jogador_atual == 1) ||
                                       (e->tab[e->ultima_jogada.linha + i - '1'][e->ultima_jogada.coluna + j - 'a'] ==
                                        DOIS && e->jogador_atual == 2)) {
                                add_position((char) (e->ultima_jogada.coluna + j), (char) (e->ultima_jogada.linha + i),
                                             e);
                                played = true;
                                break;
                            }
                        }
                    }
                }
                if (played == true)
                    break;

            }
            if (played == false) {
                int choice = rand() % k;
                char *escolha = devolve_cabeca(posicoes);
                while (counter != choice) {
                    counter++;
                    posicoes = remove_cabeca(posicoes);
                    escolha = devolve_cabeca(posicoes);
                }
                printf("\n%s", escolha);
                add_position(escolha[0], escolha[1], e);
            }
            printBoard(e);
            if (check_finish((char) e->ultima_jogada.coluna, (char) e->ultima_jogada.linha, e) != 0) {
                if (check_finish((char) e->ultima_jogada.coluna, (char) e->ultima_jogada.linha, e) == 1) {
                    puts("Player 1 wins!");
                    finish = true;
                    return 2;
                }
                if (check_finish((char) e->ultima_jogada.coluna, (char) e->ultima_jogada.linha, e) == 2) {
                    puts("Player 2 wins!");
                    finish = true;
                    return 2;
                }
                if (check_finish((char) e->ultima_jogada.coluna, (char) e->ultima_jogada.linha, e) == 3) {
                    printf("\nPlayer %i wins!", e->jogador_atual % 2 + 1);
                    finish = true;
                    return 2;
                }
            }
        }

        if (strlen(linha) == 5 && strncmp(linha, "jog2", 4) == 0) {
            int k = 0;
            bool played = false;
            LISTA posicoes = criar_lista();
            if (finish == true)
                puts("Jogo finalizado por favor escolha outro comando.");
            else {
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if (e->ultima_jogada.coluna + j >= 'a' && e->ultima_jogada.coluna + j <= 'h') {
                            if (e->ultima_jogada.linha + i >= '1' && e->ultima_jogada.linha + i <= '8') {
                                if (e->tab[e->ultima_jogada.linha + i - '1'][e->ultima_jogada.coluna + j - 'a'] ==
                                    VAZIO) {
                                    char *auxiliar = malloc(sizeof(char) * 5);
                                    sprintf(auxiliar, "%c%c", e->ultima_jogada.coluna + j, e->ultima_jogada.linha + i);
                                    posicoes = insere_cabeca(posicoes, auxiliar);
                                    k++;
                                } else if (
                                        (e->tab[e->ultima_jogada.linha + i - '1'][e->ultima_jogada.coluna + j - 'a'] ==
                                         UM &&
                                         e->jogador_atual == 1) ||
                                        (e->tab[e->ultima_jogada.linha + i - '1'][e->ultima_jogada.coluna + j - 'a'] ==
                                         DOIS && e->jogador_atual == 2)) {
                                    add_position((char) (e->ultima_jogada.coluna + j),
                                                 (char) (e->ultima_jogada.linha + i),
                                                 e);
                                    played = true;
                                    break;
                                }
                            }
                        }
                    }
                    if (played == true)
                        break;

                }
                if (played == false) {
                    char *minValue = devolve_cabeca(posicoes);
                    char *maxValue = devolve_cabeca(posicoes);
                    if (e->jogador_atual == 1 && played == false) {
                        for (int i = 0; i < k; i++) {
                            posicoes = remove_cabeca(posicoes);
                            if (lista_esta_vazia(posicoes) == 1)
                                break;
                            char *aux = devolve_cabeca(posicoes);
                            if (distancia_euclidiana(minValue[0], minValue[1], e) >=
                                distancia_euclidiana(aux[0], aux[1], e))
                                minValue = aux;
                        }
                        add_position(minValue[0], minValue[1], e);
                        played = true;
                    }
                    if (e->jogador_atual == 2 && played == false) {
                        for (int i = 0; i < k; i++) {
                            posicoes = remove_cabeca(posicoes);
                            if (lista_esta_vazia(posicoes) == 1)
                                break;
                            char *aux2 = devolve_cabeca(posicoes);
                            if (distancia_euclidiana(maxValue[0], maxValue[1], e) <=
                                distancia_euclidiana(aux2[0], aux2[1], e))
                                maxValue = aux2;
                        }
                        add_position(maxValue[0], maxValue[1], e);
                        played = true;
                    }
                }
                printBoard(e);
                if (check_finish((char) e->ultima_jogada.coluna, (char) e->ultima_jogada.linha, e) != 0) {
                    if (check_finish((char) e->ultima_jogada.coluna, (char) e->ultima_jogada.linha, e) == 1) {
                        puts("Player 1 wins!");
                        finish = true;
                        return 2;
                    }
                    if (check_finish((char) e->ultima_jogada.coluna, (char) e->ultima_jogada.linha, e) == 2) {
                        puts("Player 2 wins!");
                        finish = true;
                        return 2;
                    }
                    if (check_finish((char) e->ultima_jogada.coluna, (char) e->ultima_jogada.linha, e) == 3) {
                        printf("\nPlayer %i wins!", e->jogador_atual % 2 + 1);
                        finish = true;
                        return 2;
                    }
                }
                finish = false;
            }
        }
    }


    if (strlen(linha) == 2 && strncmp(linha, "q", 1) == 0) {
        fflush(stdin);
        printf("Exiting game");
        return 3;
    }


    if (strlen(linha) == 5 && strncmp(linha, "movs", 4) == 0)
        jogadas(e);


    if (strncmp(linha, "gr", 2) == 0 && sscanf(linha, "%s %s", token, dir)) {
        strcat(filename, dir);
        strcat(filename, ".txt");
        fp = fopen(filename, "w+");
        printInFile(e);
        jogadasInFile(e);
        fclose(fp);
        printf("\nGuardado no ficheiro %s.txt :", dir);
        printBoard(e);
    }


    if (strncmp(linha, "ler", 3) == 0 && sscanf(linha, "%s %s", token, dir)) {
        finish = false;
        char check[5] = "01:";
        int find_pos = 0;
        int line_num = 1;
        e->jogador_atual = 1;
        e->num_jogadas = 0;
        e->ultima_jogada.linha = '4';
        e->ultima_jogada.coluna = 'e';
        e->jogadas->jogador1.linha = ' ';
        e->jogadas->jogador1.coluna = ' ';
        e->jogadas->jogador2.linha = ' ';
        e->jogadas->jogador2.coluna = ' ';

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                e->tab[i][j] = VAZIO;
            }
            e->tab[3][4] = BRANCA;
            e->tab[0][7] = DOIS;
            e->tab[7][0] = UM;

        }

        strcat(filename, dir);
        strcat(filename, ".txt");
        fp = fopen(filename, "r");
        while (fgets(linha, BUF_SIZE, fp) != NULL) {
            if (strstr(linha, check) != NULL) {
                find_pos++;
            }
            line_num++;
            if (find_pos != 0) {
                add_position(linha[4], linha[5], e);
                add_position(linha[7], linha[8], e);
            }
        }
        printBoard(e);
        if  (e->tab[7][0] == BRANCA ||
             e->tab[0][7] == BRANCA ||
             check_finish(e->ultima_jogada.coluna,e->ultima_jogada.linha,e) == 3) {
            puts("Game over.");
            finish = true;
        }
    }


    if (strncmp(linha, "pos", 3) == 0 && sscanf(linha, "%s %d", token, &valor)) {
        if (valor == 0)
            printf("Ainda não há jogadas no tabuleiro");

        if (valor >= e->num_jogadas)
            printf("Por favor utilize um valor entre 1 e %d\n", e->num_jogadas / 2);

        else {
            finish = false;
            position(e, valor);
        }

    }
    return 1;
}
