#ifndef TRACER_H
#define TRACER_H


typedef struct pedido{
    pid_t pid;
    char commando[60];
    struct timeval inicial,final;
}pedido;

void escrevePID(pid_t pid);
char* extraiComandoString(int argc,char** argv);
void extraiComandoArray(char** str, int argc, char* argv[]);
pedido criaPedido(pid_t pid, char *commando, time_t inicial,time_t final);
void execute(int write_fd, char *cmd, char **cmds);


#endif