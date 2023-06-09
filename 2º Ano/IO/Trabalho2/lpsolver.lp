/* Função objetivo*/
min: 1 x1_11 + 15 x1_12 + 2 x1_13 + 6 x1_14 + 11 x1_15 + 9 x1_16 + 9 x1_17 + 9 x1_18 + 10 x1_19 + 0 x1_20
   + 5 x2_14 + 7 x2_16 + 2 x2_20
   + 16 x3_20  //nao sai de B
   + 5 x4_11 + 6 x4_14 + 6 x4_16 + 3 x4_20
   + 7 x5_20 //nao sai de E
   + 6 x6_14 + 5 x6_16 + 12 x6_20
   + 10 x7_20 //nao sai de G
   + 6 x8_14 + 10 x8_16 + 10 x8_20
   + 0 x9_11 + 13 x9_12 + 5 x9_13 + 5 x9_14 + 10 x9_15 + 7 x9_16 + 5 x9_17 + 10 x9_20
   + 7 x10_14 + 5 x10_16 + 11 x10_20; //37 arcos

/* Restrições */                           //nr equipas
Vertice1: x1_11 + x1_12 + x1_13 + x1_14 + x1_15 + x1_16 + x1_17 + x1_18 + x1_19 + x1_20 = 9 ;
vertice2: x2_14 + x2_16 + x2_20 = 1 ;
vertice3: x3_20 = 1 ;
vertice4: x4_11 + x4_14 + x4_16 + x4_20 = 1 ;
vertice5: x5_20 = 1 ;
vertice6: x6_14 + x6_16 + x6_20= 1 ;
vertice7: x7_20 = 1 ;
vertice8: x8_14 + x8_16 + x8_20= 1 ;
vertice9: x9_11 + x9_12 + x9_13 + x9_14 + x9_15 + x9_16 + x9_17 + x9_20 = 1 ;
vertice10: x10_14 + x10_16 = 1 ;
vertice11: x1_11 + x4_11 + x9_11 = 1 ;
vertice12: x1_12 + x9_12 = 1 ;
vertice13: x1_13 + x9_13 = 1 ;
vertice14: x1_14 + x2_14 + x6_14 + x8_14 + x9_14 + x10_14 = 1 ;
vertice15: x1_15 + x9_15 = 1 ;
vertice16: x1_16 + x2_16 + x4_16 + x6_16 + x8_16 + x9_16 + x10_16 = 1 ;
vertice17: x1_17 + x9_17 = 1 ;
vertice18: x1_18 = 1 ;
vertice19: x1_19 = 1 ;
vertice20: x1_20 + x2_20 + x3_20 + x4_20 + x5_20 + x6_20 + x7_20 + x8_20 + x9_20 + x10_20 = 9 ;

/* Definição das variáveis */
bin x1_11 , x1_12 , x1_13 , x1_14 , x1_15 , x1_16 , x1_17 , x1_18 , x1_19 ;
bin x2_14 , x2_16 , x2_20 ;
bin x3_20 ;
bin x4_11 , x4_14 , x4_16 , x4_20 ;
bin x5_20 ;
bin x6_14 , x6_16 , x6_20 ;
bin x7_20 ;
bin x8_14 , x8_16 , x8_20 ;
bin x9_11 , x9_12 , x9_13 , x9_14 , x9_15 , x9_16 , x9_17 , x9_20 ;
bin x10_14 , x10_16 , x10_20 ;
int x1_20 ;
