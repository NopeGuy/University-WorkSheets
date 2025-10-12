#include <stdio.h>
#include <time.h>
#include <stdlib.h>
#include "interface.h"
#include "logic.h"

#define BUF_SIZE 1024
bool finish = false;

int main() {
    srand((unsigned int) time(NULL));
    printf("\n      -----------------------\n\tWelcome to RASTROS!\n      -----------------------");
    ESTADO *e = inicializar_estado();
    printBoard(e);
    while (1) {
        if (interpretador(e) == 3) {
            break;
        }
        else if(interpretador(e) == 2){
            finish = true;
        }
        else if(interpretador(e) == 1){
            finish = false;
        }
    }
    return 0;
}

