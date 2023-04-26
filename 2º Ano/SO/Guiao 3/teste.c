#include <unistd.h>
#include <stdio.h>

int main(int argc, char const *argv[])
{

    sleep(10);

    if (execl("/bin/sleep", "sleep", "10", NULL) < 0)
    {
        printf("exec error\n");
    }

    printf("after exec\n");

    return 0;
}

