#include "lista.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "camadadedados.h"

LISTA criar_lista() {
    LISTA x = malloc(sizeof(CABECA));
    x->cabeca = NULL;
    x->proximo = NULL;
    return x;
}


LISTA insere_cabeca(LISTA L, void *valor) {
    if (L->cabeca == NULL) {
        L->cabeca = valor;
        return L;
    } else {
        LISTA x = malloc(sizeof(CABECA));
        x->cabeca = valor;
        x->proximo = L;
        return x;
    }
}


void *devolve_cabeca(LISTA L) {
    return L->cabeca;
}


LISTA proximo(LISTA L) {
    return L->proximo;
}


LISTA remove_cabeca(LISTA L) {
    if (L->proximo != NULL) {
        LISTA x = L->proximo;
        return x;
    } else {
        L->cabeca = NULL;
        return L;
    }
}


int lista_esta_vazia(LISTA L) {
    if (L->cabeca == NULL && L->proximo == NULL) {
        return 1;
    } else {
        return 0;
    }
}