import Data.List
import System.IO

--Teste 2122

--1
replicate2 :: Int -> a -> [a]
replicate2 0 _ = []
replicate2 n x = x:replicate2 (n-1) x

intersect2 :: Eq a => [a] -> [a] -> [a]
intersect2 _ [] = []
intersect2 l1 l2 = filter (\x -> elem x l2) l1

data LTree a = Tip a | Fork (LTree a) (LTree a)
data FTree a b = Leaf a | No b (FTree a b) (FTree a b)

sumLTree :: LTree Int -> Int
sumLTree (Tip x) = x
sumLTree (Fork l r) = sumLTree l + sumLTree r

conv :: LTree Int -> FTree Int Int
conv (Tip x) = Leaf x
conv (Fork l r) = No (sumLTree l + sumLTree r) (conv l) (conv r)


                --     10
                --     /\
                --    3  7
                --   / \/ \
                --  1  23  4 

type Mat a = [[a]]

tripSup:: [[Int]] ->Bool
tripSup [] = False
tripSup l = verifica 0 l

verifica :: Int -> [[Int]] -> Bool
verifica _ [] = True
verifica n (x:xs) = contazero n x && verifica (n+1) xs

contazero :: Int -> [Int] -> Bool
contazero _ [] = True
contazero 0 (x:xs) 
        | x == 0 = False
        | otherwise = True
contazero n (x:xs) 
        | x == 0 = True && contazero (n-1) xs
        | otherwise = False

data SReais = AA Double Double | FF Double Double| AF Double Double | FA Double Double | Uniao SReais SReais

instance Show SReais where
    show (AA x y) = "(" ++ "]" ++ show x ++ "," ++ show y ++ "[" ++ ")"
    show (FF x y) = "(" ++ "[" ++ show x ++ "," ++ show y ++ "]" ++ ")"
    show (AF x y) = "(" ++ "]" ++ show x ++ "," ++ show y ++ "]" ++ ")"
    show (FA x y) = "(" ++ "[" ++ show x ++ "," ++ show y ++ "[" ++ ")"
    show (Uniao a b) = "(" ++ "(" ++ show a ++ " U " ++ show b ++ ")" ++ ")"

func :: Float -> [(Float,Float)] -> [Float]
func x ((a,b):t)    |a>x = b:(func x t)
                    |otherwise =func x t

--Exame 25/01/20

--1
inits2 :: [a] -> [[a]]
inits2 [] = [[]]
inits2 l = inits2 (init l) ++ [l]

isPrefixOff :: Eq a => [a] -> [a] -> Bool
isPrefixOff [] _ = True
isPrefixOff (h1:t1) (h2:t2)  |(h1 == h2) = isPrefixOff t1 t2
                            |otherwise = False  

--2

data BTree a = Empty
            |Node a (BTree a) (BTree a)
            deriving Show

folhas :: BTree a -> Int
folhas Empty = 0
folhas (Node _ Empty Empty) = 1
folhas (Node _ l r) = folhas l + folhas r

path :: [Bool] -> BTree a -> [a]
path _ Empty = []
path [] (Node e _ _) = [e]
path (h:t) (Node e l r) = e : path t (if h then r else l)
--ver este

--3
type Polinomio = [Coeficiente]
type Coeficiente = Float

valor :: Polinomio -> Float -> Float
valor [] _ = 0
valor l x = conta l x 0

conta :: Polinomio -> Float -> Int -> Float
conta [] _ _ = 0
conta (h:t) x n = (h*(x^n))+conta t x (n+1)

deriv :: Polinomio -> Polinomio
deriv [] = []
deriv l = conta2 l 1

conta2 :: Polinomio -> Int -> Polinomio
conta2 [] _ = []
conta2 (h:[]) _ = []
conta2 (h:t1:t2) x = (t1*(fromIntegral x)):conta2 (t1:t2) (x+1)

soma :: Polinomio -> Polinomio -> Polinomio
soma a [] = a
soma [] a = a
soma (hp1:tp1) (hp2:tp2) = (hp1+hp2):soma tp1 tp2

--4
--(já definida em cima)
--type Mat a = [[a]]
ex = [[1,4,3,2,5], [6,7,8,9,0], [3,5,4,9,1]]

quebraLinha :: [Int] -> [a] -> [[a]] 
quebraLinha [] _ = []
quebraLinha (x:xs) l = take x l : quebraLinha xs (drop x l)

fragmenta :: [Int] -> [Int] -> Mat a -> [Mat a]
fragmenta [] _ _ = []
fragmenta (x:xs) c m = fragmentaCols c (take x m) ++ fragmenta xs c (drop x m)

fragmentaCols :: [Int] -> Mat a -> [Mat a]
fragmentaCols [] _ = []
fragmentaCols (x:xs) m = map (take x) m : fragmentaCols xs (map (drop x) m)
--help with this one

-- geraMat :: (Int,Int) -> (Int,Int) -> IO (Mat Int)
-- geraMat (x,y) (a,b) = 
--     sequence 
--     $ replicate y 
--     $ sequence 
--     $ replicate x 
--     $ randomRIO (a,b)

-- randomRIO :: Random a => (a,a) -> IO a
--and this one

-------------------------------------
--Teste2122

mzip :: [a] -> [b] -> [(a,b)]
mzip [] _ = []
mzip (h1:t1) (h2:t2) = [(h1,h2)]++mzip t1 t2

preCrescente :: Ord a => [a] -> [a]
preCrescente [] = []
preCrescente [a] = [a]
preCrescente (h:t1:t2)  |h<t1 = h:preCrescente(t1:t2) 
                        |otherwise = [h]

amplitude :: [Int] -> Int
amplitude [] = 0
amplitude l = maximum l - minimum l

somaMat :: Num a => Mat a -> Mat a -> Mat a
somaMat [] [] = []
somaMat (h:t) [] = (h:t)
somaMat [] (h:t) = (h:t)
somaMat (h:t) (h2:t2) = (zipWith (+) h h2) : somaMat t t2

type Nome = String
type Telefone = Integer
data Agenda = Vazia | Nodo (Nome,[Telefone]) Agenda Agenda

agenda = Nodo ("Ana", [123456, 789101]) 
           (Nodo ("Carla", [456789, 111111]) Vazia Vazia) 
           (Nodo ("João", [121212, 242424, 919191]) Vazia Vazia)

--copiar a seguinte

instance Show Agenda where
  show Vazia = ""
  show (Nodo (nome, telefones) esq dir) = 
    show esq ++ nome ++ ": " ++ showTelefones telefones ++ "\n" ++ show dir
    where showTelefones telefones = intercalate "/" (map show telefones)

organiza :: Eq a => [a] -> [(a,[Int])]
organiza [] = []
organiza (h:t) = (h,(elemIndices h (h:t))):organiza(filter (/= h) (h:t))
--n pode remover da lista

funcx :: [[Int]] -> [Int]
funcx [] = []
funcx (h:t)
    | sum h > 10 = h ++ funcx t
    | otherwise = funcx t

    insere :: String -> String -> Dictionary -> Dictionary
insere [x] desc dict = insereFim x desc dict
insere (h:t) desc [] = [ R (h,Nothing) (insere t desc [])]
insere (h:t) desc (R (a,b) l:d)
    | h == a = R (a,b) (insere t desc l) : d
    | otherwise = R (a,b) l : insere (h:t) desc d

insereFim :: Char -> String -> Dictionary -> Dictionary
insereFim x desc [] = [ R (x,Just desc) [] ]
insereFim x desc (R (a,b) l:t) 
    | x == a = R (a,Just desc) l : t
    | otherwise = R (a,b) l : insereFim x desc t

--ver esta