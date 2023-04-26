#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>

int main(int argc, char *argv[])
{
    int fd;

    // Abre o pipe com nome
    fd = open("fifo", O_WRONLY);

    // Envia a mensagem para o servidor
    write(fd, argv[1], strlen(argv[1]));
    write(fd, "\n", 1);

    close(fd);
    return 0;
}
