#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <sys/types.h>
#include <fcntl.h>

int main(int argc, char const *argv[])
{
    pid_t pid;
    int status;
    int nproc = 10;

    for(int i=1; i<= nproc; i++){
        if(((pid = fork()) == 0)){
            printf("[proc #%d] pid: %d\n", i, getpid());
            _exit(i);
        }
    }

    for(int i=1; i<= nproc; i++){
        pid_t terminated_pid = wait(&status);

        if(WIFEXITED(status)){
            printf("[pai] process %d exited. exit code %d\n", terminated_pid, WEXITSTATUS(status));
    
        }
        else{
            printf("[pai] process %d exited. \n", terminated_pid);
        }
    }
}

