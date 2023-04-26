#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/types.h>
#include <sys/stat.h>

int main(void)
{
    pid_t pid;
    int fd, fp, fq, bytes_read;
    char buf[32];

    mkfifo("fifo", 0666);

    // verificar codigo de saida
    fp = open("log.txt", O_CREAT | O_WRONLY, 0666);
    fd = open("fifo", O_RDONLY);
    fq = open("fifo", O_WRONLY);
    while ((bytes_read = read(fd, &buf, sizeof(buf))) > 0)
    {
        write(fp, &buf, bytes_read);
    }
    close(fp);
    close(fd);
    return 0;
}
