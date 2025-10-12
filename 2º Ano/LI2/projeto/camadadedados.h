/**
@file camadadedados.h
Definição das funções de estado
*/

#ifndef RASTROS_CAMADADEDADOS_H
#define RASTROS_CAMADADEDADOS_H

typedef enum {
    OK,
    COORDENADA_INVALIDA,
    JOGADA_INVALIDA,
    ERRO_LER_TAB,
    ERRO_ABRIR_FICHEIRO,
} ERROS;

/**
\brief Tipo de Dados para as casas do tabuleiro.
 *Dados para cada casa no tabuleiro , as vazias e onde é as casas de vitória e onde se inicia a peça branca.
*/

typedef enum {
    UM = '1',
    DOIS = '2',
    VAZIO = '_',
    BRANCA = 'O',
    PRETA = '#'
} CASA;

/**
\brief Tipo de Dados das colunas e linhas do tabuleiro.
 */

typedef struct {
    int coluna;
    int linha;
} COORDENADA;

/**
\brief Tipo de dados dos jogadores e da devida jogada.
 *Coordenada da jogada de casa jogador.
 */

typedef struct {
    COORDENADA jogador1;
    COORDENADA jogador2;
} JOGADA;

/**
\brief São dados de modificação de estado.
 * É um Prompt que guarda todas as jogadas efetuadas por cada jogador ao longo do jogo.
 */

typedef JOGADA JOGADAS[64];
typedef struct {
    CASA tab[8][8];
    COORDENADA ultima_jogada;
    JOGADAS jogadas;
    int num_jogadas;
    int jogador_atual;
    int num_comando;
} ESTADO;

/**
*\brief Prepara as condiçoes para o inicio do jogo , poe a primeira jogada e define o jogador atual e o numero de jogadas.
*/

ESTADO *inicializar_estado();

int obter_jogador_atual(ESTADO *estado);

int obter_numero_de_jogadas(ESTADO *estado);

CASA obter_estado_casa(ESTADO *e, COORDENADA c);

/**
\brief Muda o valor de uma casa
@param e Apontador para o estado
@param c A coordenada
@param V O novo valor para a casa
*/

void set_casa(ESTADO *e, COORDENADA c, CASA V);

/**
\brief Devolve o valor de uma casa
@param e Apontador para o estado
@param c A coordenada
@returns O valor da casa
*/
CASA get_casa(ESTADO *e, COORDENADA c);


#endif