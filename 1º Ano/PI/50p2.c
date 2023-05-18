#include <stdio.h>
#include <stdlib.h>
#include "listas.h"

// 1
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

// 2

void freeList(LInt l)
{
    while (l)
    {
        LInt pointer = l->prox;
        free(l);
        l = l->prox;
    }
}

// 3

void imprimeL(LInt l)
{
    while (l)
    {
        printf("%d \n", l->valor);
        l = l->prox;
    }
}

// 4

LInt reverseL(LInt l) // troca os apontadores de direção
{
    LInt current = l;
    LInt prev = NULL;
    LInt next = NULL;
    while (current != NULL)
    {
        next = current->prox;
        current->prox = prev;
        prev = current;
        current = next;
    }
    l = prev;
    return (l);
}

// 5

void insertOrd(LInt *l, int i)
{
    int temp;
    LInt prev = NULL;
    LInt curr = *l;
    LInt novo = malloc(sizeof(LInt));
    novo->valor = i;
    novo->prox = NULL;
    while (curr != NULL && curr->valor < i)
    {
        prev = curr;
        curr = curr->prox;
    }

    if (prev == NULL)
    {
        novo->prox = curr;
        *l = novo;
    }
    else
    {
        novo->prox = curr;
        prev->prox = novo;
    }
}

// 6

int removeOneOrd(LInt *l, int x)
{
    if ((*l)->valor == x)
    {
        LInt temp = (*l);
        (*l) = (*l)->prox;
        free(temp);
        return 0;
    }
    LInt prev = (*l);
    while (prev->prox)
    {
        LInt list = prev->prox;
        if (list->valor == x)
        {
            prev->prox = list->prox;
            free(list);
            return 0;
        }
        prev = prev->prox;
    }
    return 1;
}

// 7

void merge(LInt *r, LInt l1, LInt l2)
{
    if (l1 == NULL && l2 == NULL)
    {
        *r = NULL;
        return;
    }

    if (l1 == NULL)
    {
        *r = l2;
        return;
    }

    if (l2 == NULL)
    {
        *r = l1;
        return;
    }

    if (l1->valor < l2->valor)
    {
        *r = l1;
        merge(&((*r)->prox), l1->prox, l2);
    }
    else
    {
        *r = l2;
        merge(&((*r)->prox), l1, l2->prox);
    }
}

// 8
void splitQS(LInt l, int x, LInt *mx, LInt *Mx)
{
    LInt menor = NULL;       // Lista para elementos menores que x
    LInt maior = NULL;       // Lista para elementos maiores ou iguais a x
    LInt atual_menor = NULL; // Ponteiro para o último elemento da lista menor
    LInt atual_maior = NULL; // Ponteiro para o último elemento da lista maior

    while (l != NULL)
    {
        if (l->valor < x)
        {
            if (menor == NULL)
            {
                menor = l;
                atual_menor = l;
            }
            else
            {
                atual_menor->prox = l;
                atual_menor = atual_menor->prox;
            }
        }
        else
        {
            if (maior == NULL)
            {
                maior = l;
                atual_maior = l;
            }
            else
            {
                atual_maior->prox = l;
                atual_maior = atual_maior->prox;
            }
        }

        l = l->prox;
    }

    // Definir o próximo elemento como NULL nas duas listas para indicar o final
    if (atual_menor != NULL)
        atual_menor->prox = NULL;
    if (atual_maior != NULL)
        atual_maior->prox = NULL;

    // Atribuir as listas resultantes aos ponteiros de retorno
    *mx = menor;
    *Mx = maior;
}

void splitQS2(LInt l, int x, LInt *mx, LInt *Mx)
{
    if (!l)
        return;
    if (l->valor < x)
    {
        (*mx) = l;
        (*Mx) = NULL;
        splitQS(l->prox, x, &((*mx)->prox), Mx);
    }
    else
    {
        (*Mx) = l;
        (*mx) = NULL;
        splitQS(l->prox, x, mx, &((*Mx)->prox));
    }
}

// 9
LInt parteAmeio(LInt *l)
{
    LInt nova = NULL;
    int meio = (lengthList(*l) / 2), i = 0;
    if (meio == 0)
        return;
    for (; i < meio; i++)
    {
        nova = (*l);
        (*l)->prox;
        nova->prox;
    }
    return NULL;
}

// 10
int removeAll(LInt *l, int x)
{
    LInt prev = NULL;
    LInt curr = *l;
    int removida = 0;

    while (curr != NULL)
    {
        if (curr->valor == x)
        {
            if (prev == NULL)
            {
                *l = curr->prox; // Remove o primeiro elemento da lista
            }
            else
            {
                prev->prox = curr->prox; // Remove elemento intermediário ou final da lista
            }

            LInt temp = curr;
            curr = curr->prox;
            free(temp); // Libera a memória da célula removida
            removida++;
        }
        else
        {
            prev = curr;
            curr = curr->prox;
        }
    }

    return removida;
}

// 11 beta
int removeDupsB(LInt *l)
{
    LInt prev = NULL;
    LInt curr = *l;
    int i, j = 0;
    int tamanho = lengthList(*l);
    int arr[tamanho];

    while (curr != NULL)
    {
        for (i = 0; i < tamanho; i++)
        {
            if (arr[i] == curr->valor)
            {
                prev->prox = curr->prox;
                prev = curr;
                curr = curr->prox;
            }
            else
            {
                arr[j] = curr->valor;
                prev = curr;
                curr = curr->prox;
                j++;
            }
        }
    }

    return -1;
}

// 11 certa
int removeDupsB(LInt *l)
{
    LInt prev = NULL;
    LInt curr = *l;
    int removida = 0;
    int tamanho = lengthList(*l);
    int arr[tamanho];
    int i, j;

    while (curr != NULL)
    {
        int duplicada = 0;

        for (i = 0; i < j; i++)
        {
            if (arr[i] == curr->valor)
            {
                duplicada = 1;
                break;
            }
        }

        if (duplicada)
        {
            if (prev == NULL)
            {
                *l = curr->prox;
            }
            else
            {
                prev->prox = curr->prox;
            }
            LInt aux = curr;
            curr = curr->prox;
            free(aux);
            removida++;
        }
        else
        {
            arr[j] = curr->valor;
            j++;
            prev = curr;
            curr = curr->prox;
        }
    }

    return removida;
}

// 12
int removeMaiorL(LInt *l)
{
    LInt prev = NULL;
    LInt curr = *l;
    LInt maiorPrev = NULL;
    LInt maiorCurr = *l;
    int maior = curr->valor;

    while (curr != NULL)
    {
        if (curr->valor > maior)
        {
            maior = curr->valor;
            maiorPrev = prev;
            maiorCurr = curr;
        }
        prev = curr;
        curr = curr->prox;
    }

    if (maiorPrev == NULL)
    {
        *l = maiorCurr->prox;
    }
    else
    {
        maiorPrev->prox = maiorCurr->prox;
    }
    free(maiorCurr);

    return maior;
}

// 13
void init(LInt *l)
{
    if (*l == NULL)
        return;

    LInt prev = NULL;
    LInt curr = *l;

    while (curr->prox != NULL)
    {
        prev = curr;
        curr = curr->prox;
    }

    if (prev == NULL)
    {
        free(curr);
        *l = NULL;
    }
    else
    {
        prev->prox = NULL;
        free(curr);
    }
}

// 14
void appendL(LInt *l, int x)
{
    if (l == NULL)
        return;

    LInt prev = NULL;
    LInt curr = *l;
    LInt new = malloc(sizeof(LInt));

    new->valor = x;
    new->prox = NULL;

    if (curr == NULL)
    {
        *l = new;
        return;
    }

    while (curr->prox)
    {
        curr = curr->prox;
    }
    curr->prox = new;
}

// 15
void concatL(LInt *a, LInt b)
{
    LInt curr = *a;
    LInt prev = NULL;

    while (curr)
    {
        prev = curr;
        curr = curr->prox;
    }

    if (prev)
    {
        prev->prox = b;
    }
    else
    {
        *a = b;
    }

    while (b)
    {
        curr = b;
        b = b->prox;
    }
}

// 16
LInt cloneL(LInt l)
{
    if (!l)
        return NULL;
    LInt new = malloc(sizeof(struct lligada));
    new->valor = l->valor;
    new->prox = cloneL(l->prox);
    return new;
}

// 17
LInt cloneRev(LInt l)
{
    LInt new = NULL, list = NULL;

    for (; l; l = l->prox)
    {
        new = malloc(sizeof(struct lligada));
        new->valor = l->valor;
        new->prox = list;
        list = new;
    }

    return list;
}


//18
int maximo (LInt l){
    int maximo=0;
    while(l){
        if(maximo<l->valor) maximo = l->valor;
        l=l->prox;
    }
}


// 19
int take(int n, LInt *l)
{
    int counter = 0;
    LInt current = *l;
    LInt new = NULL, new2 = NULL;

    while (current != NULL && counter < n)
    {
        counter++;
        if (counter == n)
        {
            new = current->prox;
            current->prox = NULL;
        }
        current = current->prox;
    }

    while (new != NULL)
    {
        new2 = new;
        new = new->prox;
        free(new2);
    }

    return counter;
}


//20
int drop(int n, LInt *l) {
    int removed = 0;
    LInt curr = *l, new;

    while (curr && removed < n) {
        new = curr;
        curr = curr->prox;
        free(new);
        removed++;
    }

    *l = curr;
    return removed;
}


//21
LInt NForward (LInt l, int N){
    int counter;
    if(l == NULL) return;
    while(l && counter<N){
        l=l->prox;
        counter++;
    }
    return l;
}


//22
int listToArray(LInt l, int v[], int N)
{
    int i;
    for (i = 0; l && i < N; l = l->prox, i++)
    {
        v[i] = l->valor;
    }
}


//23
LInt arrayToList(int v[], int N)
{
    if (N == 0)
        return NULL;
    LInt l = malloc(sizeof(LInt));
    int i;
    l->valor = v[0];
    l->prox = NULL;
    LInt current = l;
    for (i = 1; i < N; i++, current = current->prox)
    {
        LInt new = malloc(sizeof(LInt));
        new->valor = v[i];
        new->prox = NULL;
        current->prox = new;
    }
    return l;
}


//24
LInt somasAcL(LInt l) // nao funciona!!
{
    if (l == NULL)
        return NULL;
    int acc = 0;
    LInt nL = malloc(sizeof(LInt));
    nL->valor = 0;
    nL->prox = NULL;
    for (; l;)
    {
        acc += l->valor;
        LInt next = malloc(sizeof(LInt));
        next->valor = acc;
        nL->prox = next;
        l = l->prox;
        nL = nL->prox;
    }
    return nL;
}


//25
LInt remreps(LInt l)
{
    LInt prev = NULL;

    if (l == NULL)
        return NULL;
    for (; l; l = l->prox)
    {
        if (prev != NULL && l->valor == prev->valor)
        {
            LInt temp = l;
            prev->prox = l->prox;
            free(temp);
        }
        prev = l;
    }
}

//26
void insere(int v[], int N, int x)
{
    int i;
    for (i = 0; i < N && v[i] < x; i++)
        ;
    for (; N > i; N--)
        v[N] = v[N - 1];
    v[i] = x;
}

//27
void merge(int r[], int a[], int b[], int na, int nb)
{
    int i, ca, cb;

    for (i = ca = cb = 0; i < na + nb; i++)
    {
        if ((ca < na && cb < nb && b[cb] < a[ca]) || ca >= na)
            r[i] = b[cb++];
        else
            r[i] = a[ca++];
    }
}
//-------------ARVORES------------------
typedef struct nodo
{
    int valor;
    struct nodo *esq, *dir;
} *ABin;

int altura(ABin a)
{
    if (a == NULL)
        return 0;
    int esq = altura(a->esq);
    int dir = altura(a->dir);
    if (esq > dir)
        return esq + 1;
    else
        return dir + 1;
}

ABin cloneAB(ABin a)
{
    ABin nova = malloc(sizeof(ABin));
    if (a == NULL)
        return NULL;
    nova->valor = a->valor;
    nova->esq = cloneAB(a->esq);
    nova->dir = cloneAB(a->dir);
    return nova;
}

void mirror(ABin *a)
{
    if (*a)
    {
        ABin temp = (*a)->esq;
        (*a)->esq = (*a)->dir;
        (*a)->dir = temp;
        mirror(&((*a)->dir));
        mirror(&((*a)->esq));
    }
}

void inorder(ABin a, LInt *l)
{
    if (a)
    {
        inorder(a->dir, l);
        LInt new = malloc(sizeof(LInt));
        new->valor = a->valor;
        new->prox = (*l);
        (*l) = new;
        inorder(a->esq, l);
    }
}

void preorder(ABin a, LInt *l) // rever
{
    if (a)
    {
        LInt new = malloc(sizeof(LInt));
        new->valor = a->valor;
        new->prox = NULL;
        if (!(*l))
        {
            (*l) = new;
        }
        else
        {
            LInt current = (*l);
            while (current->prox != NULL)
            {
                current = current->prox;
            }
            current->prox = new;
        }
        preorder(a->dir, l);
        preorder(a->esq, l);
    }
}

void posorder(ABin a, LInt *l) // rever
{
    if (a)
    {
        posorder(a->dir, l);
        posorder(a->esq, l);
        LInt new = malloc(sizeof(LInt));
        new->valor = a->valor;
        new->prox = (*l);
        (*l) = new;
    }
}

int depth(ABin a, int x) // rever
{
    if (!a)
        return -1;
    if (a->valor == x)
        return 1;
    int dir = depth(a->dir, x);
    int esq = depth(a->esq, x);

    if (dir == -1)
        return esq + 1;
    if (esq == -1)
        return dir + 1;
    if (esq == -1 && dir == -1)
        return -1;
    if (esq == 1 && dir == 1)
    {
        if (esq > dir)
            return dir + 1;
        else
            return esq + 1;
    }
}

int freeAB(ABin a)
{
    if (a)
    {
        int n = 1 + freeAB(a->dir) + freeAB(a->esq); // assim conta os nodos todos,
        free(a);                                     // em separado conta a depth
        return n;
    }
    else
        return 0;
}

int pruneAB(ABin a, int l) // muito foda (Rever!!)
{
    if (a)
    {
    }
}

int iguaisAB(ABin a, ABin b)
{
    if (a && (!b) || b && (!a))
        return 0;
    if (!a && !b)
        return 1;
    return iguaisAB(a->dir, b->dir) && iguaisAB(a->esq, b->esq) && a->valor == b->valor;
}

LInt nivelL(ABin a, int n)
{
    if (!a || n < 1)
        return NULL;
    if (a && n == 1)
    {
        LInt new = malloc(sizeof(LInt));
        new->valor = a->valor;
        new->prox = NULL;
        return new;
    }

    LInt dir = nivelL(a->dir, n - 1);
    LInt esq = nivelL(a->esq, n - 1);

    LInt aux = esq;
    if (aux == NULL)
        return dir;
    while (aux->prox)
    {
        aux = aux->prox;
    }
    aux->prox = dir;
    return esq;
}

int nivelV(ABin a, int n, int v[])
{
    if (a == NULL || n < 1) // Caso base: árvore vazia ou nível inválido
        return 0;

    if (n == 1)
    { // Caso base: nível desejado alcançado
        v[0] = a->valor;
        return 1;
    }

    // Chamadas recursivas para os subníveis esquerdo e direito
    int posEsq = nivelV(a->esq, n - 1, v);
    int posDir = nivelV(a->dir, n - 1, v + posEsq);

    return posEsq + posDir; // Retorna o número de posições preenchidas
}

int dumpAbin(ABin a, int v[], int N)
{
    if (a)
    {
        if (N > 0)
        {
            int e = dumpAbin(a->esq, v, N);
            if (e < N)
            {
                v[e] = a->valor;
                return 1 + e + dumpAbin(a->dir, v + e + 1, N - e - 1);
            }
        }
        else
            return N;
    }
    return 0;
}

ABin somasAcA(ABin a)
{
    if (a == NULL) // Caso base: árvore vazia
        return NULL;

    ABin novaArvore = malloc(sizeof(struct nodo)); // Criar um novo nó para a nova árvore
    novaArvore->valor = somaElementos(a);          // Calcular a soma dos elementos da subárvore
    novaArvore->esq = somasAcA(a->esq);            // Chamada recursiva para o subnível esquerdo
    novaArvore->dir = somasAcA(a->dir);            // Chamada recursiva para o subnível direito

    return novaArvore;
}

int somaElementos(ABin a)
{
    if (a == NULL) // Caso base: árvore vazia
        return 0;

    // Soma dos elementos da subárvore = valor do nó + soma dos elementos do subnível esquerdo + soma dos elementos do subnível direito
    return a->valor + somaElementos(a->esq) + somaElementos(a->dir);
}

int contaFolhas(ABin a)
{
    if (a)
    {
        if (!a->dir && !a->esq)
        {
            return 1;
        }
        return contaFolhas(a->dir) + contaFolhas(a->esq);
    }
}

ABin cloneMirror(ABin a)
{
    if (a)
    {
        ABin novo = malloc(sizeof(ABin));
        novo->valor = a->valor;
        novo->dir = cloneMirror(a->esq);
        novo->esq = cloneMirror(a->dir);
        return novo;
    }
}

int addOrd(ABin *a, int x)
{
    while (*a)
    {
        if ((*a)->valor == x)
            return 1;
        if ((*a)->valor < x)
            a = &((*a)->dir);
        else
            a = &((*a)->esq);
    }
    ABin novo = malloc(sizeof(ABin));
    novo->valor = x;
    novo->esq = NULL;
    novo->dir = NULL;
    (*a) = novo;
    return 0;
}

int lookupAB(ABin a, int x) // deveria funcionar (7/10 testes)
{
    while (a)
    {
        if (a->valor == x)
            return 1;
        if (x < a->valor)
            a = a->dir;
        else
            a = a->esq;
    }
    return 0;
}

int depthOrd(ABin a, int x)
{
    while (a)
    {
        int res;
        if (a->valor == x)
            return 1;
        if (a->valor < x)
        {
            res = depthOrd(a->dir, x);
            if (res == -1)
                return -1;
            else
                return res + 1;
        }
        else
        {
            res = depthOrd(a->esq, x);
            if (res == -1)
                return -1;
            else
                return res + 1;
        }
    }
    return -1;
}

int maiorAB(ABin a)
{
    while (a)
    {
        a = a->dir;
    }
    return a->valor;
}

void removeMaiorA(ABin *a)
{
    if (!(*a))
        return;
    while ((*a)->dir)
    {
        a = &((*a)->dir);
    }
    ABin temp = (*a);
    free(*a);
    (*a) = temp->esq;
}

int quantosMaiores(ABin a, int x)
{
    if (!a)
        return 0;
    if (a->valor <= x)
    {
        return quantosMaiores(a->dir, x);
    }
    else
    {
        return 1 + quantosMaiores(a->esq, x) + quantosMaiores(a->dir, x);
    }
}


//50
void listToBTree(LInt l, ABin *a)
{
    if (!l)
        *a = NULL;
    else
    {
        int mid = length(l) >> 1;
        LInt *c = &l;
        for (; mid > 0; mid--, c = &(*c)->prox)
            ;
        *a = malloc(sizeof(struct nodo));
        (*a)->valor = (*c)->valor;
        (*a)->esq = (*a)->dir = NULL;
        LInt next = (*c)->prox;
        *c = NULL;
        listToBTree(l, &(*a)->esq);
        listToBTree(next, &((*a)->dir));
    }
}


//51
int maior(ABin a, int x)
{
    return (!a || (a && a->valor > x && maior(a->esq, x) && maior(a->dir, x)));
}

int menor(ABin a, int x)
{
    return (!a || (a && a->valor < x && menor(a->esq, x) && menor(a->dir, x)));
}

int deProcura1(ABin a)
{
    if (a)
    {
        int ans = menor(a->esq, a->valor) && maior(a->dir, a->valor);
        return ans && deProcura(a->esq) && deProcura(a->dir);
    }

    return 1;
}



// run
int main(int argc, char **argv)
{
    LInt l = newLInt(1, newLInt(2, newLInt(3, newLInt(4, newLInt(5, NULL)))));
    LInt new;
    new = cloneRev(l);
}