#ifndef PROJETO_CONSTRUCTORS_H
#define PROJETO_CONSTRUCTORS_H

char* intToString(int i);

int stringNotInArray(char* arr[], int iMaxArr, char* palavra);

struct tm verifyTime (char* str);

char* removeXChars(char* str, int removeN);

int asterixToInt(int* i);

int* toIntAsterix(int i);

void writeLineToFile(char* fileName, char* write, int firstTime);

int getAge(struct tm date);

struct tm verifyTime (char* str);

char* toUppercase(char* buff);

void saveToFile(char* fileName, char write[200000], int firstTime);

#endif //PROJETO_CONSTRUCTORS_H
