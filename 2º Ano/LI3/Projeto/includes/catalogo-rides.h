//catalogo-rides.h
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <glib.h>
#include "catalogo.h"

typedef struct ride* RIDES;

int getRidesid(RIDES a);

struct tm getRidesDate(RIDES a);

int getRidesDriver(RIDES a);

char *getRidesUser(RIDES a);

char *getRidesCity(RIDES a);

double getRidesDistance(RIDES a);

double getRidesScoreUser(RIDES a);

double getRidesTip(RIDES a);

char *getRidesComment(RIDES a);

void buildRides (char* line, CATALOGO cat, ESTAT estat, int nLinha);

void loadRides(char* filename, CATALOGO cat, ESTAT estat);