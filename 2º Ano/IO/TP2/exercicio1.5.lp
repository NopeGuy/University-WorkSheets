///////////////////////////////////////
//
//     Problema de corte de stock
//
///////////////////////////////////////

/* Fun��o objectivo 1: minimizar o n�mero de rolos a usar */
min: 1 x1 + 1 x2 + 1 x3 + 1 x4 + 1 x5 + 1 x6 + 1x7 + 1 x8 ;

/* Fun��o objectivo 2: minimizar o desperd�cio existente nos rolos        */
/*                     satisfazendo a procura                             */
// min: 50x1 +150x2       +100x4 +200x5 +200x6        +100x8;
/* H� situa��es em que as duas fun��es objectivo conduzem � mesma solu��o */
/* Noutras situa��es, as solu��es podem ser diferentes                    */

L550: 1 x1 + 1 x2                                           >=  50;
L500:               2 x3 + 1 x4 + 1 x5                      >= 200;
L400: 1 x1               + 1 x4        + 2 x6 + 1 x7        >= 400;
L300:      + 1 x2               + 1 x5        + 2 x7 + 3 x8 >= 100;
   
