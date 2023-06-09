///////////////////////////////////////
//
//     Problema de corte de stock
//
///////////////////////////////////////

/* Função objectivo 1: minimizar o número de rolos a usar */
min: 1 x1 + 1 x2 + 1 x3 + 1 x4 + 1 x5 + 1 x6 + 1x7 + 1 x8 ;

/* Função objectivo 2: minimizar o desperdício existente nos rolos        */
/*                     satisfazendo a procura                             */
// min: 50x1 +150x2       +100x4 +200x5 +200x6        +100x8;
/* Há situações em que as duas funções objectivo conduzem à mesma solução */
/* Noutras situações, as soluções podem ser diferentes                    */

L550: 1 x1 + 1 x2                                           >=  50;
L500:               2 x3 + 1 x4 + 1 x5                      >= 200;
L400: 1 x1               + 1 x4        + 2 x6 + 1 x7        >= 400;
L300:      + 1 x2               + 1 x5        + 2 x7 + 3 x8 >= 100;
   
