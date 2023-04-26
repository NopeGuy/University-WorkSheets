#include <unistd.h>
#include <stdio.h>

// ex1 & 2
int main(int argc, char *argv[])
{

    if (fork() == 0)
    {
        //caso o argumento n seja valido o exit status dá 1, se não dá 0
        execl("bin/ls", "ls", "-l", NULL);
        printf("after exec\n");
        _exit(255);
    }

    int status;

    if (wait(&status) > 0 && WIFEEXITED(status))
    {
        printf("exit status %d\n", WEXITSTATUS(status));
    }
}