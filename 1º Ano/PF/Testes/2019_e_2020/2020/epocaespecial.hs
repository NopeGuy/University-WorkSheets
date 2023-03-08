import Data.List 
--1
--a
subst :: Eq a => (a,a) -> [a] -> [a]
subst (a,b) [] = []
subst (a,b) (x:xs) | a == x = b : subst (a,b) xs 
                   | otherwise = x : subst (a,b) xs 
--b
posicoes :: [a] -> [Int] -> [a]
posicoes [] _  = []
posicoes l []  = [] 
posicoes l (x:xs) = position x l : posicoes  l xs 
 where position :: Int -> [a] -> a 
       position p (x:xs) | p==1      = x 
                         | otherwise = position (p-1) xs 

--ou 

posicoes11:: [a] -> [Int] -> [a]
posicoes11 l [] = []
posicoes11 l (h:t) =  (auxiliar h l) : posicoes l t
     where auxiliar :: Int-> [a] -> a
           auxiliar 1 (a:as) = a
           auxiliar n (a:as) = auxiliar (n-1) as 

--2
data Tree a b = Leaf b 
              | Node a (Tree a b) (Tree a b)
              deriving Show 

p :: Tree Int Int
p = Node 3 (Leaf 4) (Leaf 5)

--a
folhas :: Tree a b -> [b]
folhas (Leaf b) = [b]
folhas (Node a e d) = folhas e ++ folhas d 

--b
somas :: Tree Float Int -> (Float,Int) 
somas  l = (sum (somaB l) , sum (folhas l))
somaB :: Tree a b -> [a] 
somaB (Leaf x) = []
somaB (Node m (a) (b)) =  [m] ++ (somaB  a) ++ (somaB  b )


--3
type Mat a = [[a]]

ex :: Mat Int 
ex = [[1,2,3],[0,4,5],[0,0,6]]

rotateLeft :: Mat a -> Mat a 
rotateLeft [] = []
rotateLeft m1 = (rotateLeft (map tail m1)) ++ [map head m1]

--4
type Filme = (Titulo,Realizador,[Actor],Genero,Ano)
type Titulo = String 
type Realizador = String
type Actor = String
type Ano = Int

data Genero = Comedia | Drama | Ficcao | Accao | Animacao | Documentario 
            deriving Eq 

type Filmes = [Filme]

--a
doRea :: Filmes -> Realizador -> [Titulo]
doRea [] _ = []
doRea ((a,b,_,_,_):t) p = if p == b then a : doRea t p else doRea t p 

--b
doActor :: Filmes -> Actor -> [Titulo]
doActor [] _ = []
doActor ((a,_,b,_,_):t) p = if elem p b then a : doActor t p else doActor t p 

--c
consulta :: Filmes -> Genero -> Realizador -> [(Ano, Titulo)]
consulta bd gen rea = map aux22 (filter (teste gen rea) bd)
        where teste ::  Genero -> Realizador -> Filme -> Bool
              teste g r (_,x,_,y,_) = g==y && r==x
aux22 ::  Filme -> (Ano,Titulo)
aux22 (a,_,_,_,e) = (e,a)







