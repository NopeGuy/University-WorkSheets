#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>

int main(int argc, char *argv[]) {
int ola = open("fifo", O_WRONLY);
char buf[20];
int bytes_read;
while ((bytes_read = read(0, buf, sizeof(buf))) > 0)
{
    write(ola, buf, bytes_read);
    }
}