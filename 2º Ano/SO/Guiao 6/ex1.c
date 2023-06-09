Teste 2021


#Exercício 1
    mkfifo("tubo", 0644);
int fd_leitura = open("tubo", O_RDONLY);
open("tubo", O_WRONLY); // manter aberto para o servidor nao morrer
int fildes[9];
for (int i = 0; i < 9; i++)
{
    char nome_ficheiro[7];
    sprintf(nome_ficheiro, "zona_%d", i);
    fildes[i] = open(nome_ficheiro, O_WRONLY | O_CREAT, 0644);
}
char buffer[1024];
int bytes_read = 0;
while (read(fd_leitura, buffer, BUF_SIZE) > 0)
{
    char *save_buf = buffer;
    char **fields;
    while ((fields = parse_entry(&save_buf)))
    {
        char line
            [strlen(fields[0]) + strlen(fields[1]) + strlen(fields[2]) + 3];
        int to_write =
            sprintf(line, "%s %s %s\n", fields[0], fields[1], fields[2]);
        write(fildes[atoi(fields[2])], line, to_write);
    }
}

#Exercicio 2
int vacinados(char *regiao, int idade)
{
    int n = 0;
    char idade_string[6];
    sprintf(idade_string, " %d ", idade);
    int pipes[2];
    if (pipe(pipes) == -1)
    {
        perror("Pipe creation failed");
    }
    if (fork() == 0)
    {
        close(pipes[0]);
        dup2(pipes[1], STDOUT_FILENO);
        close(pipes[1]);
        execlp("grep", "grep", idade_string, regiao, NULL);
    }
    close(pipes[1]);
    int pipes2[2];
    if (pipe(pipes2) == -1)
    {
        perror("Pipe creation failed");
    }

    if (fork() == 0)
    {
        close(pipes2[0]);
        dup2(pipes[0], STDIN_FILENO);
        close(pipes[0]);
        dup2(pipes2[1], STDOUT_FILENO);
        close(pipes2[1]);
        execlp("wc", "wc", "-l", NULL);
    }
    close(pipes[0]);
    close(pipes2[1]);
    int read_bytes = 0;
    char buf[1025];
    read_bytes = read(pipes2[0], buf, 1024);
    close(pipes2[0]);
    buf[read_bytes] = 0;
    return atoi(buf);
}

#Exercicio 3
bool vacinado(char *cidadao)
{
    int pids[9];
    for (int i = 0; i < 9; i++)
    {
        if ((pids[i] = fork()) == 0)
        {
            char ficheiro[9];
            sprintf(ficheiro, "regiao_%d", i);
            execlp("grep", "grep", cidadao, ficheiro, NULL);
        }
    }
    int status;
    bool b = false;
    while (wait(&status) != -1)
    {
        if (WIFEXITED(status))
        {
            if (WEXITSTATUS(status) == 0)
            {
                b = true;
                for (int i = 0; i < 9; i++)
                {
                    kill(pids[i], SIGKILL);
                }
            }
        }
    }
    return b;
}

Exame 2021

    // grep VmPeak /proc/[pid]/memstats | cut -d" " -f4
    int
    get_memory(int pid)
{
    int pipe_fd[2];
    pipe(pipe_fd);

    // grep VmPeak /proc/[pid]/memstats
    if (fork() == 0)
    {
        close(pipe_fd[0]);
        dup2(pipe_fd[1], STDOUT_FILENO);
        close(pipe_fd[1]);

        char file[64];
        sprintf(file, "/proc/%d/memstats", pid);
        execlp("grep", "grep", "VmPeak", file, NULL);
        _exit(1);
    }

    close(pipe_fd[1]);

    int output_fd[2];
    pipe(output_fd);

    // cut -d" " -f4
    if (fork() == 0)
    {
        dup2(pipe_fd[0], STDIN_FILENO);
        close(pipe_fd[0]);

        dup2(output_fd[1], STDOUT_FILENO);

        execlp("cut", "cut", "-d", " ", "-f4", NULL);
        _exit(1);
    }

    close(pipe_fd[0]);

    char buf[32];
    read(output_fd[0], buf, 32);
    return atoi(buf);
}

void memoria(char *program_name, char *args[])
{
    int values[10];

    for (int i = 0; i < 10; i++)
    {
        int pid = fork();

        if (pid == 0)
        {
            execvp(program_name, args);
            _exit(1);
        }
        else
        {
            int status;
            if (wait(&status) && WIFEXITED(status))
            {
                values[i] = get_memory(pid);
            }
        }
    }

    // Valor mínimo, médio e máximo
    int min, max, total;
    min = max = total = values[0];

    for (int i = 1; i < 10; i++)
    {
        if (values[i] > max)
            max = values[i];
        if (values[i] < min)
            min = values[i];

        total += values[i];
    }

    int med = total / 10;

    char *output = malloc(sizeof(char) * 256);
    sprintf(output, "memória: %d %d %d\n", min, med, max);
    write(STDOUT_FILENO, output, strlen(output));
}

Teste 2022

#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <signal.h>

    int
    mensagens(char *palavra, char *ficheiro)
{
    char buffer[100];
    int resultado = 0;
    int br = 0;

    int p[2][2];
    pipe(p[0]);
    pipe(p[1]);

    if (fork() == 0)
    {
        close(p[0][0]);
        dup2(p[0][1], 1);
        execlp("grep", "grep", palavra, ficheiro, NULL);
        exit(0);
    }
    wait(NULL);
    close(p[0][1]);

    if (fork() == 0)
    {
        close(p[1][0]);
        dup2(p[0][0], 0);
        dup2(p[1][1], 1);
        execlp("wc", "wc", "-l", NULL);
        exit(0);
    }
    wait(NULL);
    close(p[0][0]);
    close(p[1][1]);

    br = read(p[1][0], buffer, sizeof(buffer));
    resultado = atoi(buffer);
    close(p[1][0]);

    return resultado;
}

int autores_que_usaram(char *palavra, int n, char *autores[n])
{
    pid_t pide[n];
    int resultado = 0;

    for (size_t i = 0; i < n; i++)
    {
        if ((pide[i] = fork()) == 0)
        {
            int aux = 0;
            aux = mensagens(palavra, autores[i]);
            exit(aux);
        }
    }

    for (int i = 0; i < n; i++)
    {
        int status;
        pid_t aux = waitpid(pide[i], &status, 0);
        if (WIFEXITED(status))
        {
            if (WEXITSTATUS(status))
                resultado++;
        }
        else
        {
            char buffer[256];
            int bytes = sprintf(buffer, "Erro no ficheiro: %d\n", i);
            write(2, buffer, bytes);
        }
    }

    return resultado;
}