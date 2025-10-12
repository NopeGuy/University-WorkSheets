#include "list.hpp"

struct list{
    void** arr;// é um void** porque é um array de apontadores e não um array de tipos concretos
    unsigned long length; // espaços ocupados do array
    unsigned long capacity; // tamanho total do array
};

List newEmptyList(){
    List newL = (List)malloc(sizeof(struct list));
    if(newL){ // não ocorreu erros a criar o newL
        newL->arr = (void**)calloc(STDSIZE, sizeof(void*));
        if(newL->arr){ // não ocorreu erros a criar o newL->arr
            newL->length = 0;
            newL->capacity = STDSIZE;
        }else{
            free(newL);
            newL = NULL;
        }
    }
    return newL;
}

int listIsFull(List list){
    if(list){
        return list->length == list->capacity;
    }
    return 1;
}

void addValueList(List list, void* value){
    if(list){
        if(listIsFull(list)){// a lista está cheia -> temos de aumentar o espaço disponível
            list->capacity += STDSIZE;
            list->arr = (void**)realloc(list->arr,sizeof(void*)*list->capacity);
        }
        list->arr[list->length++] = value;
    }
}

void** getListValues(List list){
    if(list){
        return list->arr;
    }
    return NULL;
}

unsigned long getListLength(List list){
    if(list){
        return list->length;
    }
    return 0;
}

void* getListElemAt(List list, unsigned long index){
    if(list){
        if(0 <= index && index < list->length){
            return list->arr[index];
        }
    }
    return NULL;
}

void deleteList(List list){
    if(list){
        free(list->arr);
        free(list);
    }
}

void deepDeleteList(List list, void (*free_function)(void *)){
    if(list){
        for(unsigned long i = 0; i < list->length; i++){
            free_function(list->arr[i]);
        }
        free(list->arr);
        free(list);
    }
}