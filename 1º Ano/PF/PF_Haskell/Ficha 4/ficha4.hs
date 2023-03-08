module Ficha4 where
import Data.Char



--1.  dada uma string, devolve um par de strings: (letras,numeros)
digitAlpha :: String -> (String,String)
digitAlpha s = ((filter (isAlpha) s), (filter (isDigit) s))
--digitAlpha l = ([xs | xs <- l, isAlpha xs], [xs | xs <- l, isDigit xs])

--digitAlpha string = foldl (\(alpha, digit) x -> if      isAlpha x then (alpha ++ [x], digit)
--												else if isDigit x then (alpha, digit ++ [x])
--												                  else (alpha,digit)) ("","") string

--2.  -- lista de inteiros, devolve (nº negs, nº zeros, nº interiros)
nzp :: [Int] -> (Int,Int,Int)
nzp l = (length [l | xs <- l, xs<0],length [l | xs <- l, xs==0], length [l | xs <- l, xs>0])
--nzp list = foldl(\(neg, zer, pos) x -> if      x  < 0 then (neg + 1 , zer, pos) 
--	                                    else if x == 0 then (neg, zer + 1, pos)
--	                                    	           else (neg, zer, pos + 1)) (0,0,0) list

--3 calcula divisão e resto por subtrações sucessivas----------------------------------------
divMod' :: Integral a => a -> a -> (a, a)
divMod' a b = (div a b, mod a b)

--4 fromDigits---------------------------------------------------------------------------------
fromDigits :: [Int] -> Int
fromDigits = foldl (\acc x -> x + 10 * acc ) 0

--5 maxSumInit----------------------------------------------------------------------------
maxSumInit :: (Num a, Ord a) => [a] -> a
maxSumInit l = aux l 0 0
  where aux :: (Num a, Ord a) => [a] -> a -> a -> a
        aux [] m s = m 
        aux (x:xs) m s 
          | (s + x) > m = aux xs (s+x) (s+x)
          | otherwise = aux xs m (s+x)

--6 Fibonacci-----------------------------------------------------------
fib :: Int -> Int
fib 0 = 0
fib 1 = 1
fib n = fibo 0 1 2 n
  where
    fibo acc1 acc2 n alvo | n == alvo = acc1 + acc2
                          | otherwise = fibo acc2 (acc1 + acc2) (n + 1) alvo


--7. intToStr --------------------------------------------------------------
--intToStr :: Integer -> String

{--
8. 

a) Numeros pares e múltiplos de 3 até 20 [6,12,18]

b) Ver quais numeros tem resto 0 com o numero 3, mas em vez de ser de 1 a 20 (como na alinea anterior), 
a gama de valores tera de respeitar a seguinte condicao: quais os numero de 1 a 20 tem resto 0 na divisao com 2 

Lista auxiliar -> Resto 0 : 2,4,6,8,10,12,14,16,18,20

Resto 0 divisao por 3 : 6,12,18

Resposta: [6,12,18] 

c)
Os valores para a e para b variam entre 0 e 20, mas so se consideram aqueles cuja soma de a e b sejam 30

Resposta: [(0,30), (1,29), (2,28), (3,27), (4,26), (5,25), (6,24), (7,23), (8,22), (9,21), (10,20),
           (11,19),(12,18),(13,17),(14,16),(15,15),(16,14),(17,13),(18,12),(19,11),(20,10),
           (21,9), (22,8), (23,7), (24,6), (25,5), (26,4), (27,3), (28,2), (29,1), (30,0)

d)

[sum [y | y <- [1..x], odd y] | x <- [1..10]]

Primeira lista: vai de 1 ate ao valor que x tiver naquele momento, escolhendo-se apenas os impares
Segunda lista: vai de 1 a 10

Resposta: [1,1,4,4,9,9,16,16,25,25]


9.

a. [2^x | x <-[0..10]]
b. [(x,y) | x <- [1..5], y <- [1..5], x+y == 6]
c. [ [1..x] | x <- [1..5]]
d. [ replicate x 1 | x <- [1..5]]
e. [product [1..x] | x <- [1..6]]

e1. [ factorial x | x <- [1..6]]
            where factorial 0 = 1
                  factorial x = x * factorial (x - 1)
--}