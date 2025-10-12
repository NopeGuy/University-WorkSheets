# Define o compilador
CC = gcc

# Define as flags do compilador
CFLAGS = -Wall -Wextra -pedantic -std=c11

# Define as diretorias
SRC_DIR = ./src
INC_DIR = ./includes

# Define os ficheiros de origem
CLIENT_SRC = $(SRC_DIR)/client.c
DAEMON_SRC = $(SRC_DIR)/daemon.c
MESSAGE_SRC = $(SRC_DIR)/message.c
GROUP_SRC = $(SRC_DIR)/group.c

# Define os nomes dos executáveis
CLIENT_TARGET = client
DAEMON_TARGET = daemon

all: clean $(CLIENT_TARGET) $(DAEMON_TARGET)

$(CLIENT_TARGET): $(CLIENT_SRC) $(MESSAGE_SRC) $(GROUP_SRC)
	$(CC) $(CFLAGS) -I$(INC_DIR) -o $(CLIENT_TARGET) $(CLIENT_SRC) $(MESSAGE_SRC) $(GROUP_SRC)

$(DAEMON_TARGET): $(DAEMON_SRC) $(MESSAGE_SRC) $(GROUP_SRC)
	$(CC) $(CFLAGS) -I$(INC_DIR) -o $(DAEMON_TARGET) $(DAEMON_SRC) $(MESSAGE_SRC) $(GROUP_SRC)

clean:
	rm -f $(CLIENT_TARGET) $(DAEMON_TARGET)
