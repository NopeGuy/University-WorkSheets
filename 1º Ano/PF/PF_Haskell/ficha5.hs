module Ficha5 where

import Data.Char

-- Ficha 5 Programacao Funcional
--------------------------------------------------------------------------------------- exercicio 1 ----------------------------------------------------------------------------------------------
-- alinea A
-- teste se um predicado e verdade para algum elemento de uma lista; por exemplo 
--                                                                   any odd [1..10] == True

my_any :: (a -> Bool) -> [a] -> Bool
my_any _ []    = False
my_any f (h:t) = if f h then True 
                        else my_any f t

-- alinea B
-- combina os elementos de duas listas usando uma funcao especıfica; por exemplo:
--                                                                   zipWith (+) [1,2,3,4,5] [10,20,30,40] == [11,22,33,44]

my_zipWith :: (a->b->c) -> [a] -> [b] -> [c] 
my_zipWith f (h:t) (x:y) = (f h x) : my_zipWith f t y 
my_zipWith _ _ _         = []

-- alinea C
-- determina os primeiros elementos da lista que satisfazem um dado predicado; por exemplo
--                                                                              takeWhile odd [1,3,4,5,6,6] == [1,3]

my_takeWhile :: (a->Bool) -> [a] -> [a]
my_takeWhile _ []    = []
my_takeWhile f (h:t) = if f h then h: my_takeWhile f t 
                              else []

-- alinea D
-- elimina os primeiros elementos da lista que satisfazem um dado predicado; por exemplo:
--                                                                           dropWhile odd [1,3,4,5,6,6] == [4,5,6,6]

my_dropWhile :: (a->Bool) -> [a] -> [a]
my_dropWhile _ []    = []
my_dropWhile f (h:t) = if f h then my_dropWhile f t
                              else (h:t)

-- alinea E
-- calcula simultaneamente os dois resultados anteriores. 
-- Note que apesar de poder ser definida a custa das outras duas, usando a definicao span p l = (takeWhile p l, dropWhile p l)
-- Nessa definicao ha trabalho redundante que pode ser evitado. Apresente uma definicao alternativa onde nao haja duplicacao de trabalho

my_span :: (a-> Bool) -> [a] -> ([a],[a])
my_span _ []    = ([],[])
my_span f (h:t) = if f h then (h:s1,s2)
                         else ([],(h:t))
                         where
                            (s1,s2) = my_span f t

-- alinea F
-- apaga o primeiro elemento de uma lista que e “igual” a um dado elemento de acordo com a funcao de comparacao que e passada como parametro. 
-- Por exemplo: deleteBy (\x y -> snd x == snd y) (1,2) [(3,3),(2,2),(4,2)]

my_deleteBy :: (a -> a -> Bool) -> a -> [a] -> [a] 
my_deleteBy _ a []    = []
my_deleteBy f a (h:t) = if f a h then t 
                                 else h:my_deleteBy f a t 

-- alinea G
-- ordena uma lista comparando os resultados de aplicar uma funcao de extraccao de uma chave a cada elemento de uma lista. 
-- Por exemplo: sortOn fst [(3,1),(1,2),(2,5)] == [(1,2),(2,5),(3,1)]

my_sortOn :: (Ord b) => (a -> b) -> [a] -> [a]
my_sortOn f []    = []
my_sortOn f (h:t) = insere_no_inicio (h) (my_sortOn f t)
    where insere_no_inicio x []    = [x]
          insere_no_inicio x (a:b) = if f x <= f a then x:a:b
                                                   else a:insere_no_inicio x b 

--------------------------------------------------------------------------------------- exercicio 2 ----------------------------------------------------------------------------------------------
type Polinomio = [Monomio]
type Monomio = (Float,Int)

-- [(2,3), (3,4), (5,3), (4,5)] representa o polinomio 2(x^3) + 3(x^4) + 5(x^3) + 4(x^5)

--  UTILIZAR FUNCOES DE ORDEM SUPERIOR EM VEZ DE RECURSIVIDADE EXPLICITA

-- alinea A
-- selecciona os monomios com um dado grau de um polinomio
selgrau :: Int -> Polinomio -> Polinomio
selgrau a p = filter (\x -> snd x == a) p

-- alinea B
-- (conta n p) indica quantos monomios de grau n existem em p
conta :: Int -> Polinomio -> Int
conta a p = length (selgrau a p)
-- ou conta a p = length $ selgrau a p
-- $ = "..."

-- alinea C
-- indica o grau de um polinomio
grau :: Polinomio -> Int
grau p = undefined -----------------------------------------------------------------------------------------------------------------------------------------------------------------

-- alinea D
-- calcula a derivada de um polinomio
deriv :: Polinomio -> Polinomio
deriv p = filter (/= (0,0)) 
                 (map (\(b,e) -> if e > 0 then (b * fromIntegral e, e - 1) else (0,0)) p)

-- alinea E
-- calcula o valor de um polinomio para uma dado valor de x
calcula :: Float -> Polinomio -> Float
calcula a = undefined -----------------------------------------------------------------------------------------------------------------------------------------------------------------

-- alinea F
-- retira de um polinomio os monomios de coeficiente zero
simp :: Polinomio -> Polinomio
simp p = filter (\(b,e) -> e /= 0) p

-- alinea G
-- calcula o resultado da multiplicacao de um monomio por um polinomio
mult :: Monomio -> Polinomio -> Polinomio
mult (x,y) = map (\(b,e) -> (b*x,y+e))

-- alinea H
-- ordena um polinomio por ordem crescente dos graus dos seus monomios
ordena :: Polinomio -> Polinomio
ordena = my_sortOn (snd)

-- alinea I
-- dado um polinomio constroi um polinomio equivalente em que nao podem aparecer varios monomios com o mesmo grau
normaliza :: Polinomio -> Polinomio
normaliza p = undefined -----------------------------------------------------------------------------------------------------------------------------------------------------------------

-- alinea J
-- faz a soma de dois polinomios de forma que se os polinomios que recebe estiverem normalizados produz tambem um polinomio normalizado
soma :: Polinomio -> Polinomio -> Polinomio
soma p r = normaliza $ (++) p r

-- alinea K
-- calcula o produto de dois polinomios
produto :: Polinomio -> Polinomio -> Polinomio
produto p r = undefined -----------------------------------------------------------------------------------------------------------------------------------------------------------------

-- alinea L
-- testa se dois polinomios sao equivalentes
equiv :: Polinomio -> Polinomio -> Bool
equiv p r = (normaliza p) == (normaliza r)

--------------------------------------------------------------------------------------- exercicio 3 ----------------------------------------------------------------------------------------------
type Mat a = [[a]]

-- alinea A
-- testa se uma matriz esta bem construıda (i.e., se todas as linhas tem a mesma dimensao)
dimOK :: Mat a -> Bool
dimOK []      = True
dimOK [a]     = True
dimOK (h:x:t) = if (length h < 0) || (length h) /= (length x) then False
                                                              else dimOK t  

-- alinea B
-- calcula a dimensao de uma matriz
dimMat :: Mat a -> (Int,Int)
dimMat [] = (0,0)
dimMat m  = (length m,length (head m))

-- alinea C
-- adiciona duas matrizes
addMat :: Num a => Mat a -> Mat a -> Mat a
addMat [] []        = []
addMat l []         = l
addMat [] l         = l
addMat (h:t) (x:xs) = (soma_listas h x) : addMat t xs 

-- funcao auxiliar
soma_listas :: (Num a) => [a] -> [a] -> [a]
soma_listas [] []        = []
soma_listas l []         = l
soma_listas [] l         = l
soma_listas (h:t) (x:xs) = (h+x): soma_listas t xs

-- alinea D
-- calcula a transposta de uma matriz
transpose :: Mat a -> Mat a
transpose ([]:_) = []
transpose m      = (map head m) : transpose (map tail m)

-- alinea E
-- calcula o produto de duas matrizes 
multMat :: (Num a, Eq a) => Mat a -> Mat a -> Mat a
multMat [] _ = []
multMat m1 m2 = [map (produto_Interno (head m1)) (transpose m2)] ++ multMat (tail m1) m2

-- funcao auxiliar
produto_Interno :: (Num a, Eq a) => [a] -> [a] -> a
produto_Interno l1 l2 = sum (zipWith (*) l1 l2)

-- alinea F
-- a semelhanca do que acontece com a funcao zipWith, combina duas matrizes. Use essa funcao para definir uma funcao que adiciona duas matrizes
zipWMat :: (a -> b -> c) -> Mat a -> Mat b -> Mat c
zipWMat f m1 m2 = my_zipWith (my_zipWith f) m1 m2

-- alinea G
-- testa se uma matriz quadrada e triangular superior (i.e., todos os elementos abaixo da diagonal sao nulos)

triSup :: (Num a, Eq a) => Mat a -> Bool
triSup [] = False
triSup m = ver_linhas m 1

-- funcoes auxiliares 
compara_zeros :: (Num a, Eq a) => [a]  -- linha
                                -> Int -- numero da linhas a ver
                                -> Bool 
compara_zeros _ 1 = True
compara_zeros (h:t) a | h == 0 = compara_zeros t (a-1)
                      | otherwise = False

ver_linhas :: (Num a, Eq a) => Mat a -> Int -- um acumulador auxiliar começado smp em 1
                               -> Bool 
ver_linhas [] _  = True
ver_linhas (h:t)  i | (compara_zeros h i) = ver_linhas t (i+1)
                    | otherwise = False
         
-- alinea H
--roda uma matriz 90o para a esquerda. 
-- Por exemplo, o resultado de rodar a matriz acima apresentada deve corresponder a matriz

-- |1 2 3|    |3 5 6|
-- |0 4 5| == |2 4 0|
-- |0 0 6|    |1 0 0|
 
rotateLeft :: Mat a -> Mat a
rotateLeft ([]:_) = []
rotateLeft m      = (map last m) : rotateLeft (map init m)