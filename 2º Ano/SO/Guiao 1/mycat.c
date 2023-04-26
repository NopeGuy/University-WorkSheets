#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <unistd.h> /* chamadas ao sistema: defs e decls essenciais */
#include <fcntl.h>  /* O_RDONLY, O_WRONLY, O_CREAT, O_* */

/**
    int open(const char *path, int oflag [, mode]);
    ssize_t read(int fildes, void *buf, size_t nbyte);
    ssize_t write(int fildes, const void *buf, size_t nbyte);
    off_t lseek(int fd, off_t offset, int whence);
    int close(int fildes);
*/

#define BUFFER_SIZE 256

void mycat(int initial_path_descriptor, int final_path_descriptor)
{
    char *buffer = calloc(BUFFER_SIZE, 1);
    ssize_t bytes_read;

    while ((bytes_read = read(initial_path_descriptor, buffer, BUFFER_SIZE)) > 0)
    {
        if (bytes_read == -1)
        {
            perror("read");
            exit(EXIT_FAILURE);
        }
        write(final_path_descriptor, buffer, bytes_read);
    }

    close(initial_path_descriptor);
    close(final_path_descriptor);
}

int main(int argc, char const *argv[])
{
    if (argc == 3)
    {
        int initial_path_descriptor = open(argv[1], O_RDONLY);
        if (initial_path_descriptor == -1)
        {
            puts("Error opening file");
            exit(EXIT_FAILURE);
        }

        int final_path_descriptor = open(argv[2], O_WRONLY | O_CREAT | O_TRUNC, 0644);
        if (final_path_descriptor == -1)
        {
            puts("Error creating file");
            exit(EXIT_FAILURE);
        }

        mycat(initial_path_descriptor, final_path_descriptor);
    }

    else if (argc == 2)
    {
        int initial_path_descriptor = open(argv[1], O_RDONLY);
        if (initial_path_descriptor == -1)
        {
            puts("Error opening file");
            exit(EXIT_FAILURE);
        }

        mycat(initial_path_descriptor, STDOUT_FILENO);
    }

    else
        mycat(STDIN_FILENO, STDOUT_FILENO);

    return 0;
}