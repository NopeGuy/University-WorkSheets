#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h> /* chamadas ao sistema: defs e decls essenciais */
#include <fcntl.h> /* O_RDONLY, O_WRONLY, O_CREAT, O_* */
#include <string.h>
#include "pessoas.h"

void printPessoas(Pessoas pessoas){
    int file_descriptor = open("registo-pessoas", O_RDONLY);

    while (read(file_descriptor, &pessoas, sizeof(struct pessoas)) > 0)
        printf("%s - %d\n", pessoas.nome, pessoas.idade);

    close(file_descriptor);
    
}

void inserirPessoa(Pessoas pessoas, char* nome, int idade){
    strcpy(pessoas.nome, nome);
    pessoas.idade = idade;

    int file_descriptor = open("registo-pessoas", O_WRONLY | O_CREAT | O_APPEND, S_IRUSR | S_IWUSR);
    if (file_descriptor == -1){
        perror("ERRO - Não foi possível ler ou criar o ficheiro de registo de pessoas");
        return;
    }
    
    if (write(file_descriptor, &pessoas, sizeof(struct pessoas)) < 1){
        perror("ERRO - Não foi possível escrever no ficheiro de registo de pessoas");
        close(file_descriptor);
        return;
    }
    
    off_t filesize = lseek(file_descriptor, 0, SEEK_END);
    int pos = (int) filesize / sizeof(struct pessoas);

    printf("Inseção bem sucedida! - Registo %d\n", pos);

    close(file_descriptor);

    printPessoas(pessoas);
}

void updatePessoa(Pessoas pessoas, char* nome, int idade){

    int file_descriptor = open("registo-pessoas", O_RDWR);
    if (file_descriptor == -1){
        perror("ERRO - Não foi possível ler o ficheiro de registo de pessoas.");
        return;
    }
    
    while (read(file_descriptor, &pessoas, sizeof(struct pessoas)) > 0)
    {
        if (!strcmp(pessoas.nome, nome)){
            pessoas.idade = idade;
            lseek(file_descriptor, -sizeof(struct pessoas), SEEK_CUR);
            if (write(file_descriptor, &pessoas, sizeof(struct pessoas)) < 1){
                perror("ERRO - Não foi possível escrever no ficheiro de registo de pessoas.");
                close(file_descriptor);
                return;
            }
            printf("Update bem sucedido!\n");
            break;
        }
    }

    close(file_descriptor);
    
    printPessoas(pessoas);
}

void updatePessoaByIndice(Pessoas pessoas, long indice, int idade){

    int file_descriptor = open("registo-pessoas", O_RDWR);
    long current_indice = 0;

    if (file_descriptor == -1){
        perror("ERRO - Não foi possível ler o ficheiro de registo de pessoas.");
        return;
    }
    
    while (read(file_descriptor, &pessoas, sizeof(struct pessoas)) > 0)
    {
        current_indice++;
        if (current_indice == indice){
            pessoas.idade = idade;
            lseek(file_descriptor, -sizeof(struct pessoas), SEEK_CUR);
            if (write(file_descriptor, &pessoas, sizeof(struct pessoas)) < 1){
                perror("ERRO - Não foi possível escrever no ficheiro de registo de pessoas.");
                close(file_descriptor);
                return;
            }
            printf("Update bem sucedido!\n");
            break;
        }
    }

    close(file_descriptor);
    
    printPessoas(pessoas);
}

int main(int argc, char const *argv[])
{
    Pessoas pessoas;

    if (argc < 4){
        printf("Número de Argumentos abaixo do esperado");
        return 1;
    }

    if (*(argv[1] + 1) == 'i'){
        char* nome = (char*) argv[2];
        int idade = atoi(argv[3]);
        inserirPessoa(pessoas, nome, idade);   
    } 
    else if (*(argv[1] + 1) == 'u'){
        char* nome = (char*) argv[2];
        int idade = atoi(argv[3]);
        updatePessoa(pessoas, nome, idade);
    } 
    else if(*(argv[1] + 1) == 'o'){
        long indice = atol(argv[2]);
        int idade = atoi(argv[3]);
        updatePessoaByIndice(pessoas, indice, idade);
    } 

    return 0;
}
