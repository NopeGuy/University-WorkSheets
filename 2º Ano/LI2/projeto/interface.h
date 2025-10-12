/**
@file interface.h
Inicialização/configuração do tabuleiro e jogadas
*/

#define n 8
#ifndef RASTROS_BOARD_H
#define RASTROS_BOARD_H

#include "camadadedados.h"

#include <stdbool.h>
#include "camadadedados.h"

/** \brief Esta função constrói os tabuleiros.
 * Esta função funciona através de ciclos onde o primeiro ciclo constroi a primeira linha  do tabuleiro, sendo q isto só se  repete até i < n.
 * ou seja até i < 8 construindo assim um tabuleiro com 8 linhas e colunas.
 * No fim é aplicado printf ("\n ") para introduzir espaços entre o tabuleiro e onde o jogador insere a jogada.
 */

void printBoard(ESTADO *e);

/** Função de verificação e inicialização
        *Esta Função verifica se a jogada intruduzida é valida.Usa também os comandos "gr" , "ler" e "movs"
Lê o estado do jogo atraves do ficheiro guardado com "gr", e aplica a função movs.
*
* @return
*/

int interpretador(ESTADO *e);

extern bool finish;

#endif
