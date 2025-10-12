#ifndef RASTROSPROJECT_LISTA_H
#define RASTROSPROJECT_LISTA_H

#include "camadadedados.h"

typedef struct lista {
    void *cabeca;
    struct lista *proximo;

} CABECA, *LISTA;

LISTA criar_lista();

LISTA insere_cabeca(LISTA L, void *valor);

void *devolve_cabeca(LISTA L);

LISTA proximo(LISTA L);

LISTA remove_cabeca(LISTA L);

int lista_esta_vazia(LISTA L);

#endif //RASTROSPROJECT_LISTA_H
