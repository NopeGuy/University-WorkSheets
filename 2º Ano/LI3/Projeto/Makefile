CC=gcc
CFLAGS=-Wall -Ofast -Wpedantic -Wextra -g `pkg-config --cflags glib-2.0`


BIN     := .
SRC     := src
INCLUDE := .

LIBRARIES   := `pkg-config --libs glib-2.0`
EXECUTABLE  := Run
TESTES      := tests


all: $(BIN)/$(EXECUTABLE) $(BIN)/$(TESTES)

run: clean all
	clear
	./$(EXECUTABLE)

$(BIN)/$(EXECUTABLE): $(SRC)/*.c
	$(CC) $(CFLAGS) -lm  $^ -o $@ $(LIBRARIES) -g

$(BIN)/$(TESTES): $(SRC)/*.c
	$(CC) $(CFLAGS) -lm  $^ -o $@ $(LIBRARIES) -g

clean:
	-rm ./$(EXECUTABLE)
	-rm ./$(TESTES)
