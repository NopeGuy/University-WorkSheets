#include <stdio.h>
#include <stdlib.h>
#include <unistd.h> /* chamadas ao sistema: defs e decls essenciais */
#include <sys/wait.h>
#include <sys/types.h>
#include <fcntl.h>

int main(int argc, char const *argv[])
{
    pid_t pid;
    int status;

    for (int i = 0; i < 10; i++)
    {
        if ((pid = fork()) == 0) //se o "pid" que é retornado do fork() for igual a 0, então é o filho, o pai terá sempre um número maior que 1
        {
            pid_t processo_atual = getpid();
            pid_t processo_pai = getppid();

            //apesar dos processos serem do tipo "pid_t" é possível imprimir como "int"
            printf("Filho %d: O identificador do processo é %d , o indentificador do processo pai é %d\n", i, processo_atual, processo_pai);

            _exit(0);
        }

        else
        {
            pid_t processo_atual = getpid();
            pid_t processo_pai = getppid();

            printf("Pai %d: O identificador do processo é %d , o identificador do processo pai é %d, o identificador do processo filho é %d\n", i, processo_atual, processo_pai, pid);
        }
    }
    return 0;
}