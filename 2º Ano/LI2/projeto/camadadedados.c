#include "camadadedados.h"
#include <stdlib.h>
#include "interface.h"


ESTADO *inicializar_estado() {
    ESTADO *e = (ESTADO *) malloc(sizeof(ESTADO));
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
    return e;
}
