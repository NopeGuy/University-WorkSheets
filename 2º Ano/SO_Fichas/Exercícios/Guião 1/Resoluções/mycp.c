#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <unistd.h> /* chamadas ao sistema: defs e decls essenciais */
#include <fcntl.h> /* O_RDONLY, O_WRONLY, O_CREAT, O_* */

/**
    int open(const char *path, int oflag [, mode]);
    ssize_t read(int fildes, void *buf, size_t nbyte);
    ssize_t write(int fildes, const void *buf, size_t nbyte);
    off_t lseek(int fd, off_t offset, int whence);
    int close(int fildes);
*/

#define BUFFER_SIZE 256

void mycp(const char *initial_path, char const *new_path ){
    int initial_path_descriptor = open(initial_path, O_RDONLY);
    if (initial_path_descriptor == -1){
        puts("Error opening file");
        exit(EXIT_FAILURE);
    }
    
    int final_path_descriptor = open(new_path, O_WRONLY | O_CREAT | O_TRUNC, 0644); // 0644 -> Utilizador pode ler e escrever no ficheiro
    if (final_path_descriptor == -1){
        puts("Error creating file");
        exit(EXIT_FAILURE);
    }

    char *buffer = calloc(BUFFER_SIZE, 1);
    ssize_t bytes_read;

    while((bytes_read = read(initial_path_descriptor, buffer, BUFFER_SIZE)) > 0){
        if (bytes_read == -1){
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
    if (argc == 3) mycp(argv[1], argv[2]);

    else{
        char *buffer = NULL;
        char *buffer2 = NULL;

        size_t buffer_size = 256;

        puts("Introduza o caminho do ficheiro que quer copiar");
        if (getline(&buffer, &buffer_size, stdin) < 0){
            perror("read");
            exit(EXIT_FAILURE);
        }

        char *initial_path = strsep(&buffer, "\n\r");

        puts("Introduza o caminho para o qual quer copiar o ficheiro");
        if (getline(&buffer2, &buffer_size, stdin) < 0){
            perror("read");
            exit(EXIT_FAILURE);
        }
        
        char *final_path = strsep(&buffer2, "\n\r");

        mycp(initial_path, final_path);

    }
    
    return 0;
}
