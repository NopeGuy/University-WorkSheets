
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h> /* chamadas ao sistema: defs e decls essenciais */
#include <sys/wait.h>
#include <sys/types.h>
#include <fcntl.h>

int main(int argc, char const *argv[])
{
    pid_t processo_atual = getpid();
    pid_t processo_pai = getppid();

    printf("O identificador do processo é %d , o identificador do processo pai é %d\n", processo_atual, processo_pai);

    return 0;
}