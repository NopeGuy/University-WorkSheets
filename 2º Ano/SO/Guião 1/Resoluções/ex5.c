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

//vai de caracter em caracter e verifica se é um numero
int isNumber(char *string)
{
    for(int i = 0; string[i] != '\0'; i++){
        if(string[i] < 48 || string[i] > 57)
            return 0;
    }
    return 1;
}

int numbers(char *buffer, int num)
{
    char local[20];
    int i = 0;

    if (num == 0)
    {
        buffer[0] = '0';
        buffer[1] = '\0';
        return 1;
    }

    while (num > 0)
    {
        local[i++] = (num % 10) + '0';
        num /= 10;
    }

    int j;
    for (j = 0; i > 0; j++, i--)
    {
        buffer[j] = local[i - 1];
    }
    buffer[j] = '\0';

    return j;
}

void inserir(char *argv[]){
    int file = open("pessoas.txt", O_CREAT | O_RDWR);
    int enc = 0;
    int reg = 1;
    char registo[20];
    ssize_t res;
    struct pessoa humano;

    // Guarda o nome e a idade no ficheiro
    // Imprime no stdout o número do registo
    strcpy(humano.nome, argv[2]);
    humano.idade = atoi(argv[3]);
    //apontar reg para o final do arquivo aberto
    reg = lseek(file, 0, SEEK_END);
    //registo vai guardar o numero de registos
    reg = numbers(registo, reg/sizeof(struct pessoa));
    write(file, &humano, sizeof(struct pessoa));
    write(1, "Registo ", 8);
    write(1, registo, reg);
    write(1, "\n", 1);
    
    close(file);
}





int main(int argc, char* argv[]){
    if (argc >=4)
    {
        if (!strcmp(argv[1], "-i")){
            inserir(argv);
        }
        /*
        else if(!strcmp(argv[1], "-u")){
            if(isNumber(argv[2])){
                alteraRegisto(argv);
            }
            else altera(argv);
        }
        */
        else write(1, "Argumento inválido\n", 19);
    }
    else write(1, "Argumento inválido\n", 19);
    return 0;
}