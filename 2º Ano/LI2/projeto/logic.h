/**
@file logic.h
Inicialização/configuração do tabuleiro e jogadas
*/



#ifndef RASTROSPROJECT_LOGIC_H
#define RASTROSPROJECT_LOGIC_H

#include "camadadedados.h"

/** \brief Função que verifica quando o jogo se dá por terminado.
  * Esta divide-se em 2 casos , onde o primeiro dá vitoria ao player 1 ou 2 casos
* estes cheguem as casas [0][7] ou [7][0]
* consideramos no segundo caso , a hipótese de um player ficar rodeado por # , dando assim a vitória ao outro jogador.
* o check faz com que caso seja o player 1 rodeado entao com o check a vitoria será dada ao player 2 e vice-versa
  * A Parte final da função verifica se as casas á volta estão vazias ou não , se sim , então o valor do check passa a 0 , caso contrário passa a 1
  */

int check_finish(char letter, char number, ESTADO *e);

/** \brief  Função sobres as jogadas, funcionalidade e dados do jogo.
 * Esta função considera se uma jogada de um jogador é válida ou não e move a peça branca, caso a casa esteja vazia então a jogada é valida.
 * As funçoes ultima_jogada.linha , ultima_jogada.coluna , jogador atual , num_jogadas, jogador2.coluna/linhas e jogador1.coluna/linha são funções de estado que guardam o valor, que depois sempre que sao chamadas através do e->"..." aplica esse valor que estava guardado.
 * Por fim esta função verifica se uma jogada é impossivel ou não, isto é, se a casa existe, ou se a casa que o jogador quer jogar é consecutiva aquela onde ele se situa.
*/

int add_position(char letter, char number, ESTADO *e);

#endif //RASTROSPROJECT_LOGIC_H