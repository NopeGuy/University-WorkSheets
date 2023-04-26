#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>

int main(int argc, char *argv[])
{
    mkfifo("fifo", 0666);
    if(mkfifo("fifo", 0666) == -1) {
        perror("mkfifo");
        return 1;
    }
}