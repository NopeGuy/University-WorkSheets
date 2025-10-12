#include <sys/types.h>
#include <sys/stat.h>
#include <stdio.h>
#include <fcntl.h>
#include <unistd.h>
#include <string.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <sys/time.h>
#include "tracer.h"

pedido global[100]; //array para guardar os pedidos
int pos=0; //ultima posicao no array global

suseconds_t calcExecMonitor(struct pedido pedido,struct pedido global)
{
    return ((pedido.final.tv_usec - global.inicial.tv_usec)/1000 + (pedido.final.tv_sec - global.inicial.tv_sec)*1000);
}

suseconds_t calcExec(pedido pedido,struct timeval now)
{
    return (now.tv_usec - pedido.inicial.tv_usec)/1000 + (now.tv_sec - pedido.inicial.tv_sec)*1000;
}

void status(pedido global[], int N)
{
    struct timeval now;
    gettimeofday(&now, NULL);

    int fd = open("../fifos/write", O_WRONLY);

    for (int i = 0; i < N; i++)
    {
        suseconds_t elapsed = calcExec(global[i],now);
        char resposta[100];
        snprintf(resposta, sizeof(resposta), "%d %s %ld ms\n", global[i].pid,global[i].commando, elapsed);
        write(fd, resposta, strlen(resposta));
    }
    close(fd);
}


int main(int argc, char *argv[])
{
    struct stat sb;
    if(argc>1 && stat(argv[1],&sb) == 0 && S_ISDIR(sb.st_mode))
    {
    char *pids_folder = argv[1];
    memset(global, 0, sizeof(global));
    int res_fifo, res_fifo2, fd_read, fd_write, bytes_read;
    pedido pedido;

    res_fifo = mkfifo("../fifos/read", 0666);
    res_fifo2 = mkfifo("../fifos/write", 0666);

    if ((fd_read = open("../fifos/read", O_RDONLY)) == -1)
    {
        fprintf(stderr, "Erro ao abrir o main fifo (read) (poderá não estar criado)!!!\n");
        fflush(stderr);
        _exit(1);
    }
    if ((fd_write = open("../fifos/read", O_WRONLY)) == -1) // Para manter o servidor aberto
    {
        fprintf(stderr, "Erro ao abrir o main fifo (write)!!!\n");
        fflush(stderr);
        _exit(1);
    }

    while ((bytes_read = read(fd_read, &pedido, (sizeof(pedido)))) > 0)
    {
        if (!strcmp(pedido.commando, "status"))
        {
            status(global, pos);
        }
        else if (!strcmp(pedido.commando, "break"))
        {
            exit(1);
        }
        else
        {
            int flag = 0;
            for (int j = 0; j <= pos; j++)
            {
                if (global[j].pid == pedido.pid)
                {
                    //escrita em ficheiro
                    char f[100],tempo[200],cmd[200];
                    snprintf(f, sizeof(f), "%s/%d.txt", pids_folder, global[j].pid);
                    int file = open(f, O_CREAT | O_WRONLY, 0666);
                    snprintf(tempo,sizeof(tempo),"%ld\n",calcExecMonitor(pedido,global[j]));
                    snprintf(cmd,sizeof(cmd),"%s\n",global[j].commando);
                    write(file,tempo,strlen(tempo));
                    write(file,cmd,strlen(cmd));
                    close(file);

                    //remoção do pedido da struct global
                    global[j] = global[pos - 1];
                    pos--;
                    flag = 1;
                    break;
                }
            }
            if (!flag)
            {
                global[pos] = pedido;
                pos++;
            }
        }
    }

    close(fd_read);
    close(fd_write);

    unlink("../fifos/read");
    unlink("../fifos/write");
    }
    else
    {
        write(1,"Folder not found!\n",19);
    }
    return 0;
}