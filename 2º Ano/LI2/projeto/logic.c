#include <stdlib.h>
#include <stdbool.h>
#include "camadadedados.h"

int check_finish(char letter, char number, ESTADO *e) {
    int check = 1;
    if (letter - 'a' == 0 && number - '1' == 7) {
        e->num_jogadas++;
        return 1;
    }
    if (letter - 'a' == 7 && number - '1' == 0) {
        e->num_jogadas++;
        return 2;
    }
    for (int i = -1; i <= 1; i++) {
        for (int j = -1; j <= 1; j++) {
            if ((letter - 'a' + i) >= 0 && (number - '1' + j) >= 0) {
                if (e->tab[number - '1' + j][letter - 'a' + i] == VAZIO ||
                    e->tab[number - '1' + j][letter - 'a' + i] == UM ||
                    e->tab[number - '1' + j][letter - 'a' + i] == DOIS)
                    check = 0;
            }
        }
    }
    if (check) {
        return 3;
    }
    return 0;
}


int add_position(char letter, char number, ESTADO *e) {
    if (e->tab[(number - 49)][(letter - 'a')] == VAZIO ||
        e->tab[(number - 49)][(letter - 'a')] == UM ||
        e->tab[(number - 49)][(letter - 'a')] == DOIS) {
        if (!(abs(letter - (e->ultima_jogada.coluna)) > 1 || abs(number - (e->ultima_jogada.linha)) > 1)) {
            if (letter >= 'a' && letter <= 'h' && number >= '1' && number <= '8') {
                e->tab[(e->ultima_jogada.linha - 49)][(e->ultima_jogada.coluna - 'a')] = PRETA;
                e->tab[(number - 49)][(letter - 'a')] = BRANCA;
                e->ultima_jogada.linha = number;
                e->ultima_jogada.coluna = letter;
                if (e->jogador_atual == 1) {
                    e->jogadas[e->num_jogadas].jogador1.linha = number;
                    e->jogadas[e->num_jogadas].jogador1.coluna = letter;
                    e->jogador_atual = 2;
                } else {
                    e->jogador_atual = 1;
                    e->jogadas[e->num_jogadas].jogador2.linha = number;
                    e->jogadas[e->num_jogadas].jogador2.coluna = letter;
                }
                if (check_finish(letter, number, e))
                    return 2;
                else e->num_jogadas++;
                return 1;
            }
        }
    }
    return 0;
}