{- |
Module      : Tarefa3_2021li1g012
Description : Representação textual do jogo
Copyright   : João Pedro Cardoso <a94595@alunos.uminho.pt>;
            : Luís Gomes Ferreira <a91672@alunos.uminho.pt>;

Módulo para a realização da Tarefa 3 do projeto de LI1 em 2021/22.
-}
module Tarefa3_2021li1g012 where

import LI12122



instance Show Jogo where
  show (Jogo l j) = funcaoFinal l j


--Funções relativas à Construição Mapa--------------------------------------------------------------

--transforma uma lista de Peca em String
showLineMap :: [Peca] -> String
showLineMap [] = []
showLineMap (x:xs) | x == Vazio = ' ' : showLineMap xs
                   | x == Bloco = 'X' : showLineMap xs
                   | x == Porta = 'P' : showLineMap xs
                   | x == Caixa = 'C' : showLineMap xs


-- utilizando showLineMap e showAllMap retorna uma String, na qual divisão entre linhas é representada por '\n
-- >>> showAllMap [[Vazio,Bloco],[Porta,Bloco],[Caixa,Vazio]]  >> " X \nPX \nC  "
showAllMap :: Mapa -> String
showAllMap [] = []
showAllMap [x] = showLineMap x
showAllMap ((x:xs):y) = showLineMap (x:xs) ++ "\n" ++  showAllMap y



-- Funções relativas ao Jogador------------------------------------------------------------------------------
--  insere o Jogador corretamente numa String
switchLetra :: Coordenadas -> Jogador -> String -> String
switchLetra (x,y) (Jogador (a,b) d f) [] = []
switchLetra (x,y) (Jogador (a,b) d f) (z:zs) | x == a && y == b && d == Este = '>' : zs
                                            | x == a && y == b && d == Oeste = '<' : zs
                                            | z == '\n' = z : switchLetra (0,y+1) (Jogador (a,b) d f) zs
                                            | otherwise = z : switchLetra (x+1,y) (Jogador (a,b) d f) zs

--retorna todas as coordenadas de uma String
showCoord :: Coordenadas -> String -> [Coordenadas]
showCoord (x,y) [] = []
showCoord (x,y) (z:zs) | z == ' ' = showCoord (x+1,y) zs
                       | z == '\n' = showCoord (0,y+1) zs
                       | otherwise = (x,y) : showCoord (x+1,y) zs





-- verifica se jogador está apoiado numa em alguma Peca 
checkSupport :: Coordenadas -> [Coordenadas] -> Bool
checkSupport (x,y) [] = False
checkSupport (x,y) ((a,b):c) | x == a && y == b-1 = True
                             | otherwise = checkSupport (x,y) c

--retorna as coordenadas com a mesma abcissa da coordenada do Jogador
showColuna :: Coordenadas -> [Coordenadas] -> [Coordenadas]
showColuna (x,y) [] = []
showColuna (x,y) ((a,b):c) | x == a = (a,b) : showColuna (x,y) c
                           | otherwise = showColuna (x,y) c

-- retorna uma lista de coordenadas com as coordenadas abaixo do Jogador 
removeAbove :: Coordenadas -> [Coordenadas] -> [Coordenadas] 
removeAbove (x,y) [] = []
removeAbove (x,y) ((a,b):c) | y >= b = removeAbove (x,y) c
                            | otherwise = (a,b) : removeAbove (x,y) c




-- "I Have the high ground !" retorna a coordenada com menor ordenada  
highGround :: [Coordenadas] -> Coordenadas
highGround [(x,y)] = (x,y)
highGround ((x,y):(a,b):c) | y < b = highGround ((x,y):c)  
                           | otherwise = highGround ((a,b):c)


--impede que o Jogador fique enterrado entre Blocos 
gravity :: Coordenadas -> Bool -> Jogador -> Jogador
gravity (x,y) r (Jogador (a,b) d f) | r == True = (Jogador (a,b) d f)
                                    | otherwise = (Jogador (a,y-1) d f)

--  nunca deixa o jogador a flutuar, ou seja, coloca-or sempre no chão 
gravityFinal :: String -> Jogador -> Jogador
gravityFinal l (Jogador (a,b) d f) = gravity (highGround (removeAbove (a,b) (showColuna (a,b) (showCoord (0,0) l)))) (checkSupport (a,b) (showCoord (0,0) l)) (Jogador (a,b) d f)


-- devolve uma String na qual o Mapa está corretamente representado e Jogador nunca flutua
funcaoFinal :: Mapa -> Jogador -> String
funcaoFinal l j = switchLetra (0,0) (gravityFinal (showAllMap l) j) (showAllMap l)
