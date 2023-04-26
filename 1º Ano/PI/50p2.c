#include <stdio.h>
#include <stdlib.h>
#include "listas.h"

//1
LInt newLInt(int v, LInt t)
{
    LInt new = (LInt)malloc(sizeof(struct lligada));

    if (new != NULL)
    {
        new->valor = v;
        new->prox = t;
    }
    return new;
}

int lengthList(LInt l)
{
    int i = 0;
    while (l)
    {
        l = l->prox;
        i++;
    }
    return i;
}

//2

void freeList(LInt l){
    while(l){
    LInt pointer = l->prox;
    free(l);
    l=l->prox;
    }
}



int main(int argc, char** argv){
    LInt l = newLInt(1, newLInt(2, newLInt(3, newLInt(4, newLInt(5, NULL)))));
    printf("%d \n", lengthList(l));
}