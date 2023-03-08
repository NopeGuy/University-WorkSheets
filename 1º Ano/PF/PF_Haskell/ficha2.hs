module Ficha2 where
import Data.Char

-- Ficha 2 Programacao Funcional

--------------------------------------------------------------------------------------- exercicio 1 ----------------------------------------------------------------------------------------------
-- alinea A
-- dizer qual o output de funA [2,3,5,1]

funA :: [Double] -> Double
funA [] = 0
funA (y:ys) = y^2 + (funA ys)

{-

2^2 + funA (3,5,1) = 
4 + 3^2 + funA (5,1) =
4 + 9 + 5^2 + funA (1:[]) =
4 + 9 + 25 + 1^2 + funA ([]) = 
4 + 9 + 25 + 1 + 0 = 39 

-}

-- alinea B 
-- dizer qual o output de funB [8,5,12]

funB :: [Int] -> [Int]
funB [] = []
funB (h:t) = if (mod h 2)==0 then h : (funB t)
                             else (funB t)

{-

8 : funB (5,12) =
8 : funB (12:[]) = 
8 : 12 : []

-}

-- alinea C
-- dizer qual o output de funC [1,2,3,4,5]
funC (x:y:t) = funC t
funC [x] = []
funC [] = []

{-

funC [1,2,3,4,5] = 
funC [2,3,4,5] = 
funC [3,4,5] = 
funC [4,5] = 
funC [5] =
funC [] =
[]

-}

-- alinea D
-- dizer qual o output de funD "otrec"
funD l = g [] l
g l [] = l
g l (h:t) = g (h:l) t

{-

funD "otrec" = 
g [] "otrec" = 
g [] 'o':"trec" = 
g 'o':[] "trec" = 
g ['o'] 't':"rec" =
g 't':['o'] "rec" = 
g "to" 'r':"ec" = 
g 'r':"to" "ec" = 
g "rto" 'e':['c'] = 
g 'e':"rto" ['c'] = 
g "erto" 'c':[] = 
g 'c':"erto" [] = 
"certo"

-}

--------------------------------------------------------------------------------------- exercicio 2 ----------------------------------------------------------------------------------------------
-- alinea A
-- recebe uma lista e produz a lista em que cada elemento e o dobro do valor correspondente na lista de entrada
dobros :: [Float] -> [Float]
dobros [] = []
dobros (h:t) = (2 * h) : dobros t

-- alinea B
-- calcula o numero de vezes que um caracter ocorre numa string
numOcorre :: Char -> String -> Int
numOcorre _ [] = 0
numOcorre c (h:t) = if c == h then 1 + numOcorre c t
                              else numOcorre c t

-- alinea C
-- testa se uma lista so tem elementos positivos
positivos :: [Int] -> Bool
positivos [h] = h >= 0 -- assume-se que 0 e positivo
positivos (h:t) = if h < 0 then False
                           else positivos t

-- alinea D
-- retira todos os elementos nao positivos de uma lista de inteiros
soPos :: [Int] -> [Int]
soPos [] = []
soPos (h:t) = if h >= 0 then h : soPos t
                        else soPos t 

-- alinea E
-- soma todos os numeros negativos da lista de entrada
somaNeg :: [Int] -> Int
somaNeg [] = 0
somaNeg (h:t) = if h < 0 then h + somaNeg t
                         else somaNeg t

-- alinea F
-- devolve os ultimos tres elementos de uma lista. Se a lista de entrada tiver menos de tres elementos, devolve a propria lista
tresUlt :: [a] -> [a]
tresUlt l | (length l) <= 3 = l
          | otherwise       = tresUlt (tail l)

-- funcoes auxiliares
length' :: [a] -> Int --comprimento da lista
length' [] = 0
length' (h:t) = 1 + length' t

tail' :: [a] -> [a] -- da a cauda de uma lista
tail' [] = []
tail' (h:t) = t

-- alinea G
-- calcula a lista das segundas componentes dos pares
segundos :: [(a,b)] -> [b]
segundos [(a,b)] = [b]
segundos ((a,b) : t) = b : segundos t 

-- alinea I
-- testa se um elemento aparece na lista como primeira componente de algum dos pares
nosPrimeiros :: (Eq a) => a -> [(a,b)] -> Bool
nosPrimeiros x [(a,b)] = x == a
nosPrimeiros x ((a,b):t) = x == a || nosPrimeiros x t

-- alinea J
-- soma uma lista de triplos componente a componente
sumTriplos :: (Num a, Num b, Num c) => [(a,b,c)] -> (a,b,c)
sumTriplos [(a,b,c)] = (a,b,c)
sumTriplos l = (sumA, sumB, sumC)
                where sumA = sum' [a | (a,_,_) <- l]
                      sumB = sum' [b | (_,b,_) <- l]
                      sumC = sum' [c | (_,_,c) <- l]

-- funcao auxiliar
sum' :: Num a => [a] -> a
sum' [] = 0
sum' (h:t) = h + sum' t 

--------------------------------------------------------------------------------------- exercicio 3 ----------------------------------------------------------------------------------------------

-- alinea A
-- que recebe uma lista de caracteres, e selecciona dessa lista os caracteres que sao algarismos
soDigitos :: [Char] -> [Char]
soDigitos [] = []
soDigitos (h:t) = if h `elem'` ['0'..'9'] then h : soDigitos t
                                          else soDigitos t

-- funcao auxiliar
elem' :: Eq a => a -> [a] -> Bool
elem' x [] = False
elem' x (h:t) = if x == h then True else elem x t

-- alinea B
-- recebe uma lista de caracteres, e conta quantos desses caracteres sao letras minusculas
minusculas :: [Char] -> Int
minusculas [h] = if h `elem'`['a'..'z'] then 1 
                                        else 0
minusculas (h:t) = if h `elem'`['a'..'z'] then 1 + minusculas t 
                                          else minusculas t

{- -- funcao auxiliar
elem' :: Eq a => a -> [a] -> Bool
elem' x [] = False
elem' x (h:t) = if x == h then True else elem x t
-}

-- alinea C
-- recebe uma string e devolve uma lista com os algarismos que ocorrem nessa string, pela mesma ordem
nums :: String -> [Int] 
nums [] = []
nums (h:t) = if h `elem'` ['0' .. '9'] then (ord (h) - 48) : nums t
                                       else nums t

--------------------------------------------------------------------------------------- exercicio 4 ----------------------------------------------------------------------------------------------
type Polinomio = [Monomio]
type Monomio = (Float,Int)

-- [(2,3), (3,4), (5,3), (4,5)] representa o polinomio 2(x^3) + 3(x^4) + 5(x^3) + 4(x^5)

-- alinea A
-- (conta n p) indica quantos monomios de grau n existem em p
conta :: Int -> Polinomio -> Int
conta _ []        = 0
conta i [(a,b)]   = if i == b then 1 
                              else 0
conta i ((a,b):t) = if i == b then 1 + conta i t 
                              else conta i t 

-- alinea B
-- indica o grau de um polinomio
grau :: Polinomio -> Int
grau [(a,b)]   = b
grau ((a,b):(c,d):t) = if b > d then grau ((a,b):t) else grau ((c,d):t)   


-- alinea C
-- selecciona os monomios com um dado grau de um polinomio
selgrau :: Int -> Polinomio -> Polinomio
selgrau i [(a,b)]   = if i == b then [(a,b)] 
                              else []
selgrau i ((a,b):t) = if i == b then (a,b): selgrau i t
                                else selgrau i t

-- alinea D
-- calcula a derivada de um polinomio
deriv :: Polinomio -> Polinomio
deriv [(a,b)]   = [(a*(fromIntegral b), b-1)]
deriv ((a,b):t) = (a*(fromIntegral b), b-1) : deriv t

-- alinea E
-- calcula o valor de um polinomio para uma dado valor de x
calcula :: Float -> Polinomio -> Float
calcula _ [] = 0
calcula x ((a,b):t) = a*(x^b) + calcula x t

-- alinea F
-- retira de um polinomio os monomios de coeficiente zero
simp :: Polinomio -> Polinomio
simp [(a,b)]   = if a == 0 then []
                           else [(a,b)]
simp ((a,b):t) = if a == 0 then simp t
                           else (a,b):simp t

-- alinea G
-- calcula o resultado da multiplicacao de um monomio por um polinomio
mult :: Monomio -> Polinomio -> Polinomio
mult (a,b) [(c,d)]   = [(a*c,b+d)]
mult (a,b) ((c,d):t) = (a*c,b+d):mult (a,b) t

-- alinea H
-- dado um polinomio constroi um polinomio equivalente em que nao podem aparecer varios monomios com o mesmo grau
normaliza :: Polinomio -> Polinomio
normaliza [] = []
normaliza [(a,b)] = [(a,b)]
normaliza ((a,b):(c,d):t) = if b == d then normaliza ((a+c,b):t) 
                            else if conta b t == 0 then (a,b):normaliza ((c,d):t)
                                                   else [(c,d)] ++ normaliza ((a,b):t) 

-- alinea I
-- faz a soma de dois polinomios de forma que se os polinomios que recebe estiverem normalizados produz tambem um polinomio normalizado
soma :: Polinomio -> Polinomio -> Polinomio
soma l1 l2 = normaliza (l1 ++ l2)

-- alinea J
-- calcula o produto de dois polinomios
produto :: Polinomio -> Polinomio -> Polinomio
produto [] _ = []
produto (h:t) l = soma (mult h l) (produto t l)

-- alinea K
-- ordena um polinomio por ordem crescente dos graus dos seus monomios
ordena :: Polinomio -> Polinomio
ordena [] = []
ordena [(a,b)] = [(a,b)]
ordena ((a,b):(c,d):t) |b < d            = (a,b) : ordena ((c,d):t)
                       |b == d && a < c  = (a,b) : ordena ((c,d):t)
                      -- |b == d && a > d  = (c,d) : ordena ((a,b):t)
                       |b == d && a == c = (a,b) : (c,d) : ordena t 
                       |otherwise        = (c,d) : ordena ((a,b):t) 


-- alinea L
-- testa se dois polinomios sao equivalentes
equiv :: Polinomio -> Polinomio -> Bool
equiv l1 l2 = ordena (normaliza l1) == ordena (normaliza l2)