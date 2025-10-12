#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/resource.h>
#include <glib.h>
#include <ctype.h>
#include <time.h>
#include <malloc.h>
#include <sys/resource.h>
#define DATA_FINAL_DIA 9 //VALORES NA getAge()
#define DATA_FINAL_MES 10
#define DATA_FINAL_ANO 2022

char* intToString(int i){
    char* buff = malloc(sizeof(char)*30);
    sprintf(buff, "%d", i);
    return buff;
}


int stringNotInArray(char* arr[], int iMaxArr, char* palavra){
    for(int i = 0; i<iMaxArr; i++)
        if(strcmp(palavra, arr[i]) == 0)
            return 1;
    return 0;
}

char* removeXChars(char* str, int removeN){
    char* newString = malloc(sizeof(char)*400000);
    int nI = 0;
    for(int i = 0;i<strlen(str);i++){
        if(i >= removeN){
            newString[nI++] = str[i];
        }
    }

    free(str);
    return newString;
}

char* toUppercase(char* buff){
    char * name;
    name = strtok(buff,"");

    // Convert to upper case
    char *s = name;
    while (*s) {
        *s = toupper((unsigned char) *s);
        s++;
    }
}


struct tm verifyTime (char* str)
{
    struct tm tempo = {0};
    if(strlen(str)!=10) tempo.tm_year=0;
       strptime(str, "%d/%m/%Y", &tempo);

    /*
       if(tempo.tm_mday < 1 || tempo.tm_mday > 31) tempo.tm_year = 0; // verificar dia 
       if(tempo.tm_mon < 1 || tempo.tm_mon > 12) tempo.tm_year = 0; // verificar mês
       if(tempo.tm_year < 1 || tempo.tm_year > DATA_FINAL_ANO) tempo.tm_year = 0; //verificar ano
       if((tempo.tm_year == DATA_FINAL_ANO && tempo.tm_mon > DATA_FINAL_MES) ||    //verifica se ultrapassa a data final estipulada no enunciado
          (tempo.tm_year == DATA_FINAL_ANO && tempo.tm_mon == DATA_FINAL_MES && tempo.tm_mday > DATA_FINAL_DIA)) tempo.tm_year = 0;     
        */
       tempo.tm_year += 1900;
    return tempo;
}


int getAge(struct tm date){
    struct tm atual = verifyTime("9/10/2022");

    int s = (int) difftime(mktime(&atual), mktime(&date));
    return (s/31556952);

}

int asterixToInt(int* i){
    return *i;
}

int* toIntAsterix(int i){
    int* ret = malloc(sizeof(int*));
    *ret = i;
    return ret;
}

void saveToFile(char* fileName, char write[200000], int firstTime) {
    FILE *f;
    if(firstTime == 0) f = fopen(fileName, "a");
    if(firstTime == 1) f = fopen(fileName, "w");

    if (f == NULL)
    {
        printf("Nao foi possivel abrir o ficheiro: %s\n", fileName);
        return;
    }

    for(int i = 0; i<strlen(write); i++){
        fprintf(f, "%c", write[i]);
    }



    fclose(f);

    printf("%s alterado com sucesso !\n", fileName);
}

void writeLineToFile(char* fileName, char* write, int firstTime) {
   
    FILE *f = fopen(fileName, "a");
    if(firstTime == 1) f = fopen(fileName, "w");

    if (f == NULL)
    {
        printf("Nao foi possivel abrir o ficheiro: %s\n", fileName);
        return;
    }

    fprintf(f, "%s", write);
    fclose(f);

}
