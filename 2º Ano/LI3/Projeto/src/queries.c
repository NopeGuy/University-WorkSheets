#define _XOPEN_SOURCE
#define _DEFAULT_SOURCE
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <glib.h>

#include "../includes/queries.h"
#include "../includes/catalogo-users.h"
#include "../includes/catalogo-rides.h"
#include "../includes/catalogo-drivers.h"
#include "../includes/constructors.h"
#include "../includes/catalogo.h"
#include "../includes/stats.h"

// structs auxiliares

// funcoes auxiliares

// balanceia a tree
gint inteiros(gconstpointer a, gconstpointer b)
{
    if (a > b)
        return 1;
    if (a < b)
        return -1;
    if (a == b)
        return 0;
}

gint CompareNames(gconstpointer name1, gconstpointer name2)
{
    return (strcmp(name1, name2));
}

// funcoes_aux Query4

int verificaCidade(char cidade, CATALOGO cat, int id_driver)
{
    GTree *temp = NULL;
    temp = getDrivers(cat);
    int *idDriver = toIntAsterix(id_driver);
    int *alvo = g_tree_lookup(temp, idDriver);
    if (cidade == getDriversCity(alvo))
    {
        g_tree_destroy(temp);
        return 1;
    }
    g_tree_destroy(temp);
    return 0;
}

char *verificaClasse(int id_driver, CATALOGO cat)
{
    GTree *temp = NULL;
    temp = getDrivers(cat);
    int *idDriver = toIntAsterix(id_driver);
    int *alvo = g_tree_lookup(temp, idDriver);
    char *tarifa = getDriversCarClass(alvo);
    g_tree_destroy(temp);
    return tarifa;
}

// funcao que remove todos os nodos que não são daquela cidade (para ser usada numa g_tree_foreach())
//(q4->temp_drivers,removePorCidade,q4)

// chamadas da main

double getTripPrice(DRIVERS *treeDrivers, int driver_id, int km)
{
    DRIVERS driver = g_tree_lookup(treeDrivers, driver_id);
    int carClass = getDriversCarClass(driver);

    if (carClass == 1)
    {
        return 4.00 + 0.79 * km;
    }
    if (carClass == 0)
    {
        return 3.25 + 0.62 * km;
    }
    if (carClass == 2)
    {
        return 5.20 + 0.94 * km;
    }
}

// Query 1
struct trip_iter
{
    CATALOGO cat;
    char *username;
    int id;
    double price_sum;
    double ranking_sum;
    int total_drives;
};

typedef struct trip_iter *TRIP_ITER;

gboolean conta_viagens(gpointer key, gpointer value, gpointer data)
{
    TRIP_ITER trip_iterator = (TRIP_ITER)data;
    RIDES ride = (RIDES)value;
    GTree *driversTree = getDrivers(trip_iterator->cat);

    if (trip_iterator->id != -1)
    {
        if (getRidesDriver(ride) == trip_iterator->id)
        {
            double tripPrice = getTripPrice(driversTree, getRidesDriver(ride), getRidesDistance(ride));
            trip_iterator->price_sum += tripPrice;
            trip_iterator->total_drives++;
            trip_iterator->ranking_sum += getRidesScoreUser(ride);
        }
    }
    else
    {

        if (strcmp(getRidesUser(ride), trip_iterator->username) == 0)
        {
            double tripPrice = getTripPrice(driversTree, getRidesDriver(ride), getRidesDistance(ride));
            trip_iterator->price_sum += tripPrice;
            trip_iterator->total_drives++;
            trip_iterator->ranking_sum += getRidesScoreUser(ride);
        }
    }
    return FALSE;
}

char *profilefromUsername(CATALOGO cat, char *username, FILE *dest, int i)
{
    USER u = g_tree_lookup(getUsers(cat), username);

    if (u == NULL)
    {
        fprintf(dest, "%s", "Utilizador nao encontradoo.");
        return "";
    }

    TRIP_ITER trip = malloc(sizeof(struct trip_iter));
    trip->username = username;
    trip->id = -1;
    trip->cat = cat;
    trip->total_drives = 0;
    trip->ranking_sum = 0.000;
    trip->price_sum = 0.000;

    g_tree_foreach(getRides(cat), conta_viagens, trip);

    if (i == 1)
    {
        printf("\n%s ;", getName(u));
        printf("%c ;", getGender(u));
        printf("%d ;", getAge(getBirth_date(u)));
        printf("%0.3f ;", trip->ranking_sum / trip->total_drives);
        printf("%d ;", trip->total_drives);
        printf("%0.3f ;", trip->price_sum);
    }
    else
    {
        fprintf(dest, "%s", getName(u));
        fprintf(dest, ";");
        fprintf(dest, "%c", getGender(u));
        fprintf(dest, ";");
        fprintf(dest, "%d", getAge(getBirth_date(u)));
        fprintf(dest, ";");
        fprintf(dest, "%0.3f", trip->ranking_sum / trip->total_drives);
        fprintf(dest, ";");
        fprintf(dest, "%d", trip->total_drives);
        fprintf(dest, ";");
        fprintf(dest, "%0.3f", trip->price_sum);
    }

    free(trip);

    return "";
}

char *profilefromID(CATALOGO cat, int id_condutor, FILE *dest, int i)
{
    DRIVERS d = g_tree_lookup(getDrivers(cat), id_condutor);

    if (d == NULL)
    {
        fprintf(dest, "%s", "Driver nao encontrado.");
        return "";
    }

    TRIP_ITER trip = malloc(sizeof(struct trip_iter));
    trip->id = id_condutor;
    trip->cat = cat;
    trip->total_drives = 0;
    trip->ranking_sum = 0.000;
    trip->price_sum = 0.000;

    g_tree_foreach(getRides(cat), conta_viagens, trip);

    if (i == 1)
    {
        printf("\n%s ;", getDriversName(d));
        printf("%c ;", getDriversGender(d));
        printf("%d ;", getAge(getDriversBirthDay(d)));
        printf("%0.3f ;", trip->ranking_sum / trip->total_drives);
        printf("%d ;", trip->total_drives);
        printf("%0.3f ;", trip->price_sum);
    }
    else
    {
        fprintf(dest, "%s", getDriversName(d));
        fprintf(dest, ";");
        fprintf(dest, "%c", getDriversGender(d));
        fprintf(dest, ";");
        fprintf(dest, "%d", getAge(getDriversBirthDay(d)));
        fprintf(dest, ";");
        fprintf(dest, "%0.3f", trip->ranking_sum / trip->total_drives);
        fprintf(dest, ";");
        fprintf(dest, "%d", trip->total_drives);
        fprintf(dest, ";");
        fprintf(dest, "%0.3f", trip->price_sum);
    }

    free(trip);

    return "";
}

// Query 2


// void topUsersDistance(CATALOGO cat, ESTAT estat, int n, FILE *f, int i)
// {
//     GTree *tree1 = NULL;
//     tree1 = getTopDistanceUsers(estat);
//     gint x = (gint)n;
//     GSList *top_n = NULL;

//     g_tree_foreach(tree1, (GTraverseFunc)g_slist_prepend, &top_n);
//     top_n = g_slist_sort_with_data(top_n, compare_by_distance, NULL);

//     gint count = 0;
//     GSList *node = top_n;
//     while (node && count < x)
//     {
//         STATSUSERS nd = (STATSUSERS)node->data;
//         if (i == 1)
//         {
//             printf("Name: %s\n Distance: %f\n", nd->name, nd->dist);
//         }
//         else
//         {
//             fprintf(f, "Name: %s\n Distance: %f\n", nd->name, nd->dist);
//         }
//         node = node->next;
//         count++;
//     }

//     if (node)
//     {
//         g_slist_free_full(node->next, g_free);
//         node->next = NULL;
//     }
// }

// Query 3

// Query 4

struct cities_iter
{
    CATALOGO cat;
    char *city;
    double preco_total;
    int n_cidades;
};
typedef struct cities_iter *CITIES_ITER;

gboolean city_iter(gpointer key, gpointer value, gpointer data)
{
    CITIES_ITER cities_iter = (CITIES_ITER)data;
    RIDES ride = (RIDES)value;
    GTree *driversTree = getDrivers(cities_iter->cat);

    if (strcmp(getRidesCity(ride), cities_iter->city) == 0)
    {
        double tripPrice = getTripPrice(driversTree, getRidesDriver(ride), getRidesDistance(ride));
        cities_iter->preco_total += tripPrice;
        cities_iter->n_cidades++;
    }
    return FALSE;
}

void medianPrice(CATALOGO cat, char *cidade, FILE *f, int i)
{
    // inicialização da struct aux query4
    CITIES_ITER cities_iter = malloc(sizeof(struct cities_iter));
    cities_iter->cat = cat;
    cities_iter->city = cidade;
    cities_iter->preco_total = 0.000;
    cities_iter->n_cidades = 0;

    g_tree_foreach(getRides(cat), city_iter, cities_iter);


        if (cities_iter->n_cidades == 0)
        {
            if(i==1) printf("%d\n", "0");
            if(i==0)fprintf(f, "%d\n", "0");
            return;
        }
        else
        {
            double preco_medio = cities_iter->preco_total / cities_iter->n_cidades;
            if(i==1) printf("resultado: %0.3f", preco_medio);
            if(i==0) fprintf(f, "resultado: %0.3f", preco_medio);
        }
    

    free(cities_iter);
}

// Query 5

struct median_between_iter
{
    CATALOGO cat;
    time_t date1;
    time_t date2;
    double preco_total;
    int nnodes;
};
typedef struct median_between_iter *MEDIAN_BETWEEN_ITER;

gboolean median_between_iter(gpointer key, gpointer value, gpointer data)
{
    MEDIAN_BETWEEN_ITER median_iter = (MEDIAN_BETWEEN_ITER)data;
    RIDES ride = (RIDES)value;
    GTree *driversTree = getDrivers(median_iter->cat);
    time_t date1 = median_iter->date1;
    time_t date2 = median_iter->date2;
    struct tm date_trip = getRidesDate(ride);
    time_t t = mktime(&date_trip);
    if (difftime(t, date1) >= 0 && difftime(date2, t) >= 0)
    {
        double tripPrice = getTripPrice(driversTree, getRidesDriver(ride), getRidesDistance(ride));
        median_iter->nnodes++;
        median_iter->preco_total += tripPrice;
    }
    return FALSE;
}

void medianPriceBetween(CATALOGO cat, struct tm date1, struct tm date2, FILE *f, int i)
{
    MEDIAN_BETWEEN_ITER median_iter = malloc(sizeof(struct median_between_iter));
    time_t t1 = mktime(&date1);
    time_t t2 = mktime(&date2);
    median_iter->cat = cat;
    median_iter->date1 = t1;
    median_iter->date2 = t2;
    median_iter->preco_total = 0;
    median_iter->nnodes = 0;

    g_tree_foreach(getRides(cat), median_between_iter, median_iter);

    {
    if (median_iter->nnodes == 0)
    {
            if(i==0) fprintf(f, "%d\n", "0");
            if(i==1) printf("%d\n", "0");
            return;
    }

    double preco_medio = median_iter->preco_total / median_iter->nnodes;

    if (i==0) fprintf(f, "%0.3f", preco_medio);
    if (i== 1) printf("\nresultado: %0.3f", preco_medio);
    }


    free(median_iter);
}

// Query 6

struct dist_city_iter_between
{
    GTree *rides;
    char *city;
    time_t date1;
    time_t date2;
    double dist_total;
    int n;
};
typedef struct dist_city_iter_between *CITY_BETWEEN_ITER;

gboolean median_dist_iter(gpointer key, gpointer value, gpointer data)
{
    CITY_BETWEEN_ITER city_between_iter = (CITY_BETWEEN_ITER)data;
    RIDES ride = (RIDES)value;
    if (strcmp(getRidesCity(ride), city_between_iter->city) == 0)
    {
        struct tm date_trip = getRidesDate(ride);
        time_t t = mktime(&date_trip);
        time_t date1 = city_between_iter->date1;
        time_t date2 = city_between_iter->date2;
        if (difftime(t, date1) >= 0 && difftime(date2, t) >= 0)
        {
            city_between_iter->n++;
            city_between_iter->dist_total += getRidesDistance(ride);
        }
    }
    return FALSE;
}

void medianDistBetween(GTree *rides, char *city, struct tm date1, struct tm date2, FILE *f, int i)
{
    CITY_BETWEEN_ITER city_between_iter = malloc(sizeof(struct dist_city_iter_between));
    time_t t1 = mktime(&date1);
    time_t t2 = mktime(&date2);
    city_between_iter->rides = rides;
    city_between_iter->city = city;
    city_between_iter->date1 = t1;
    city_between_iter->date2 = t2;
    city_between_iter->dist_total = 0;
    city_between_iter->n = 0;

    g_tree_foreach(rides, median_dist_iter, city_between_iter);
    if (city_between_iter->n == 0)
    {
        fprintf(f, "%d", "0\n");
        return;
    }
    double dist_media = city_between_iter->dist_total / city_between_iter->n;

    if(i==0)fprintf(f, "%0.3f", dist_media);
    if(i==1)printf("\n %0.3f", dist_media);

    free(city_between_iter);
}

// Query 9
typedef struct tip_iter
{
    FILE *f;
    GTree *rides;
    time_t data_i;
    time_t data_f;
} *TIP_ITER;

gboolean tip_iter_between(gpointer key, gpointer value, gpointer data)
{
    TIP_ITER tip_iter = (TIP_ITER)data;
    RIDES ride = (RIDES)value;
    struct tm date_trip = getRidesDate(ride);
    time_t t = mktime(&date_trip);
    if (difftime(t, tip_iter->data_i) >= 0 && difftime(tip_iter->data_f, t) >= 0)
    {
        if (getRidesTip(ride) > 0)
        {
            fprintf(tip_iter->f, "%d;%d/%d/%d;%f;%s;%f\n", getRidesid(ride), getRidesDate(ride).tm_mday, getRidesDate(ride).tm_mon, getRidesDate(ride).tm_year, getRidesDistance(ride), getRidesCity(ride), getRidesTip(ride));
        }
        return FALSE;
    }
}

void tipBetween(CATALOGO cat, struct tm date1, struct tm date2, FILE *f, int i)
{
    TIP_ITER tip_iter = malloc(sizeof(struct tip_iter));
    time_t t1 = mktime(&date1);
    time_t t2 = mktime(&date2);
    tip_iter->f = f;
    tip_iter->rides = getRides(cat);
    tip_iter->data_i = t1;
    tip_iter->data_f = t2;

    g_tree_foreach(getRides(cat), tip_iter_between, tip_iter);
    free(tip_iter);
}