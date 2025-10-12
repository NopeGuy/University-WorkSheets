#ifndef INTERPRETER_H
#define INTERPRETER_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/resource.h>
#include <glib.h>
#include <ctype.h>

#include "main.h"
#include "catalogo-users.h"
#include "catalogo-drivers.h"
#include "catalogo-rides.h"
#include "queries.h"
#include "constructors.h"

void interpreter(CATALOGO cat, ESTAT estat);

void commandInterpreter(CATALOGO cat, char *filename, int writeToFile, char *fileUsers, char *fileDrivers, char *fileRides);

#endif /* INTERPRETER_H */
