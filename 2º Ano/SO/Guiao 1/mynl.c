#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <unistd.h> /* chamadas ao sistema: defs e decls essenciais */
#include <fcntl.h>  /* O_RDONLY, O_WRONLY, O_CREAT, O_* */

#define MAX_LENGTH 8

ssize_t readln(int fd, char *line, size_t size)
{
    ssize_t characters_read = 0;
    ssize_t bytes_read = 0;

    while (bytes_read = read(fd, line + bytes_read, size - bytes_read))
    {
        if (line[bytes_read - 1] == '\n')
        {
            line[bytes_read] = 0;
            return bytes_read - 1;
        }

        if (bytes_read >= size)
            return bytes_read + 1;
    }
}

ssize_t enumerateln(int fd, char *line, size_t size)
{
    ssize_t lines = 0;
    ssize_t bytes_read = 0;
    int buffer_overflow = 0;

    while ((bytes_read = readln(fd, line, size)) > 0 || line[0] == '\n')
    {
        if (bytes_read >= size && buffer_overflow == 0)
        {
            buffer_overflow = 1;
            lines++;
            printf("\t%lu %s", lines, line);
        }
        else if (bytes_read >= size && buffer_overflow == 1)
            printf("%s", line);

        else if (bytes_read < size && buffer_overflow == 1)
        {
            buffer_overflow = 0;
            printf("%s\n", line);
        }

        else if (bytes_read < size && buffer_overflow == 0)
        {
            lines++;
            printf("\t%lu %s", lines, line);
        }
    }

    return lines;
}

int main(int argc, char const *argv[])
{
    char *line = calloc(1, MAX_LENGTH);
    ssize_t characters_read = enumerateln(STDIN_FILENO, line, MAX_LENGTH);
    printf("%lu\n", characters_read);
    return 0;
}