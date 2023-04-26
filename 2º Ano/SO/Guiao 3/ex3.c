#include <unistd.h>
#include <stdio.h>
#include <sys/wait.h>

// ex3
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>

int main(int argc, char* argv[])
{
    int i, status;
    for (i = 1; i < argc; i++)
    {
        pid_t pid = fork();
        if (pid == 0)
        {
            char* args[] = {argv[i], NULL};
            if (execvp(argv[i], args) == -1)
            {
                perror("execv");
                _exit(EXIT_FAILURE);
            }
        }
        else if (pid == -1)
        {
            perror("fork");
            exit(EXIT_FAILURE);
        }
    }
    for (i = 1; i < argc; i++)
    {
        if (wait(&status) > 0 && WIFEXITED(status))
        {
            printf("exit status %d\n", WEXITSTATUS(status));
        }
    }
    return 0;
}