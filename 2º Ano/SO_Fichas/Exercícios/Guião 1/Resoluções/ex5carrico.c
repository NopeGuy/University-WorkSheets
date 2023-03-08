#include <sys/types.h>
#include <unistd.h>
#include <fcntl.h>
#include <string.h>
#include <stdlib.h>

struct pessoa
{
    char nome[100];
    int idade;
};

int isNumber(char *string)
{

    for (int i = 0; string[i] != '\0'; i++)
        if (string[i] < 48 || string[i] > 57)
            return 0;

    return 1;
}

int numbers(char *buffer, int num)
{
    char local[20];
    int i = 0, j = 0;
    do
    {
        local[i++] = (num % 10) + 48;
        num /= 10;
    } while (num != 0);
    while (i >= 0)
    {
        buffer[j++] = local[--i];
    }
    buffer[j] = '\0';
    return j;
}

void inserir(char *argv[])
{
    int file = open("pessoas.txt", O_RDWR);
    int enc = 0;
    int reg = 1;
    char registo[20];
    ssize_t res;
    struct pessoa humano;

    // Guarda o nome e a idade no ficheiro
    // Imprime no stdout o número do registo
    strcpy(humano.nome, argv[2]);
    humano.idade = atoi(argv[3]);
    reg = lseek(file, 0, SEEK_END);
    reg = numbers(registo, reg / sizeof(struct pessoa));
    write(file, &humano, sizeof(struct pessoa));
    write(1, "Registo ", 8);
    write(1, registo, reg);
    write(1, "\n", 1);

    close(file);
}

void alteraRegisto(char *argv[])
{
    int file = open("pessoas.txt", O_RDWR);
    ssize_t res;
    int reg = 1;
    int enc = 0;
    char registo[20];
    struct pessoa humano;

    lseek(file, sizeof(struct pessoa) * (atoi(argv[2]) - 1), SEEK_SET);
    read(file, &humano, sizeof(struct pessoa));
    lseek(file, sizeof(struct pessoa) * (atoi(argv[2]) - 1), SEEK_SET);
    humano.idade = atoi(argv[3]);
    write(file, &humano, sizeof(struct pessoa));

    close(file);
}

void altera(char *argv[])
{
    int file = open("pessoas.txt", O_RDWR);
    ssize_t res;
    int reg = 1;
    int enc = 0;
    struct pessoa humano;

    while (!enc && (res = read(file, &humano, sizeof(struct pessoa))) > 0)
    {

        if (!strcmp(humano.nome, argv[2]))
        {
            enc = 1;
        }
        reg++;
    }

    if (enc)
    {
        // Voltar para trás no ficheiro para reescrever
        lseek(file, -sizeof(struct pessoa), SEEK_CUR);

        humano.idade = atoi(argv[3]);

        write(file, &humano, sizeof(struct pessoa));
    }

    close(file);
}

int main(int argc, char *argv[])
{
    if (argc >= 4)
    {
        if (!strcmp(argv[1], "-i"))
        {
            inserir(argv);
        }

        // Esta merda funcionou à primeira, poggers
        else if (!strcmp(argv[1], "-u"))
        {
            if (isNumber(argv[2]))
            {
                alteraRegisto(argv);
            }
            else
                altera(argv);
        }
        else
            write(1, "Invalid inputs\n", 15);
    }
    else
        write(1, "Invalid inputs\n", 15);
    return 0;
}
