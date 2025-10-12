// stats
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <glib.h>
#include "../includes/catalogo.h"
#include "../includes/stats.h"

struct statcity
{
    int total_rides;
    double total_a;
    char *city;
};

struct stat2
{
    int id;
    int total_rides;
    double total_a;
    GList *cidades_stats;
};

struct stat3
{
    char *name;
    double dist;
};

int getId(STATSDRIVERS d)
{
    return d->id;
}

int getTotalr(STATSDRIVERS d)
{
    return d->total_rides;
}

void setTotalr(STATSDRIVERS d, int tr)
{
    d->total_rides = tr;
}

double getTotala(STATSDRIVERS d)
{
    return d->total_a;
}

void setTotala(STATSDRIVERS d, double ta)
{
    d->total_a = ta;
}

double calcula_media(int total_r, double total_a)
{
    return total_a / (double)total_r;
}

double getDist(STATSUSERS u)
{
    return u->dist;
}

void setDist(STATSUSERS u, double d)
{
    u->dist = d;
}

gint compare_statDrivers(gconstpointer a, gconstpointer b)
{
    STATSDRIVERS node_a = (STATSDRIVERS)a;
    STATSDRIVERS node_b = (STATSDRIVERS)b;

    if (node_a->total_a < node_b->total_a)
        return -1;
    else if (node_a->total_a > node_b->total_a)
        return 1;
    else
    { // se as avaliacoes forem iguais, ordena por ordem crescente de id
        if (node_a->id < node_b->id)
            return -1;
        else if (node_a->id > node_b->id)
            return 1;
    }
    return 0;
}

gint compare_statUsers(gconstpointer a, gconstpointer b)
{
    STATSUSERS node_a = (STATSUSERS)a;
    STATSUSERS node_b = (STATSUSERS)b;

    if (node_a->dist < node_b->dist)
        return -1;
    else if (node_a->dist > node_b->dist)
        return 1;
    return (strcmp(node_a->name, node_b->name));
}

// gboolean comparecities(gpointer value, gpointer citycmp){
//     STATSCITY city= (STATSCITY)value;
//     char*citycmp = (char) citycmp;
//     if(stcmp(value->city,citycmp)){
//         return TRUE;
//     }
//     return FALSE;
// }

buildEstatUser(ESTAT estat, char *user, double distance)
{
    // pegar valores do id na rides com forEach
    // somar as viagens no iterador
    // inserir na estat
    GTree *t = NULL;
    t = getTopDrivers(estat);

    STATSUSERS temp = malloc(sizeof(struct stat2));
    // STATSUSERS temp2 = malloc(sizeof(struct stat2));

    double x;

    temp->name = user;
    temp->dist = distance;

    if (g_tree_lookup(t, temp->name) != NULL)
    {
        temp->dist = temp->dist + distance;
        // printf("temp->name: %s \n", temp->name);
        // printf("temp->dist: %f \n", temp->dist);
        g_tree_replace_node(t, temp->name, temp);
    }
    else
    {
        // printf("temp->name: %s \n", temp->name);
        // printf("temp->dist: %f \n\n", temp->dist);
        g_tree_insert(t, temp->name, temp);
    }
    free(temp);
}

// gint compare_by_distance(gconstpointer a, gconstpointer b, gpointer user_data)
// {
//     STATSUSERS nd1 = (STATSUSERS)a;
//     STATSUSERS nd2 = (STATSUSERS)b;

//     return (nd2->dist - nd1->dist) > 0 ? 1 : -1;
// }

// void buildEstatDriver(ESTAT estat, int driver, double score, char* city){
//     GTree* t = NULL;
//     t = getEstatDrivers(estat);
//     STATSDRIVERS tempd = malloc(sizeof(struct stat2));
//     STATSCITY tempc = malloc(sizeof(struct statcity));
//     tempd->id = driver;
//     tempd->total_a = score;
//     tempd->total_rides = 0;
//     tempc->total_a = score;
//     tempc->city = strdup(city);
//     if(g_tree_lookup(t, driver) != NULL){
//         tempd = g_tree_lookup(t, driver);
//         tempd->total_a += score;
//         tempd->total_rides += 1;
//         GList* tmp = g_list_find_custom(tempd->cidades_stats, tempc->city, comparecities);
//         if(tmp != NULL){
//             tmp->data->total_rides += 1;
//             tmp->data->total_a += score;
//         }else{
//             g_list_append(tempd->cidades_stats, tempc);
//         }
//         g_tree_insert(t,tempd->id,tempd);
//     }
//     else{
//         g_list_append(tempd->cidades_stats, tempc);
//         g_tree_insert(t,tempd->id,tempd);
//     }

//     free(tempd);
//     free(tempc);

// }