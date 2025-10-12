#ifndef FIGURA
#define FIGURA
#include <vector>
#include <stdlib.h>
#include <stdio.h>
#include "ponto.hpp"
#include "list.hpp"

using namespace std;

typedef struct figura* Figura;

Figura newEmptyFigura();

Figura newFigura(List);

void addPonto(Figura, Ponto);

void addPontos(Figura, Figura);

void figuraToFile(Figura, const char*);

Figura fileToFigura(const char*);

List getPontos(Figura);

void deleteFigura(void*);

void deleteFigura2(void*);

#endif // FIGURA