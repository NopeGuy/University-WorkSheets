#include <stdio.h>

#define TAM_TURMA 40

typedef struct estudante
{
    char nome[50];
    int nota;
} Estudante;

int calcula_nota(char nome[])
{
    int nota = 0;
    int i = 0;

    while (nome[i] != '\0')
    {
        if (nome[i] >= 'a' && nome[i] <= 'z')
        {
            nota += nome[i] - 'a' + 1;
        }
        else if (nome[i] == ' ')
        {
            break;
        }
        i++;
    }

    return nota % 20;
}

void preenche_notas(Estudante turma[], int N)
{
    for (int i = 0; i < N; i++)
    {
        turma[i].nota = calcula_nota(turma[i].nome);
    }
}

void ordena_turma(Estudante turma[], int N)
{
    int i, j, max_idx;

    for (i = 0; i < N - 1; i++)
    {
        max_idx = i;
        for (j = i + 1; j < N; j++)
        {
            if (turma[j].nota > turma[max_idx].nota)
            {
                max_idx = j;
            }
        }
        // troca o elemento na posição i com o de maior nota encontrado
        Estudante tmp = turma[i];
        turma[i] = turma[max_idx];
        turma[max_idx] = tmp;
    }
}

void soma_melhores_notas(Estudante turma[], int N)
{
    int i, soma = 0;

    ordena_turma(turma, N);

    for (i = 0; i < 13; i++)
    {
        soma += turma[i].nota;
    }

    printf("Soma das notas dos 13 melhores alunos: %d\n", soma);
}

int main()
{

    Estudante turma[TAM_TURMA] = {{"Amity Blight", 0}, {"Willow", 0}, {"Daryl Dixon", 0}, {"Troy Barnes", 0}, {"Diane Nguyen", 0}, {"Aragorn", 0}, {"Negan", 0}, {"Gus Fring", 0}, {"Rey", 0}, {"Han Solo", 0}, {"Grogu", 0}, {"Nomi Marks", 0}, {"Charlie Spring", 0}, {"Lito Rodriguez", 0}, {"Frodo Baggins", 0}, {"Poe Dameron", 0}, {"Ashoka Tano", 0}, {"Miles Morales", 0}, {"Patrick Bateman", 0}, {"Anakin Skywalker", 0}, {"Glenn Rhee", 0}, {"Darth Vader", 0}, {"Ciri", 0}, {"Kala Dandekar", 0}, {"Camina Drummer", 0}, {"James Holden", 0}, {"Evelyn Wang", 0}, {"Cassian Andor", 0}, {"Will Gorski", 0}, {"Stanley Pines", 0}, {"America Chavez", 0}, {"Edalyn Clawthorne", 0}, {"Eugene Porter", 0}, {"Boba Fett", 0}, {"Gus Porter", 0}, {"Alex Kamal", 0}, {"Scott Lang", 0}, {"Ellie", 0}, {"Princess Carolyn", 0}, {"Capheus Onyango", 0}};
    preenche_notas(turma, TAM_TURMA);

    printf("Nota do estudante de índice 30: %d\n", turma[30].nota);

    soma_melhores_notas(turma, 40);

    return 0;
}