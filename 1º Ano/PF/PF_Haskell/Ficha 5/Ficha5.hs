module Ficha5 where
import Data.Char


--1. Definições de funcões de ordem superior
-- testa se um predicado é verdade para pelo menos um elemento da lista
myany :: (a -> Bool) -> [a] -> Bool
myany  f   [] = False
myany  f (h:t) | f h = True 
 		       | otherwise = myany f t

--combina elementos de duas listas
myzipWith :: (a->b->c) -> [a] -> [b] -> [c]
myzipWith f (x:xs) (y:ys) = f x y : zipWith f xs ys
myzipWith _ _ _ = []

--determina os primeiros elemtos que satisfazem uma condição
mytakeWhile :: (a->Bool) -> [a] -> [a]
mytakeWhile f  []   = [] 
mytakeWhile f (h:t) | f h = h : mytakeWhile f t
					| otherwise = []


--remove os primeiro que satisfaazem um predicado
mydropWhile :: (a->Bool) -> [a] -> [a]
mydropWhile f [] = []
mydropWhile f (h:t) | f h = mydropWhile f t
					| otherwise = (h:t)

--span apresenta o resultado de duas funções
myspan :: (a-> Bool) -> [a] -> ([a],[a])
myspan f [] = ([],[])
myspan f (h:t) | f h = ( h:s1, s2)
			   | otherwise = ([],h:t)
			   where (s1,s2) = myspan f t
--l@(h:t) -> l as (h:t)

-- deleteBy (\x y -> snd x == snd y) (1,2) [(3,3),(2,2),(4,2)] -> [(3,3),(4,2)]
deleteBy :: (a -> a -> Bool) -> a -> [a] -> [a]
deleteBy f x (h:t) | f x h = t
				   | otherwise = h : deleteBy f x t


sortOn :: Ord b => (a -> b) -> [a] -> [a]
sortOn f [] = []
sortOn f (h:t) = insere h (sortOn f t)
	where insere x [] = [x]
	      insere x (y:ys) = if f x > f y then y:insere x ys else x:y:ys



--Exercício 2-------------------------------------------
type Polinomio = [Monomio]
type Monomio = (Float,Int)

--ir buscar os monomios de um dado grau
selgrau :: Int -> Polinomio -> Polinomio
selgrau g p = filter (\x -> snd x == g) p

--contar nº de monomios de um grau
conta :: Int -> Polinomio -> Int
conta g p = length (selgrau g p)

--indica o grau de um polinomio, o monomio mais alto
grau :: Polinomio -> Int
grau p = foldl(\ac x -> max ac (snd x)) 0 p


--fazer a derivada de um poliniomio (a^b = (a*b)^(b-1)
deriv :: Polinomio -> Polinomio
--deriv p = map aux p
--	where aux (x,y)= (x* (fromIntegral y), y-1)
deriv p = map (\(x,y) -> (x* (fromIntegral y), y-1)) p


calcula :: Float -> Polinomio -> Float
calcula x =foldl(\acc (c,g) -> c*(x^g) + acc) 0 --c -> constante (2 em 2x) g -> grau

simp :: Polinomio -> Polinomio
simp p = filter(\(c,g) ->  c /= 0) p
--simp = filter (\(c,g) -> c /= 0)

mult :: Monomio -> Polinomio -> Polinomio
mult (x,y) p = map(\(c,g) -> (x*c, y+g)) p


ordena :: Polinomio -> Polinomio
ordena p = sortOn snd p

normaliza :: Polinomio -> Polinomio
normaliza p = foldl f [] (ordena p)
 where
 f [] x = [x]
 f ((a,b):t) (c,d) | b == d = f t (a+c,b)
                   | otherwise = (a,b):f t (c,d)


soma :: Polinomio -> Polinomio -> Polinomio
soma p1 p2 = normaliza (p1 ++ p2)

produto :: Polinomio -> Polinomio -> Polinomio
produto p1 p2 = concat(map(\m -> mult m p2) p1)

equiv :: Polinomio -> Polinomio -> Bool
equiv p1 p2 = normaliza(p1) == normaliza (p2)


--Exercício 3------------Matrizes-----------------
type Mat a = [[a]]

dimOK :: Mat a -> Bool
dimOK (h:t) = all(\x -> length x == length h) t --função all aplica a função a todos elems da lista

{-
[[1,2,3],[4,5,6],[7,8,9]]
[[1,2],[3,4]]
[[0,1],[0,1]]
--}

dimMat :: Mat a -> (Int,Int)
dimMat [] = (0,0)
dimMat l = (length l, length (head l))

addMat :: Num a => Mat a -> Mat a -> Mat a
addMat m1 m2 = myzipWith(myzipWith(+)) m1 m2

--lembrete 
-- > zipWith div [10,20..50] [1..]
-- [10,10,10,10,10]


mytranspose :: Mat a -> Mat a
-- se linha chegar a lista vazia 
--tudo tem de ficar lista vazia
mytranspose ([]:_) = []                       
mytranspose m = map head m: mytranspose (map tail m)


mymultMat :: Num a => Mat a -> Mat a -> Mat a
mymultMat m1 m2 = [[sum(zipWith (*) l1 l2) | l2 <- mytranspose m2]  | l1 <- m1]


zipWMat :: (a -> b -> c) -> Mat a -> Mat b -> Mat c
zipWMat f m1 m2 = zipWith(zipWith f) m1 m2


--Rever esta merda
triSup :: (Num a, Eq a) => Mat a -> Bool
triSup m = l == c && all (\(p,l) -> temZeros p l) (zip [0..l-1] m)
 where 
 (l,c) = dimMat m 

temZeros :: (Num a, Eq a) => Int -> [a] -> Bool
temZeros 0 _ = True
temZeros _ [] = False
temZeros n (h:t) = h == 0 && temZeros (n-1) t


rotateLeft :: Mat a -> Mat a
rotateLeft m = reverse(mytranspose m)




{-

transpose' :: Mat a -> Mat a
transpose' ([]:_) = [] -- se uma das linhas chegar a lista vazia tudo tem de ficar lista vazia
transpose' l = map head l: transpose'(map tail l)
--e)
multMat :: Num a => Mat a -> Mat a -> Mat a
multMat m1 m2 = [[sum(zipWith(*) l1 l2) | l2 <- transpose m2]| l1 <- m1]
--f)
zipWMat:: (a->b->c)->Mat a -> Mat b -> Mat c
zipWMat _ _ [] = []
zipWMat _ [] _ = []
zipWMat f (m1:m1s) (m2:m2s) = combinaLinhas f m1 m2 : zipWMat f m1s m2s
combinaLinhas :: (a->b->c) -> [a] -> [b] -> [c]
combinaLinhas _ [] _ = []
combinaLinhas _ _ [] = []
combinaLinhas f (l1:l1s) (l2:l2s) = f l1 l2 : combinaLinhas f l1s l2s

--ou
--zipWMat' f = zipWith (zipWith f)

somaMatrizes = zipWMat (+)

--g)
triSup :: (Num a, Eq a) => Mat a -> Bool
triSup m = l == c && all (\(p,l) -> aux p l) (zip [0..l-1] m)
 where 
 (l,c) = dimMat m 

aux :: (Num a, Eq a) => Int -> [a] -> Bool
aux 0 _ = True
aux _ [] = False
aux n (h:t) = h == 0 && aux (n-1) t

--h)
rotateLeft :: Mat a -> Mat a
rotateLeft l = reverse(transpose' l)
-}

