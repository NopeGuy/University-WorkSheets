{- |
Module      : Tarefa1_2021li1g012
Description : Validação de um potencial mapa
Copyright   : João Pedro Cardoso <a94595@alunos.uminho.pt>;
            : Luís Gomes Ferreira <a91672@alunos.uminho.pt>;

Módulo para a realização da Tarefa 1 do projeto de LI1 em 2021/22.
-}
module Tarefa1_2021li1g012 where

import LI12122

validaPotencialMapa :: [(Peca, Coordenadas)] -> Bool
validaPotencialMapa [] = False
validaPotencialMapa l@((p,(x1,y1)):z) = not (verifyRepetemCoord l) && verifyPorta l && checkApoioTotal ((p,(x1,y1)):z) && verifyEmpty l && checkBase l

                        

--1. Não pode haver mais que uma para a mesma peça---------------------------------------------------------------------------



--Coordenada perternce à lista ? True se sim, False se não
verifyExisteCoord :: Coordenadas -> [(Peca, Coordenadas)] -> Bool
verifyExisteCoord (x,y) [] = False
verifyExisteCoord (x,y) ((p,(a,b)):z) | x == a && y == b = True
                                      | otherwise = verifyExisteCoord (x,y) z

--Função que verifica se existem coordenadas repetidas
verifyRepetemCoord :: [(Peca, Coordenadas)] -> Bool
verifyRepetemCoord [] = False
verifyRepetemCoord  ((p,(x1,y1)):z) = verifyExisteCoord (x1,y1) z || verifyRepetemCoord z




--2. Ter exatamente uma porta-----------------------------------------------------------------------------------------------

--Tem porta ? devolve false else true
verifyNtemPorta :: [(Peca, Coordenadas)] -> Bool
verifyNtemPorta [] = True
verifyNtemPorta ((p,(a,b)):z) | Porta /= p = verifyNtemPorta z
                              | otherwise = False

--Tem Porta ? devolve True else False
verifyPorta :: [(Peca, Coordenadas)] -> Bool
verifyPorta [] = False
verifyPorta ((p,(a,b)):z) | Porta == p = verifyNtemPorta z
                          | otherwise = verifyPorta z 



--3. Não haver caixas a flutaru -----------------------------------------------------------------------------
--Devolve as coordenadas de caixas em lista 
getCoordCaixa :: [(Peca, Coordenadas)] -> [Coordenadas]
getCoordCaixa [] = []
getCoordCaixa ((p,(a,b)):z) | p == Caixa = (a,b) : getCoordCaixa z
                            | otherwise = getCoordCaixa z


--verifica se uma peca está "apoiada" por caixa ou bloco
checkApoio :: Coordenadas -> [(Peca, Coordenadas)]  -> Bool
checkApoio (x,y) [] = False
checkApoio (x,y) ((p,(a,b)):z) | x == a && y == b-1 = p == Bloco || p == Caixa --posição com mesmo x e y-1
                               | otherwise = checkApoio (x,y) z

--mesmo que anterior mas mais eficaz. Verifica uma lista de coordenadas
checkApoioCaixa :: [Coordenadas] -> [(Peca, Coordenadas)] -> Bool
checkApoioCaixa [] ((p,(a,b)):z) = True
checkApoioCaixa ((x,y):w) ((p,(a,b)):z) = checkApoio (x,y) ((p,(a,b)):z) && checkApoioCaixa w z
 

--Check Final, faz um check que junta as 3 anteriores
checkApoioTotal :: [(Peca, Coordenadas)] -> Bool
checkApoioTotal [] = False
checkApoioTotal l | getCoordCaixa l == [] = True
                  | otherwise = checkApoioCaixa (getCoordCaixa l) l




--4. devem existir espaços vazios---------------------------------------------------------------------------------------------


--retira as coordenadas para uma lista
getCoordenadas :: [(Peca, Coordenadas)] -> [Coordenadas]
getCoordenadas [] = []
getCoordenadas ((p,(a,b)):z) = (a,b) : getCoordenadas z

--get coordenada abaixo
coordAbaixo :: [Coordenadas] -> Coordenadas
coordAbaixo [] = (0,0)
coordAbaixo [(x,y)] = (x,y)
coordAbaixo ((x,y):(a,b):z) | y >= b = coordAbaixo ((x,y):z)  
                                 | otherwise = coordAbaixo ((a,b):z)
 
--get Coordenada a direita
coordAdireita :: [Coordenadas] -> Coordenadas
coordAdireita [] = (0,0)
coordAdireita [(x,y)] = (x,y)
coordAdireita ((x,y):(a,b):z) | x >= a = coordAdireita ((x,y):z)
                              | otherwise = coordAdireita ((a,b):z)



--calcular o canto inferior direito, usando os outputs das funções anteriores
lastCoord :: Coordenadas -> Coordenadas -> Coordenadas
lastCoord (x,y) (a,b) = (x,b) 


--Retiramos todos  as coordenadas a partir da última coordenada 
-- Nota: pode ser espaço vazio
allCoord :: Coordenadas -> [Coordenadas]
allCoord (0,0) = [(0,0)]
allCoord (x,y) | y == 0 = allCoord (x-1,0) ++ [(x,0)] 
               | x == 0 = allCoord (0,y-1) ++ [(0,y)]
               | otherwise = (allCoord (x,y-1)) ++ (allCoord (x-1,y)) ++ [(x,y)]


--verifica que se uma coordenada existe/pertence a uma lista
verifyNexiste :: Coordenadas -> [Coordenadas] -> Bool
verifyNexiste (x,y) [] = True
verifyNexiste (x,y) ((a,b):z) | x == a && y == b = False
                              | otherwise = verifyNexiste (x,y) z


--faz o mesmo que a anterior mas para listas
verifyNexisteList :: [Coordenadas] -> [Coordenadas] -> Bool
verifyNexisteList ((x,y):z) [] = True
verifyNexisteList [] l = False
verifyNexisteList ((x,y):z) l = verifyNexiste (x,y) l || verifyNexisteList z l

--verifica se o mapa tem espaços vazios : True se tiver
verifyEmpty :: [(Peca, Coordenadas)] -> Bool
verifyEmpty [] = True
verifyEmpty l@((u,(x1,y1)):z) = verifyNexisteList (allCoord (lastCoord (coordAdireita (getCoordenadas l))  (coordAbaixo (getCoordenadas l)))) (getCoordenadas l)


{-
[(Bloco, (6,0)), (Bloco, (6,1)), (Porta, (0,2)), (Caixa, (4,2)),(Bloco, (6,2)), (Bloco, (0,3)), (Bloco, (1,3)), (Bloco, (2,3)),(Bloco, (3,3)), (Bloco, (4,3)), (Bloco, (5,3)), (Bloco, (6,3))]
-}
--5. Deve existir chão-----------------------------------------------------------------------------------------

-- lista com coordenadas de blocos
getCoordBloco :: [(Peca, Coordenadas)] -> [Coordenadas]
getCoordBloco [] = []
getCoordBloco ((p,(a,b)):z) | p == Bloco = (a,b) : getCoordBloco z
                            | otherwise = getCoordBloco z


--saber coordenada de ultimo bloco
getCoordLastBloco :: [Coordenadas] -> Coordenadas
getCoordLastBloco [(x,y)] = (x,y)
getCoordLastBloco ((x,y):(a,b):z) | x > a = getCoordLastBloco ((x,y):z)
                                  | x == a && y >= b = getCoordLastBloco ((x,y):z)
                                  | otherwise = getCoordLastBloco ((a,b):z)
--saber coordenada de primeiro bloco
getCoordFirstBloco :: [Coordenadas] -> Coordenadas
getCoordFirstBloco [(x,y)] = (x,y)
getCoordFirstBloco ((x,y):(a,b):z) | x < a = getCoordFirstBloco ((x,y):z)
                                   | x == a && y >= b = getCoordFirstBloco ((x,y):z)
                                   | otherwise = getCoordFirstBloco ((a,b):z)  


--saber coordenada da peca mais distante da origem
maisDistanteMapa :: [(Peca, Coordenadas)] -> Coordenadas
maisDistanteMapa [(p,(x,y))] = (x,y)
maisDistanteMapa ((p,(x,y)):(u,(a,b)):c) | x > a = maisDistanteMapa ((p,(x,y)):c)
                                     | x == a && y >= b = maisDistanteMapa ((p,(x,y)):c)
                                     | otherwise = maisDistanteMapa ((u,(a,b)):c) 

-- dá  as Coordenadas de uma lista de pecas com as respetivas Coordenadas
everyCoord :: [(Peca, Coordenadas)] -> [Coordenadas]
everyCoord [] = []
everyCoord ((p,(x,y)):z) = (x,y) : everyCoord z


--Nova checkVizinhos -> começa no primeiro bloco, verifica o mais à esquerda, se este tiver continua a verificar até chega ao último
verifyVizinho :: Coordenadas -> [Coordenadas] -> Bool
verifyVizinho (x,y) [] = False
verifyVizinho (x,y) l@((a,b):c) | x == (a-1) && y == b = verifyVizinho (a,b) l
                                | x == (a-1) && y == (b-1) = verifyVizinho (a,b) l
                                | x == (a-1) && y == (b+1) = verifyVizinho (a,b) l
                                | (x,y) == getCoordLastBloco l = True
                                | otherwise = verifyVizinho (x,y) c 





--Verifica se a base de um mapa existe e está bem construída (Não ter blocos debaixo dela)
checkBase :: [(Peca, Coordenadas)] -> Bool
checkBase l@((u,(a,b)):z) | getCoordLastBloco (getCoordBloco l) == maisDistanteMapa l = verifyVizinho (getCoordFirstBloco (getCoordBloco l)) (getCoordBloco l)
                          | (a,b) == getCoordLastBloco (getCoordBloco l) = True
                          | otherwise = False
