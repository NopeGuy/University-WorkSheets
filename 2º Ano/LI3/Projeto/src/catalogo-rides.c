//catalogo-rides
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <glib.h>

#include "../includes/catalogo-rides.h"
#include "../includes/constructors.h"
#include "../includes/catalogo.h"        //ver isto
#include "../includes/main.h"

//id;date;driver;user;city;distance;score_user;score_driver;tip;comment

struct ride {
    int id;
    struct tm date;
    int driver; //id condutor
    char *user; //nome do utilizador
    char *city; //nome da cidade
    double distance; //distancia da viagem arredondada a 3 casas decimais
    double score_user; //score do utilizador
    double score_driver; //score do condutor
    double tip; // gorjeta que o utlizador deixa
    char *comment;
    int lnNumber; //linha da rides.csv
};

//getters


int getRidesid(RIDES a){
    return a->id;
}

struct tm getRidesDate(RIDES a){
    return a->date;
}

int getRidesDriver(RIDES a){
    return a->driver;
}

char *getRidesUser(RIDES a){
    return a->user;
}

char *getRidesCity(RIDES a){
    return a->city;
}

double getRidesDistance(RIDES a){
    return a->distance;
}

double getRidesScoreUser(RIDES a){
    return a->score_user;
}

double getRidesScoreDriver(RIDES a){
    return a->score_driver;
}

double getRidesTip(RIDES a){
    return a->tip;
}

char *getRidesComment(RIDES a){
    return strdup(a->comment);
}


void buildRides (char* line, CATALOGO cat, ESTAT estat, int nLinha){
GTree* t = NULL;
t = getRides(cat);                          //por fazer
char* buffR = line;
RIDES temp = malloc(sizeof(struct ride));
//struct tm date = {0};

int id_temp = atoi(strsep(&buffR,";\n"));
if(id_temp == 0) return;
struct tm date_temp = verifyTime(strsep(&buffR, ";\n"));
if(date_temp.tm_year==0) return;
int driver_temp = atoi(strsep(&buffR,";\n"));
if(driver_temp == 0) return;
char *user_temp = strsep(&buffR,";\n");
if(user_temp == 0) return;
char *city_temp = strsep(&buffR,";\n");
if(city_temp == 0) return;
double distance_temp = atof(strsep(&buffR,";\n"));
double score_user_temp = atof(strsep(&buffR,";\n"));
double score_driver_temp = atof(strsep(&buffR,";\n"));
double tip_temp = atof(strsep(&buffR,";\n"));

//Passar para tree temporária
temp->id = id_temp;
temp->date = date_temp;
temp->driver = driver_temp;
temp->user = strdup(user_temp);
temp->city = strdup(city_temp);
temp->distance = distance_temp;
temp->score_user = score_user_temp;
temp->score_driver = score_driver_temp;
temp->tip = tip_temp;
temp->lnNumber = nLinha;
//inserir na glib

g_tree_insert(t, GINT_TO_POINTER(id_temp), temp);

//buildEstatDriver(estat, driver_temp, score_driver_temp, city_temp);
buildEstatUser(estat, user_temp, distance_temp);
}

void loadRides(char* filename, CATALOGO cat, ESTAT estat){
    int max_len = 2000;
    char buff[max_len];
    FILE *f = fopen(filename, "r");
    if(f == NULL){
        printf("Ficheiro não encontrado: %s\n", filename);
        return;
    }
    fgets(buff,max_len,f); //primeira linha
    int line=0;
    while(fgets(buff,max_len, f)){
        buildRides(buff,cat, estat, line);
        line++;
    }


    fclose(f);
}


