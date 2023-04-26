#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <unistd.h>

int main(int argc, char *argv[])
{
    int fd = open("fifo", O_RDONLY);
    if (fd == -1)
    {
        perror("open");
        exit(1);
    }
    char buf[20];
    int bytes_read;
    while ((bytes_read = read(fd, buf, sizeof(buf))) > 0)
    {
        write(STDOUT_FILENO, buf, bytes_read);
    }
    close(fd);
    return 0;
}
