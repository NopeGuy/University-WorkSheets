#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <glib.h>
#include "catalogo.h"


typedef struct user* USER;

char *getUsername(USER u);

char *getName(USER u);

char getGender(USER u);

struct tm getBirth_date(USER u);

struct tm getAccount_creation(USER u);

char getPay_method(USER u);

char getAccount_status(USER u);

void buildUsers(char* line, CATALOGO cat);

void loadUsers(char *fileName, CATALOGO cat);