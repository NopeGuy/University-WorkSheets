#include <stdio.h>
#include <string.h>
#include <sys/ioctl.h>
#include <unistd.h>
#include <stdlib.h>
#include <glib.h>



typedef struct catalogo *CATALOGO;
typedef struct  estat *ESTAT;


//Getters

GTree* getUsers(CATALOGO cat);
GTree* getRides(CATALOGO cat);
GTree* getDrivers(CATALOGO cat);
GTree* getTopDrivers (ESTAT estat);
GTree* getTopDistanceUsers(ESTAT estat);

//Setters

void setUsers(CATALOGO cat, GTree* t);
void setRides(CATALOGO cat, GTree* t);
void setDrivers(CATALOGO cat, GTree* t);
void setTopDrivers(ESTAT estat, GTree *t);
void setTopDistanceUsers(ESTAT estat, GTree *t);

//Funcoes

CATALOGO iniciarCatalogo();
ESTAT iniciarEstat();