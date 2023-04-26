#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <limits.h>
#include <assert.h>

#define MAXLINE 1024

char buffer[MAXLINE];

typedef enum movimento
{
    Norte,
    Oeste,
    Sul,
    Este
} Movimento;
typedef struct posicao
{
    int x, y;
} Posicao;

void one()
{
    int num;
    int largest = 0;
    while (num != 0)
    {
        scanf("%d", &num);
        if (num > largest)
            largest = num;
    }
    printf("Maior: %d\n", largest);
}

void two()
{
    int num;
    int index = 0;
    double average = 0;
    int *numlist;

    while (num != 0)
    {
        scanf("%d", &num);
        average += num;
        index++;
    }
    average = average / index;
    printf("%f\n", average);
}

void three()
{
    int num;
    int largest = 0;
    int secondlarg = 0;
    while (num != 0)
    {
        scanf("%d", &num);
        if (num > largest)
        {
            secondlarg = largest;
            largest = num;
        }
        else if (num > secondlarg)
        {
            secondlarg = num;
        }
    }
    printf("Segundo maior: %d\n", secondlarg);
}

int six(unsigned int n)
{
    int x = 10, i = 0;
    for (i; n > 1; i++)
    {
        n = n / 10;
    }
    return i;
}

char *eight(char *dest, char source[])
{
    int i;
    for (i = 0; source[i] != '\0'; i++)
    {
        dest[i] = source[i];
    }
    dest[i] = '\0';
    return dest;
}

void thirteen(char t[], int n)
{
    int i = 0, j = 0, count = 0;
    while (t[i])
    {
        if (count == n)
        {
            while (t[i] != ' ' && t[i])
                i++;
            if (!t[i])
                break;
            t[j++] = t[i++];
            count = 0;
        }
        else if (t[i] != ' ')
        {
            t[j++] = t[i++];
            count++;
            ;
        }
        else
        {
            t[j++] = t[i++];
            count = 0;
        }
    }
    t[j] = '\0';
}

void twentyseven(int r[], int a[], int b[], int na, int nb)
{
    int i = 0;
    int j = 0;
    int k = 0;
    while (k < na + nb)
    {
        if ((a[i] <= b[j] && i < na) || j >= nb)
        {
            r[k] = a[i];
            k++;
            i++;
        }
        else
        {
            r[k] = b[j];
            k++;
            j++;
        }
    }
    for (int b = 0; b < na + nb; b++)
    {
        printf("%d \n", r[b]);
    }
}

int twentyeight(int a[], int i, int j)
{
    for (int k = i; k < j; k++)
    {
        if (a[k + 1] < a[k])
        {
            return 0;
        }
    }
    return 1;
}

int thirtyone(int v[], int N)
{
    int number;
    int counter = 0;
    int countermax = 0;
    for (int i = 0; i < N; i++)
    {
        if (counter > countermax)
        {
            countermax = counter;
            number = v[i];
        }
        if (v[i + 1] == v[i])
        {
            counter++;
        }
        else if (v[i + 1] != v[i])
        {
            counter = 0;
        }
    }
    return number;
}

int thirtysix(int a[], int na, int b[], int nb)
{
    int i, j;
    int counter = 0;
    for (i = 0; i < na; i++)
    {
        for (j = 0; j < nb; j++)
        {
            if (a[i] == b[j])
            {
                counter++;
                break;
            }
        }
    }
    return counter;
}

int fortyfive(int N, int v1[N], int v2[N], int r[N]){
    int i = 0, c = 0;
    for (; i < N; i++, c++)
    {
        if (v1[i] == v2[i])
            r[c] = v1[i];
        else if (v1[i] < v2[i])
            r[c] = v2[i];
        else
            r[c] = v1[i];
    }
    return c;
}

Posicao fourtyseven(Posicao inicial, Movimento mov[], int N)
{
    int i = 0;
    for (i; i < N; i++)
    {
        if (mov[i] == Norte)
            inicial.y += 1;
        if (mov[i] == Este)
            inicial.x += 1;
        if (mov[i] == Sul)
            inicial.y -= 1;
        if (mov[i] == Oeste)
            inicial.x -= 1;
    }
    return inicial;
}

int main()
{
    int r;

    printf("Escreve o número da pergunta\n");
    int input_number, number;
    char input[] = "Isto é uma cópia";
    char dest[strlen(input) + 1];
    if (scanf("%d", &input_number) == 1)
    {
        number = input_number;
    }
    else
    {
        printf("Error: Invalid input\n");
        return 1;
    }

    switch (number)
    {
    case 1:
        printf("Pergunta 1\n\n");
        one();
        break;

    case 2:
        printf("Pergunta 2\n\n");
        two();
        break;

    case 3:
        printf("Pergunta 3\n\n");
        three();
        break;

    case 6:
        printf("Pergunta 6\n\n");
        r = six(440000);
        printf("Resposta à pergunta 6 é: %d \n", r);
        break;

    case 8:
        printf("Pergunta 8\n\n");
        char *answer = eight(dest, input);
        printf("%s\n", answer);
        return 0;
        break;

    case 13:
        printf("Pergunta 13\n\n");
        thirteen("oooo aaaaaaa bbbbbbbb", 3);
        break;

    case 27:
        printf("Pergunta 27\n\n");
        int v[6] = {1, 2, 4, 5, 6, 9};
        int w[6] = {0, 1, 2, 3, 4, 5};
        int l[12];
        twentyseven(l, v, w, 6, 6);
        break;

    case 28:
        printf("Pergunta 28\n\n");
        int y[6] = {1, 0, 4, 5, 3, 9};
        r = twentyeight(v, 1, 3);
        printf("Resposta da 28: %d \n", r);
        break;

    case 31:
        printf("Pergunta 31\n\n");
        int boda[10] = {0, 1, 1, 1, 2, 3, 3, 4, 4, 4};
        r = thirtyone(boda, 11);
        printf("Resposta da 31: %d \n", r);
        break;

    case 36:
        printf("Pergunta 36\n\n");
        int boda1[10] = {0, 1, 1, 1, 2, 3, 3, 4, 4, 7};
        int boda2[10] = {0, 1, 1, 1, 2, 3, 3, 4, 4, 5};
        r = thirtysix(boda1, 10, boda2, 10);
        printf("Resposta da 36: %d \n", r);
        break;

    case 45:
        printf("Pergunta 45\n\n");
        //  fortyfive(7, {1, 2, 3, 4, 5, 5, 5 }, );
        break;

    case 47:
        printf("Pergunta 47\n\n");
        Posicao fk;
        fk.x = 1;
        fk.y = 4;
        Movimento aaa[] = {Norte, Norte, Sul, Este};
        fourtyseven(fk, aaa, 4);
        break;

    default:
        printf("Invalid choice\n");
        break;
    }

    return 0;
}
