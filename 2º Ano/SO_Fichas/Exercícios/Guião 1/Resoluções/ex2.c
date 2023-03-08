#include <fcntl.h>
#include <sys/types.h>
#include <sys/uio.h>
#include <unistd.h>
#include <stdlib.h>

#define MAX 1

int main(int argc, char *argv[])
{

    int f1, f2, n;
    char buf[MAX];

    f1 = open(argv[1], O_RDONLY);
    f2 = open(argv[2], O_WRONLY | O_TRUNC | O_CREAT, 0640);

    while ((n = read(0, buf, MAX)) > 0)
    {
        write(1, buf, n);
    }

    exit(0);
}