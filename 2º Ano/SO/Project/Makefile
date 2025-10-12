# Define variables for folder and file paths
SRC_DIR := ./src
OBJ_DIR := ./obj
BIN_DIR := ./bin
TMP_DIR := ./tmp
FIFO_DIR := ./fifos

# Define the targets and dependencies
all: folders server client

server: $(BIN_DIR)/monitor
client: $(BIN_DIR)/tracer

# Create the necessary folders
folders:
	@mkdir -p $(SRC_DIR) $(OBJ_DIR) $(BIN_DIR) $(TMP_DIR) $(FIFO_DIR)

# Compile monitor.c into a binary called monitor
$(BIN_DIR)/monitor: $(OBJ_DIR)/monitor.o
	gcc -g $(OBJ_DIR)/monitor.o -o $(BIN_DIR)/monitor

# Compile monitor.o from monitor.c
$(OBJ_DIR)/monitor.o: $(SRC_DIR)/monitor.c
	gcc -Wall -g -c $(SRC_DIR)/monitor.c -o $(OBJ_DIR)/monitor.o

# Compile tracer.c and tracer.h into a binary called tracer
$(BIN_DIR)/tracer: $(OBJ_DIR)/tracer.o
	gcc -g $(OBJ_DIR)/tracer.o -o $(BIN_DIR)/tracer

# Compile tracer.o from tracer.c
$(OBJ_DIR)/tracer.o: $(SRC_DIR)/tracer.c $(SRC_DIR)/tracer.h
	gcc -Wall -g -c $(SRC_DIR)/tracer.c -o $(OBJ_DIR)/tracer.o

# Clean the project
clean:
	rm -f $(OBJ_DIR)/* $(TMP_DIR)/* $(BIN_DIR)/*

# Create a fifo directory
fifo:
	mkdir -p $(FIFO_DIR)
