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

    if ((pid = fork()) == 0)
    {
        pid_t processo_atual = getpid();
        pid_t processo_pai = getppid();

        printf("Filho: O identificador do processo é %d , o indentificador do processo pai é %d\n", processo_atual, processo_pai);

        _exit(0);
    }

    else
    {
        pid_t processo_atual = getpid();
        pid_t processo_pai = getppid();

        printf("Pai: O identificador do processo é %d , o identificador do processo pai é %d, o identificador do processo filho é %d\n", processo_atual, processo_pai, pid);
    }

    return 0;
}