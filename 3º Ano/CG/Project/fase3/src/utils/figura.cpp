#include "figura.hpp"

using namespace std;

struct figura{
    List pontos; // lista de pontos
};

Figura newEmptyFigura(){
    Figura r = (Figura)malloc(sizeof(struct figura));
    if(r != NULL){
        r->pontos = newEmptyList();
    }
    return r;
}

Figura newFigura(List pontos){
    Figura r = newEmptyFigura();
    if(r != NULL){
        for(unsigned long i = 0; i < getListLength(pontos); i++){
            addPonto(r, (Ponto)getListElemAt(pontos,i));
        }
    }
    return r;
}

void addPonto(Figura f, Ponto p){
    if(f){
        addValueList(f->pontos, p);
    }
}

void addPontos(Figura f, Figura toAdd){
    if(f){
        List pontos = toAdd->pontos;
        for(unsigned long i = 0; i < getListLength(pontos); i++){
            addValueList(f->pontos,getListElemAt(pontos,i));
        }
    }
}

void figuraToFile(Figura f, const char* path){
    if(!f){
        printf("A figura está vazia na chamada a FiguraToFile.\n");
        return;
    }
    FILE* file = fopen(path, "w");
    if (!file){
        printf("Ocorreu um erro na abertura do ficheiro '%s'\n", path);
        return;
    }
    fprintf(file,"%lu\n",getListLength(f->pontos));
    for(unsigned long i = 0; i < getListLength(f->pontos); i++){
        Ponto p = (Ponto)getListElemAt(f->pontos, i);
        fprintf(file, "%g,%g,%g\n", getX(p), getY(p), getZ(p));
    }
    fclose(file);
}

Figura fileToFigura(const char* path) {
    Figura f = newEmptyFigura();
    FILE* file = fopen(path, "r");
    if (f && file) {
        char buffer[1024];
        fgets(buffer, 1023, file);
        int vertices = atoi(buffer);
        float x, y, z;
        for (int i = 0; i < vertices; i++) {
            fgets(buffer, 1023, file);
            sscanf(buffer, "%f,%f,%f", &x, &y, &z);
            addPonto(f, newPonto(x, y, z));
        }
        fclose(file);
    }
    return f;
}


List getPontos(Figura f){
    if(f){
        return f->pontos;
    }
    return NULL;
}

void deleteFigura(void* figura){
    if(figura){
        for(unsigned long i = 0; i < getListLength(((Figura)figura)->pontos); i++){
            deletePonto((Ponto)getListElemAt(((Figura)figura)->pontos,i));
        }
        deleteList(((Figura)figura)->pontos);
        free(figura);
    }
}

void deleteFigura2(void* figura){
    if(figura){
        deleteList(((Figura)figura)->pontos);
        free(figura);
    }
}
