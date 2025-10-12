#define _XOPEN_SOURCE
#define _DEFAULT_SOURCE

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/resource.h>
#include <glib-2.0/glib.h>
#include <ctype.h>
#include "../includes/queries.h"
#include "../includes/catalogo.h"
#include "../includes/constructors.h"
#include "../includes/stats.h"

char *fileUsers;
char *fileDrivers;
char *fileRides;

int main(int argc, char const *argv[])
{
    GTree *users = g_tree_new(CompareNames);
    GTree *drivers = g_tree_new(inteiros);
    GTree *rides = g_tree_new(inteiros);
    //mudar depois iterador v
    GTree *topDrivers = g_tree_new(inteiros);
    GTree *topDistUsers = g_tree_new(CompareNames);

    int max_len = 500;
    char *buff = malloc(sizeof(char) * 500);
    int query = 1;
    CATALOGO cat = iniciarCatalogo();
    ESTAT estat = iniciarEstat();

    setUsers(cat, users);
    setDrivers(cat, drivers);
    setRides(cat, rides);
    setTopDrivers(estat, topDrivers);
    setTopDistanceUsers(estat, topDistUsers);


    char *commandFile = "./entrada/commands.txt";
    FILE *file = fopen(commandFile, "r");

    if (file == NULL)
    {
        printf("Ficheiro não encontrado, introduza ficheiro em: ./entrada/commands.txt\n");
        return 0;
    }


    //load de entrada
    fileUsers = malloc(sizeof(char)*300);
    fileDrivers = malloc(sizeof(char)*300);
    fileRides = malloc(sizeof(char)*300);


    strcat(fileUsers, "entrada/users.csv");
    strcat(fileDrivers, "entrada/drivers.csv");
    strcat(fileRides, "entrada/rides.csv");



    //correr commands
    if (argc == 1)
    {
        interpreter(cat, estat);
    }

    if (argc == 2)
    {
        commandInterpreter(cat, estat, argv[1], fileUsers, fileDrivers, fileRides);
    }

    if (strcmp(argv[0], "./testes") == 0)
    {
    }

    //unload
    fclose(file);

    g_tree_destroy(getUsers(cat));
    g_tree_destroy(getDrivers(cat));
    g_tree_destroy(getRides(cat));
    free(cat);
    //free(buff);

    return 1;
}
