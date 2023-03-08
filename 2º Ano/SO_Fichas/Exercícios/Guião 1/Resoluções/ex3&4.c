#include <sys/types.h>
#include <unistd.h>
#include <fcntl.h>

#define MAXBUFFER 1000

// Ex 3

ssize_t readln(int fd, char *line, ssize_t size) {
    ssize_t res = 0;
    ssize_t i = 0;

    while ((res = read(fd, &line[i], size)) > 0 && ((char) line[i] != '\n')) {
        i+=res;
    }

    return i;
}

//outra solução
/*
int readch(int fd, charbuff)
{
    if (read(fd, buff, 1) > 0)
        return 0;
    return 1;
}

ssize_t readln(int fd, charline, size_t size)
{
    int next_pos = 0;
    int read_bytes = 0;
    while (next_pos < size && readch(fd, line + next_pos) > 0)
    {
        read_bytes++;
        if (line[next_pos] == '\n')
            break;
        next_pos++;
    }
    return read_bytes;
}
*/


// Ex 4

ssize_t readln(int fd, char *line, ssize_t size)
{
    ssize_t res = 0;
    int j = 0;
    char local[MAXBUFFER];

    while ((res = read(fd, local, size)) > 0)
    {
        for (int i = 0; i < res; i++)
        {
            if ((char)local[i] != '\n')
            {
                line[j++] = local[i];
            }
            else
                return j;
        }
    }

    return j;
}

int main(int argc, char *argv[])
{
    char line[100];
    ssize_t size;
    if (argc < 2)
        return -1;

    int fd = open(argv[1], O_RDONLY);
    ssize_t tam = (ssize_t)atoi(argv[2]);

    size = readln(fd, line, tam);

    write(1, &line, size);
    line[0] = '\n';
    write(1, line, 1);
    close(fd);
    return 0;
} 