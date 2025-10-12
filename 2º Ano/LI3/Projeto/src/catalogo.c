#include <stdio.h>
#include <string.h>
#include <sys/ioctl.h>
#include <unistd.h>
#include <stdlib.h>
#include <glib.h>
#include "../includes/catalogo.h"

struct catalogo
{
    GTree* users;
    GTree* drivers;
    GTree* rides;
};

struct estat
{
    GTree* topDrivers;
    GTree* topDistanceUsers;
};

CATALOGO iniciarCatalogo(){
    CATALOGO c = malloc(sizeof(struct catalogo));
    c->drivers = NULL;
    c->users = NULL;
    c->rides = NULL;
    return c;
}

ESTAT iniciarEstat(){
    ESTAT e = malloc(sizeof(struct estat));
    e->topDrivers = NULL;
    e->topDistanceUsers = NULL;
    return e;
}

GTree* getUsers(CATALOGO cat){
    return cat->users;
}
GTree* getDrivers(CATALOGO cat){
    return cat->drivers;
}
GTree* getRides(CATALOGO cat){
    return cat->rides;
}

GTree *getTopDrivers(ESTAT estat)
{
    return estat->topDrivers;
}
GTree *getTopDistanceUsers(ESTAT estat)
{
    return estat->topDistanceUsers;
}

void setUsers(CATALOGO cat, GTree* t){
    cat->users = t;
}
void setDrivers(CATALOGO cat, GTree *t)
{
    cat->drivers = t;
}
void setRides(CATALOGO cat, GTree *t)
{
    cat->rides = t;
}
void setTopDrivers(ESTAT estat, GTree *t)
{
    estat->topDrivers = t;
}
void setTopDistanceUsers(ESTAT estat, GTree *t)
{
    estat->topDistanceUsers = t;
}
