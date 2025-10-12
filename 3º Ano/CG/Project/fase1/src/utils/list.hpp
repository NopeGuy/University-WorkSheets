#ifndef LIST
#define LIST
#include <stdlib.h>

#define STDSIZE 100

typedef struct list* List;

List newEmptyList();

int listIsFull(List);

void addValueList(List, void*);

void** getListValues(List);

unsigned long getListLength(List);

void* getListElemAt(List, unsigned long);

void deleteList(List);

void deepDeleteList(List, void (*)(void *));

#endif // LIST