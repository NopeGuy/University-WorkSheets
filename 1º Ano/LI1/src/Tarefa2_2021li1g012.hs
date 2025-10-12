{- |
Module      : Tarefa2_2021li1g012
Description : Construção/Desconstrução do mapa
Copyright   : João Pedro Cardoso <a94595@alunos.uminho.pt>;
            : Luís Gomes Ferreira <a91672@alunos.uminho.pt>;

Módulo para a realização da Tarefa 2 do projeto de LI1 em 2021/22.
-}
module Tarefa2_2021li1g012 where

import LI12122
import Data.List (nub)



constroiMapa :: [(Peca, Coordenadas)] -> Mapa
constroiMapa l = constroiFinal l

desconstroiMapa :: Mapa -> [(Peca, Coordenadas)]
desconstroiMapa l = desconstroiFinal l



--1a Parte ---------------------------Construir -----------------------------------------------------

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


--Com o último ponto do mapa, cria mapa com apenas Vazios:  
-- Exemplo: >>> getMapVazios (3,1) >> [[Vazio,Vazio,Vazio,Vazio],[Vazio,Vazio,Vazio,Vazio]]

getMapVazios :: Coordenadas -> Mapa
getMapVazios (x,y)  = replicate (y+1) (replicate (x+1) Vazio)


-- permite saber coordenadas de cada Peca e substituir, mas apenas numa lista
auxChangePeca1 :: Coordenadas -> (Peca, Coordenadas) -> [Peca] -> [Peca]
auxChangePeca1 (c,d) (u,(x,y)) [] = []
auxChangePeca1 (c,d) (u,(x,y)) (a:b) | x == c && d == y = u:b
                                    | otherwise = a : auxChangePeca1 (c+1,d) (u,(x,y)) b


--faz o mesmo que a anterior, mas consegue analisar Mapas
auxChangePeca2 :: Coordenadas -> (Peca, Coordenadas) -> Mapa -> Mapa
auxChangePeca2 (c,d) (u,(x,y)) [] = []
auxChangePeca2 (c,d) (u,(x,y)) ((a:b):h) = auxChangePeca1 (c,d) (u,(x,y)) (a:b) : auxChangePeca2 (c,d+1) (u,(x,y)) h 



--trocar uma Peca (Bloco\Porta\Caixa) por Vazio no sitio adequado
changePeca :: (Peca, Coordenadas) -> Mapa -> Mapa
changePeca (u,(x,y)) [] = []
changePeca (u,(x,y)) ((a:b):h) = auxChangePeca2 (0,0) (u,(x,y)) ((a:b):h)



-- o mesmo que a anterior, mas conseguie substituir uma lista de Peca
changeAllPeca :: [(Peca, Coordenadas)] -> Mapa -> Mapa
changeAllPeca [] ((a:b):h) = ((a:b):h)
changeAllPeca (x:xs) l = changeAllPeca xs (changePeca x l)



-- aglomera todas as funções anteriores de forma a obter a função 'constroiMapa'
constroiFinal :: [(Peca, Coordenadas)] -> Mapa
constroiFinal [] = []
constroiFinal l = changeAllPeca l (getMapVazios (lastCoord (coordAdireita (getCoordenadas l)) (coordAbaixo (getCoordenadas l))))


--2a Parte ---------------------------Desconstruir -----------------------------------------------------



--Devolve lista sem Vazios, "ignora-os" 
--ignoreVazio [Bloco,Bloco,Vazio,Caixa,Porta] >> [Bloco,Bloco,Caixa,Porta]
ignoreVazio :: [Peca] -> [Peca]
ignoreVazio [] = []
ignoreVazio (x:xs) | x == Vazio = ignoreVazio xs
                    | otherwise = x : ignoreVazio xs



-- Faz o mesmo que a anterior (ignoreVazio), mas para mapas
ignoreVazioMapa :: Mapa -> Mapa
ignoreVazioMapa [] = []
ignoreVazioMapa ((x:xs):y) = ignoreVazio (x:xs) : ignoreVazioMapa y

-- transformar um Mapa em uma lista  
getLista3 :: Mapa -> [Peca]
getLista3 [] = [] 
getLista3 ([]:xs) = getLista3 xs
getLista3 (x:xs) = x ++ getLista3 xs




--calcula as coordenadas de cada Peca numa lista de Pecas 
auxCountCoordLista :: Coordenadas -> Peca -> [Peca] -> [Coordenadas]
auxCountCoordLista (x,y) a [] = []
auxCountCoordLista (x,y) a (z:zs) | a == z = (x,y) : auxCountCoordLista (x+1,y) a zs
                                    | otherwise = auxCountCoordLista (x+1,y) a zs



-- o mesmo que a 'auxCountCoordLista', mas analisa um Mapa                                
auxCountCoordMap :: Coordenadas -> Peca -> Mapa -> [Coordenadas]
auxCountCoordMap (x,y) a [] = []
auxCountCoordMap (x,y) a ((c:b):h) = (auxCountCoordLista (x,y) a (c:b)) ++ auxCountCoordMap (x,y+1) a h

-- saber as coordenadas de uma Peca num Mapa
countCoord :: Peca -> Mapa -> [Coordenadas]
countCoord p m = auxCountCoordMap (0,0) p m

-- dada uma lista de Pecas,  retorna  lista com todas as coordenadas de  Pecas iguais no Mapa
countAllCoord :: [Peca] -> Mapa -> [Coordenadas]
countAllCoord (x:xs) [] = []
countAllCoord [] y = []
countAllCoord (x:xs) y = countCoord x y ++ countAllCoord xs y


-- devolve o número de vezes que uma determinada Peca aparece numa lista de Pecas
auxCountRepeated1 :: Peca -> [Peca] -> Int
auxCountRepeated1 p [] = 0
auxCountRepeated1 p (x:xs) | p == x = 1 + auxCountRepeated1 p xs
                         | otherwise = auxCountRepeated1 p xs

--analisa quantas vezes determinada Peca aparece num Mapa
auxCountRepeated2 :: Peca -> Mapa -> Int
auxCountRepeated2 p [] = 0
auxCountRepeated2 p ((x:xs):y) = auxCountRepeated1 p (x:xs) + auxCountRepeated2 p y


--Junta as duas anteriores para cobrir mais casos
--  countRepeated [Bloco,Porta] [[Bloco,Caixa,Bloco],[Porta,Bloco,Bloco]] >> [4,1]
countRepeated :: [Peca] -> Mapa -> [Int]
countRepeated [] [] = []
countRepeated (x:xs) [] = []
countRepeated [] l = []
countRepeated (x:xs) l = auxCountRepeated2 x l : countRepeated xs l


-- multiplica cada Peca por um determinado número (número de vezes que é repetida no Mapa):
-- >>> multiply [4,1] [Bloco,Porta] [Bloco,Bloco,Bloco,Bloco,Porta]
multiply :: [Int] -> [Peca] -> [Peca]
multiply [] [] = []
multiply (x:xs) [] = []
multiply [] (y:ys) = []
multiply (x:xs) (y:ys) = (replicate x y) ++ multiply xs ys

--Função que agrega todas a as funções anteriores, fazendo isso das p´re definidas nub e zip
desconstroiFinal ::  Mapa -> [(Peca, Coordenadas)]
desconstroiFinal l = zip (multiply (countRepeated (nub (getLista3 (ignoreVazioMapa l))) l) (nub (getLista3 (ignoreVazioMapa l)))) (nub (countAllCoord (getLista3 (ignoreVazioMapa l)) l))






--Temporarely deprecated
{-
--Extrair dimensoes finais a partir de um mapa 
getMapSize :: [(Peca, Coordenadas)] -> Coordenadas
getMapSize [] = (0,0)
getMapSize l = (getSize (ncolunas l), getSize(nlinhas l))


--Extrai as coordenadas da lista
ncolunas :: [(Peca, Coordenadas)] -> [Int]
ncolunas [] = [0]
ncolunas ((peca,(x,y)):t) = x : ncolunas t

nlinhas :: [(Peca, Coordenadas)] -> [Int]
nlinhas [] = [0]
nlinhas ((peca,(x,y)):t) = y : nlinhas t

--Determina as dimensoes
getSize :: [Int] -> Int
getSize [] = 0
getSize l = maximum l
--}
