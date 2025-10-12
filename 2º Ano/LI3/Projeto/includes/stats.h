//stats
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <glib.h>

typedef struct statcity* STATSCITY;
typedef struct stat2* STATSDRIVERS;
typedef struct stat3* STATSUSERS;

gint compare_statDrivers(gconstpointer a, gconstpointer b);
gint compare_statUsers(gconstpointer a, gconstpointer b);

double calcula_media(int total_r, double total_a);
int getId(STATSDRIVERS d);
int getTotalr(STATSDRIVERS d);
void setTotalr(STATSDRIVERS d, int tr);
double getTotala(STATSDRIVERS d);
void setTotala(STATSDRIVERS d, double ta);
char* getCity(STATSDRIVERS d);
double getDist(STATSUSERS u);
void setDist(STATSUSERS u, double d);
gint compare_by_distance(gconstpointer a, gconstpointer b, gpointer user_data)


buildEstatUser(estat, user_temp, distance_temp);
