module Ficha4 where

import Data.Char

-- Ficha 4 Programacao Funcional
--------------------------------------------------------------------------------------- exercicio 1 ----------------------------------------------------------------------------------------------
-- Para cada uma das expressoes seguintes, exprima por enumeracao a lista correspondente. 
-- Tente ainda, para cada caso, descobrir uma outra forma de obter o mesmo resultado.

-- alinea A
{-

[x | x <- [1..20], mod x 2 == 0, mod x 3 == 0]



Ver quais numeros tem resto 0 com a divisao com o numero 2 e numero 3
Por 2: 2,4,6,8,10,12,14,16,18,20
Por 3: 3,6,9,12,15,18

Resposta: [6,12,18]

-}

-- alinea B
{-

[x | x <- [y | y <- [1..20], mod y 2 == 0], mod x 3 == 0]

Ver quais numeros tem resto 0 com o numero 3, mas em vez de ser de 1 a 20 (como na alinea anterior), 
a gama de valores tera de respeitar a seguinte condicao: quais os numero de 1 a 20 tem resto 0 na divisao com 2 

Lista auxiliar -> Resto 0 : 2,4,6,8,10,12,14,16,18,20

Resto 0 divisao por 3 : 6,12,18

Resposta: [6,12,18] 

-}

-- alinea C
{-

[(x,y) | x <- [0..20], y <- [0..20], x+y == 30]

Devolve uma lista de pares do tipo (a,b)
Os valores para a e para b variam entre 0 e 20, mas so se consideram aqueles cuja soma de a e b sejam 30

Resposta: [(0,30), (1,29), (2,28), (3,27), (4,26), (5,25), (6,24), (7,23), (8,22), (9,21), (10,20),
           (11,19),(12,18),(13,17),(14,16),(15,15),(16,14),(17,13),(18,12),(19,11),(20,10),
           (21,9), (22,8), (23,7), (24,6), (25,5), (26,4), (27,3), (28,2), (29,1), (30,0)]

-}

-- alinea D
{-

[sum [y | y <- [1..x], odd y] | x <- [1..10]]

Primeira lista: vai de 1 ate ao valor que x tiver naquele momento, escolhendo-se apenas os impares
Segunda lista: vai de 1 a 10

Resposta: [1,1,4,4,9,9,16,16,25,25]

-}

--------------------------------------------------------------------------------------- exercicio 2 ----------------------------------------------------------------------------------------------
-- Definir as listas por compreensao

-- alinea A
-- [1,2,4,8,16,32,64,128,256,512,1024]

alineaA = [2^x | x <- [0..10]]

-- alinea B
-- [(1,5),(2,4),(3,3),(4,2),(5,1)]

alineaB = [(x,y) | x <- [1..5] , y <- [1..5], x+y == 6]

-- alinea C
-- [[1],[1,2],[1,2,3],[1,2,3,4],[1,2,3,4,5]]

alineaC = [ [1..x] | x <- [1..5]]

-- alinea D
-- [[1],[1,1],[1,1,1],[1,1,1,1],[1,1,1,1,1]]

alineaD = [ replicate x 1 | x <- [1..5]]

alineaD' = [[1 | i <- [1..x]] | x <- [1..5]]

-- alinea E
-- [1,2,6,24,120,720]

alineaE = [ factorial x | x <- [1..6]]
            where factorial 0 = 1
                  factorial x = x * factorial (x - 1)

alineaE' = [product [1..x] | x <- [1..6]]

--------------------------------------------------------------------------------------- exercicio 3 ----------------------------------------------------------------------------------------------
-- que dada uma string, devolve um par de strings: uma apenas com as letras presentes nessa string, e a outra apenas com os numeros presentes na string
-- Implemente a funcao de modo a fazer uma unica travessia da string. Relembre que as funcoes isDigit,isAlpha :: Char -> Bool estao ja definidas no modulo Data.Char

digitAlpha :: String -> (String,String)
digitAlpha string = foldl (\(alpha,digit) x -> if      isDigit x then (alpha,digit ++ [x]) 
                                               else if isAlpha x then (alpha ++ [x],digit) 
                                                                 else (alpha,digit)) ("","") string

--------------------------------------------------------------------------------------- exercicio 4 ----------------------------------------------------------------------------------------------
-- dada uma lista de inteiros, conta o numero de valores negativos, o numero de zeros e o numero de valores positivos, devolvendo um triplo com essa informacao.
-- Certifique-se que a funcao que definiu percorre a lista apenas uma vez

nzp :: [Int] -> (Int,Int,Int)
nzp = foldl (\(n,z,p) x -> if x < 0      then (n+1,z,p) 
                           else if x > 0 then (n,z,p+1) 
                                         else (n,z+1,p)) (0,0,0)

--------------------------------------------------------------------------------------- exercicio 5 ----------------------------------------------------------------------------------------------

-- calcula simultaneamente a divisao e o resto da divisao inteira por subtraccoes sucessivas

-- divMod :: Integral a => a -> a -> (a, a)

--------------------------------------------------------------------------------------- exercicio 6 ----------------------------------------------------------------------------------------------
-- Utilizando uma funcao auxiliar com um acumulador, optimize seguinte definicao recursiva que determina qual o numero que corresponde a uma lista de digitos
{-

fromDigits :: [Int] -> Int
fromDigits [] = 0
fromDigits (h:t) = h*10^(length t) + fromDigits t

Note que
    fromDigits [1,2,3,4] = 1 × 103 + 2 × 102 + 3 × 101 + 4 × 100
                         = 4 + 10 × (3 + 10 × (2 + 10 × (1 + 10 × 0)))

-}

--------------------------------------------------------------------------------------- exercicio 7 ----------------------------------------------------------------------------------------------
-- Optimize a seguinte definicao recursiva da funcao que calcula o n-esimo numero da sequencia de Fibonacci, usando uma funcao auxiliar com 2 acumuladores que 
-- representam, respectivamente, o n-esimo e o n+1-esimo numeros dessa sequencia

{-

fib :: Int -> Int
fib 0 = 0
fib 1 = 1
fib n = fib (n-1) + fib (n-2)

-}

-- Funçoes com listas de compreensão
-- Map
my_map f l = [f l | x <- l]

-- Replicate
my_replicate n x = [x |i <- [1..n]]