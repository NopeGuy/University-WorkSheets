#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>

int main(int argc, char *argv[])
{
    int pipe_fd[2];

    if (pipe(pipe_fd) < 0)
    {
        perror("pipe");
        return 1;
    }

    int pid;
    if ((pid = fork()) == 0)
    {
        close(pipe_fd[0]);
        int i = 10;
        write(pipe_fd[1],&i,sizeof(int));
        sleep(30);
        _exit(0);
    }
    close(pipe_fd[0]);
    int i;
    int returnedvalue = read(pipe_fd[0], &i, sizeof(int));
    printf("Returned value %d, i value: %d", returnedvalue, i);

    wait(NULL);
}