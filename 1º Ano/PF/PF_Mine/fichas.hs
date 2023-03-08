module Ficha1 where
import Data.Char


--1

--a
perimetro :: Double -> Double
perimetro r = 2 * pi * r

--b
dist :: (Double,Double) -> (Double, Double) -> Double
dist (x1,y1) (x2,y2) = sqrt ((abcissas^2)+(ordenadas^2))
                       where
                        abcissas = x1-x2 
                        ordenadas = y1-y2

--c
primUlt :: [a] -> (a,a)
primUlt l = (head l, last l)

--d
multiplo :: Int -> Int -> Bool
multiplo m n = if mod m n == 0 then True 
                               else False

--e
truncaImpar :: [a] -> [a]
truncaImpar [] = []
truncaImpar x = if mod (length x) 2 == 0 then x
                                         else tail x

--f
max2 :: Int -> Int -> Int
max2 a b = if a > b then a
           else b

--g
max3 :: Int -> Int -> Int -> Int
max3 a b c = max2 (max2 b c) a

--2

nRaizes :: Double->Double->Double-> Int
nRaizes a b c   |delta > 0 = 2
                |delta < 0 = 0
                |delta ==0 = 1
                where delta = b^2 - 4 * a * c

raizes :: Double -> Double -> Double -> [Double]
raizes a b c |nRaizes a b c  == 2 = [divid1/divisor, divid2/divisor]
             |nRaizes a b c  == 1 = [divid1/divisor]
             |otherwise           = []
    where
        delta   = b^2 - 4 * a * c 
        divid1  = -b + sqrt(delta)
        divid2  = -b - sqrt(delta)
        divisor = 2 * a

--3

type Hora = (Int, Int)

--a
validez :: Hora -> Bool
validez (a,b) = (a >= 0 && a < 24) && (b >= 0 && b < 60) 

--b
apos :: Hora -> Hora -> Bool
apos (h1,m1) (h2,m2) | (h1 > h2)  = True
                     | (h1 == h2) && (m1 > m2)  = True
                     | otherwise = False


--c
convertToMin :: Hora -> Int
convertToMin (a,b) = a*60 + b

--d
convertToHour :: Int -> Hora
convertToHour i = (a,b)
            where
            a = div i 60
            b = mod i 60

--e
differenceBetweenHours :: Hora -> Hora -> Int
differenceBetweenHours (h1,m1) (h2,m2) = (h2-h1)*60 + m2 - m1

--f
addMinutes :: Hora -> Int -> Hora
addMinutes (h,m) x = (h+a,m+b)
                    where
                        a = div x 60
                        b = mod x 60

--5

data Semaforo = Verde | Amarelo | Vermelho deriving (Show,Eq)

--a
next :: Semaforo -> Semaforo
next smf | smf == Verde = Amarelo
         | smf == Amarelo = Vermelho
         | otherwise = Verde

--b
stop :: Semaforo -> Bool
stop smf = smf == Vermelho

--c
safe :: Semaforo -> Semaforo -> Bool
safe smf1 smf2 = smf1 == Vermelho || smf2 == Vermelho 

--6

data Ponto = Cartesiano Double Double | Polar Double Double
             deriving (Show, Eq)


posx :: Ponto -> Double
posx (Cartesiano a b) = a
posx (Polar a b)      = a * acos b 

posy :: Ponto -> Double
posy (Cartesiano a b) = b
posy (Polar a b)      = a * asin b 

raio :: Ponto -> Double
raio (Cartesiano a b) = sqrt ((a^2) + (b^2))
raio (Polar a b)      = a

-----------------------------------------------------------------------
--FICHA 2--
--2
--a

dobros :: [Float] -> [Float]
dobros [] = []
dobros (h:hs) = (h*2) : dobros hs

numOcorre :: Char -> String -> Int
numOcorre _ [] = 0
numOcorre c (h:t) = if c == h then 1 + numOcorre c t
                              else numOcorre c t

positivos :: [Int] -> Bool
positivos [] = True
positivos (h:hs) = if h >= 0 then positivos hs
                   else False

soPos :: [Int] -> [Int]
soPos [] = []
soPos (h:hs) = if h >= 0 then h:soPos hs
                   else soPos hs

somaNeg :: [Int] -> Int
somaNeg [] = 0
somaNeg (h:hs) = if h >= 0 then somaNeg hs
                   else h + somaNeg hs

tresUlt :: [a] -> [a]
tresUlt l | (length l) <= 3 = l
          | otherwise       = tresUlt (tail l)

segundos :: [(a,b)] -> [b]
segundos [(a,b)] = [b]
segundos ((a,b) : t) = b : segundos t

nosPrimeiros :: (Eq a) => a -> [(a,b)] -> Bool
nosPrimeiros _ [] = False
nosPrimeiros x ((a,b):t) | x==a = True
                      | otherwise = nosPrimeiros x t

--ver este
sumTriplos :: (Num a, Num b, Num c) => [(a,b,c)] -> (a,b,c)
sumTriplos [(a,b,c)] = (a,b,c)
sumTriplos l = (sumA, sumB, sumC)
                where sumA = sum [a | (a,_,_) <- l]
                      sumB = sum [b | (_,b,_) <- l]
                      sumC = sum [c | (_,_,c) <- l]

--3

soDigitos :: [Char] -> [Char]
soDigitos [] = []
soDigitos (h:t) = if h `elem'` ['0'..'9'] then h : soDigitos t
                                          else soDigitos t

-- funcao auxiliar
elem' :: Eq a => a -> [a] -> Bool
elem' x [] = False
elem' x (h:t) = if x == h then True else elem x t

minusculas :: [Char] -> Int
minusculas [] = 0
minusculas (h:t) = if h `elem'` ['a'..'z'] then 1 + minusculas t
                    else minusculas t


--errada
-- nums :: String -> [Int]
-- nums [] = []
-- nums (h:t) = if h `elem'` ['0'..'9'] then h : nums t
--                                      else nums t
--certa
nums :: String -> [Int] 
nums [] = []
nums (h:t) = if h `elem'` ['0' .. '9'] then (ord (h) - 48) : nums t
                                       else nums t

--4
type Polinomio = [Monomio]
type Monomio = (Float,Int)

conta :: Int -> Polinomio -> Int
conta _ [] = 0
conta n ((a,b):t)
    | n == b = 1 + conta n t
    | otherwise = conta n t


grau :: Polinomio -> Int
grau [] = 0
grau ((a,b):t)
        |b > grau t = b
        |otherwise = grau t

selgrau :: Int -> Polinomio -> Polinomio
selgrau _ [] = []
selgrau x ((a,b):t)
        |x == b = (a,b) : selgrau x t
        |otherwise = selgrau x t

deriv :: Polinomio -> Polinomio
deriv [] = []
deriv ((a,b):t) = (c,b-1) : deriv t
        where c = (a*(fromIntegral b))

calcula :: Float -> Polinomio -> Float
calcula _ [] = 0
calcula x ((a,b):t) = a*(x^b) + calcula x t

simp :: Polinomio -> Polinomio
simp [] = []
simp ((a,b):t) 
        |b==0 = simp t
        |otherwise = (a,b) : simp t

mult :: Monomio -> Polinomio -> Polinomio
mult _ [] = []
mult (a,b) ((c,d):t) = (y,z) : mult (a,b) t
                       where y=(a*c)
                             z=(b+d)

normaliza :: Polinomio -> Polinomio
normaliza [] = []
normaliza [(a,b)] = [(a,b)]
normaliza ((a,b):(c,d):t) = if b == d then normaliza ((a+c,b):t) 
                            else if conta b t == 0 then (a,b):normaliza ((c,d):t)
                                                   else [(c,d)] ++ normaliza ((a,b):t) 

{-
Explicação
A 3º linha da normaliza equivale a um caso de paragem extra na eventualidade da
lista introduzida ter só um monomio, como da maneira que a função representada está
escrita é sempre necessário haver mais do que um monomio no polinomio para que possa
haver a comparação
De seguida na linha 4, é comparado o grau do monomio que está a ser comparado com o 
seguinte para que se verifique se é igual e caso seja é somado o termo independente
do monomio. Caso isto não se verifique é chamada uma função que conta as ocorrências
de um grau de monómio no polinomio e verifica-se se ainda existe alguma (se conta 
devolver um valor >0), se se verificar a existencia de outro monomio com o mesmo
grau no polinomio entao a função é chamada
-}

soma :: Polinomio -> Polinomio -> Polinomio
soma p1 p2 = normaliza (p1 ++ p2)

produto :: Polinomio -> Polinomio -> Polinomio
produto [] _ = []
produto (h:t) x = (mult h x) ++ produto t x

ordena :: Polinomio -> Polinomio
ordena [] = []
ordena (m:t) = insere m (ordena t)

insere :: Monomio -> Polinomio -> Polinomio
insere m [] = [m]
insere (cm,gm) ((c,g):t)
    | g > gm = (cm,gm) : (c,g) : t
    | otherwise = (c,g) : insere (cm,gm) t

-- (1,3) ((2,2),(5,2),(4,3))
--ver again

----------------------------------------------------------------------------
--Ficha 3

--1
data Hora2 = H Int Int
            deriving Show

type Etapa = (Hora2, Hora2)
type Viagem = [Etapa]

--[(H 9 30, H 10 25), (H 11 20, H 12 45), (H 13 30, H 14 45)]
{-significa que teve trˆes etapas:
•a primeira come ̧cou `as 9 e meia e terminou `as 10 e 25;
•a segunda come ̧cou `as 11 e 20 e terminou `a uma menos um quarto;
•a terceira come ̧cou `as 1 e meia e terminou `as 3 menos um quarto-}

testa_etapa :: Etapa -> Bool
testa_etapa ((H a b),(H c d)) = (testaHoras_validas1 (H a b)) && (testaHoras_validas1 (H c d)) && (testaHoras_comparacao1 (H a b) (H c d) == False)

-- funcoes auxiliares

testaHoras_validas1 :: Hora2 -> Bool -- testar se um par de inteiros representa uma hora do dia valida
testaHoras_validas1 (H a b )= if (a >= 0 && a < 24) && (b >= 0 && b <= 59) then True else False

testaHoras_comparacao1 :: Hora2 -> Hora2 -> Bool -- testar se uma hora e ou nao depois de outra (comparacao) - referimos a primeira hora introduzida
testaHoras_comparacao1 (H a b) (H c d) | (a > c)  && testaHoras_validas1 (H a b) && testaHoras_validas1 (H c d)            = True
                                       | (a == c) && testaHoras_validas1 (H a b) && testaHoras_validas1 (H c d) && (b > d) = True
                                       | otherwise = False


--4

type Dia = Int
type Mes = Int
type Ano = Int
type Nome = String

data Data = D Dia Mes Ano
            deriving Show

type TabDN = [(Nome,Data)]

boda :: TabDN
boda = [("Luis", (D 25 11 2000)),("Ana", (D 20 4 1999))]

procura :: Nome -> TabDN -> Maybe Data 
procura _ [] = Nothing
procura nome ((n,d):ts) | nome == n = Just d
                        |otherwise = procura nome ts

--Em maybe tem que se pôr Just na variavel a receber

-- idade :: Data -> Nome -> TabDN -> Maybe Int
-- idade _ _ [] = Nothing
-- idade (D dx mx ax) nome ((n,D d m a):ts) 
--         |nome == n = Just (calculaIdade (D d m a) (D dx mx ax))
--         |otherwise idade (D dx mx ax) nome ts

calculaIdade :: Data -> Data -> Int
calculaIdade (D dn mn an) (D d m a) = if m > mn || m == mn 
    && d > dn then a - an else a - an - 1

---------------------------------------------------------------------------
--Ficha 4

digitAlpha :: String -> (String,String)
digitAlpha string = foldl (\(alpha,digit) x -> if      isDigit x then (alpha,digit ++ [x]) 
                                               else if isAlpha x then (alpha ++ [x],digit) 
                                                                 else (alpha,digit)) ("","") string

digitAlpha2 :: String -> (String,String)
digitAlpha2 s = ((filter (isAlpha) s), (filter (isDigit) s))

nzp :: [Int] -> (Int,Int,Int)
nzp l = foldl (\(neg,zer,pos) x -> if x<0 then (neg+1,zer,pos)
                                        else if x==0 then (neg,zer+1,pos)
                                        else (neg,zer,pos+1)) (0,0,0) l

nzp2 :: [Int] -> (Int,Int,Int)
nzp2 l = (length [l | xs <- l, xs<0],length [l | xs <- l, xs==0], length [l | xs <- l, xs>0])
----------------------------------------------------------------------------
--Ficha 5

my_any :: (a -> Bool) -> [a] -> Bool
my_any _ []    = False
my_any f (h:t) = if f h then True 
                        else my_any f t

my_zipWith :: (a->b->c) -> [a] -> [b] -> [c]
my_zipWith x (h1:t1) (h2:t2) = (x h1 h2) : my_zipWith x t1 t2
my_zipWith x _ [] = []
my_zipWith x [] _ = []

my_takeWhile :: (a->Bool) -> [a] -> [a]
my_takeWhile x [] = []
my_takeWhile x (h:t)|x h ==True = h:my_takeWhile x t
                |otherwise = []

my_dropWhile :: (a->Bool) -> [a] -> [a]
my_dropWhile x [] = []
my_dropWhile x (h:t)|x h ==True = my_dropWhile x t
                    |otherwise = h:t

my_span :: (a-> Bool) -> [a] -> ([a],[a])                   
my_span p l = (takeWhile p l, dropWhile p l)

my_deleteBy :: (a -> a -> Bool) -> a -> [a] -> [a] 
my_deleteBy _ a []    = []
my_deleteBy f a (h:t) = if f a h then t 
                                 else h:my_deleteBy f a t 

my_sortOn :: Ord b => (a -> b) -> [a] -> [a]
my_sortOn f [] = []
my_sortOn f (h:t) = insere h (my_sortOn f t)
	where insere x [] = [x]
	      insere x (y:ys) = if f x > f y then y:insere x ys else x:y:ys
--n sei esta

--2

selgrau2 :: Int -> Polinomio -> Polinomio
selgrau2 g p = filter (\x -> snd x == g) p

conta2 :: Int -> Polinomio -> Int
conta2 g p = length(filter(\x-> snd x == g) p)
{-
conta2 g ((a,b):t) = length(filter(\(x,y)-> y == g) ((a,b):t))
-}

grau2 :: Polinomio -> Int
grau2 p = foldl(\ac x -> max ac (snd x)) 0 p
--max x->y->z

deriv2 :: Polinomio -> Polinomio
deriv2 [] = []
deriv2 p = filter (/= (0,-1)) (map(\(x,y)-> (x*(fromIntegral y), (y-1))) p)

calcula2 :: Float -> Polinomio -> Float
calcula2 x =foldl(\acc (c,g) -> c*(x^g) + acc) 0
--tb n percebi esta

simp2 :: Polinomio -> Polinomio
simp2 [] = []
simp2 p = filter(\x->snd x/=0) p

mult2 :: Monomio -> Polinomio -> Polinomio
mult2 (x,y) p = map(\(a,b)-> (x*a, y+b)) p

ordena2 :: Polinomio -> Polinomio
ordena2 = my_sortOn (snd)

normaliza2 :: Polinomio -> Polinomio
normaliza2 p = foldl f [] (ordena2 p)
 where
 f [] x = [x]
 f ((a,b):t) (c,d) | b == d = f t (a+c,b)
                   | otherwise = (a,b):f t (c,d)
--e esta tb n

soma2 :: Polinomio -> Polinomio -> Polinomio
soma2 p1 p2 = normaliza2 (p1 ++ p2)

produto2 :: Polinomio -> Polinomio -> Polinomio
produto2 p1 p2 = concat(map(\m -> mult2 m p2) p1)
--Por exemplo, se p1 = [1, 2, 3] e p2 = [4, 5, 6], 
--a expressão map(\m -> mult2 m p2) p1 irá produzir a lista 
--[mult2 1 p2, mult2 2 p2, mult2 3 p2].


equiv :: Polinomio -> Polinomio -> Bool
equiv p1 p2 |normaliza p1 == normaliza p2 = True
            |otherwise = False
--fazer mais 2 e a pergunta 3

--3

type Mat a = [[a]]

dimOK :: Mat a -> Bool
dimOK (h:t) = all(\x -> length x == length h) t 
--função all aplica a função a todos elems da lista

dimMat :: Mat a -> (Int,Int)
dimMat [] = (0,0)
dimMat l = (length l, length (head l))

addMat :: Num a => Mat a -> Mat a -> Mat a
addMat m1 m2 = my_zipWith(my_zipWith(+)) m1 m2

transpose :: Mat a -> Mat a
transpose ([]:_) = []
transpose m      = (map head m) : transpose (map tail m)
-------------------------------------------------------------


data BTree a = Empty
               |Node a (BTree a) (BTree a)
               deriving Show

a1 = Node 5 (Node 3 Empty (Node 4 Empty Empty)) (Node 9 Empty (Node 10 Empty Empty))


--1
altura :: BTree a -> Int
altura Empty = 0
altura (Node _ e d) = max (altura e) (altura d)

contaNodos :: BTree a -> Int
contaNodos Empty = 0
contaNodos (Node _ e d) = 1 + (contaNodos e) + (contaNodos d)

folhas :: BTree a -> Int
folhas Empty = 0
folhas (Node n Empty Empty) = 1
folhas (Node n l r) = folhas l + folhas r


prune :: Int -> BTree a -> BTree a
prune 0 _ = Empty	
prune _ Empty = Empty
prune x (Node n l r) = Node n (prune(x-1) l) (prune(x-1) r)	 


path :: [Bool] -> BTree a -> [a]
path _ Empty = []
path [] (Node n _ _)  = [n]
path (h:t) (Node n l r) | h = n : path t r 
		          | otherwise = n : path t l 


mirror :: BTree a -> BTree a
mirror Empty = Empty
mirror (Node n l r) =  Node n (mirror l) (mirror r)

zipWithBT :: (a -> b -> c) -> BTree a -> BTree b -> BTree c
zipWithBT _ Empty _ = Empty
zipWithBT _ _ Empty = Empty
zipWithBT f (Node n1 l1 r1) (Node n2 l2 r2) = Node (f n1 n2) (zipWithBT f l1 l2) (zipWithBT f r1 r2)



unzipBT :: BTree (a,b,c) -> (BTree a,BTree b,BTree c)
unzipBT Empty = (Empty,Empty,Empty)
unzipBT (Node (a,b,c) l r) = (Node a a1 a2, Node b b1 b2, Node c c1 c2)
 where
 (a1,b1,c1) = unzipBT l
 (a2,b2,c2) = unzipBT r
a2 = Node (1,2,3) (Node (4,5,6) Empty Empty) (Node (9,0,1) Empty Empty)
{-
       (1,2,3)
       /      \
   (4,5,6)    (9,0,1)
-}

--2. Árvores binárias de procura-------------------------------------------
minimo :: Ord a => BTree a -> a
minimo (Node n Empty _) = n
minimo (Node n l _) = minimo l


semMinimo :: Ord a => BTree a -> BTree a
semMinimo (Node n Empty _) = Empty
semMinimo (Node n l r) = Node n (semMinimo l) r


minSmin :: Ord a => BTree a -> (a,BTree a)
minSmin (Node n Empty r) = (n,r)
minSmin (Node n l r) = (m, Node n l2 r)
 where
 (m,l2) = minSmin l2

remove :: Ord a => a -> BTree a -> BTree a
remove _ Empty = Empty
remove n (Node n' e d)
 | n < n' = Node n' (remove n e) d
 | n > n' = Node n' e (remove n d)
 | otherwise = case d of
  Empty -> e
  _     -> Node m e d'
  where
  (m,d') = minSmin d



--3. Árvore binárias com dados do tipo Turma------------------------------- 
type Aluno = (Numero,Nome,Regime,Classificacao)

type Numero = Int
type Nome = String
data Regime = ORD | TE | MEL deriving Show
data Classificacao = Aprov Int
                   | Rep
                   | Faltou

type Turma = BTree Aluno -- árvore binária de procura (ordenada por número)


inscNum :: Numero -> Turma -> Bool
inscNum _ Empty = False
inscNum x (Node (n,_,_,_) l r) = n == x || inscNum x (if x < n then l else r)



inscNome :: Nome -> Turma -> Bool
inscNome _ Empty = False
inscNome n (Node (_,nome,_,_) l r) = n == nome || inscNome n l || inscNome n r

trabEst :: Turma -> [(Numero,Nome)]
trabEst Empty = []
trabEst (Node (numero,n,regime,_) l r) = case regime of
 TE -> trabEst l ++ [(numero,n)] ++ trabEst r
 _  -> trabEst l ++ trabEst r



nota :: Numero -> Turma -> Maybe Classificacao
nota _ Empty = Nothing
nota n (Node (numero,_,_,classificacao) l r) | n == numero = Just classificacao
                                             | n < numero = nota n l
                                             | otherwise = nota n r 

percFaltas :: Turma -> Float
percFaltas Empty = 0
percFaltas turma = somaDasFaltas (turma) / contaAlunos (turma) * 100
 where
 somaDasFaltas :: Turma -> Float
 somaDasFaltas (Node (_,_,_,classificacao) l r) = case classificacao of
  Faltou -> 1 + percFaltas l + percFaltas r 
  _      -> 0 + percFaltas l + percFaltas r

contaAlunos :: Turma -> Float
contaAlunos Empty = 0
contaAlunos (Node n l r) = 1 + contaAlunos l + contaAlunos r  

mediaAprov :: Turma -> Float
mediaAprov Empty = 0
mediaAprov turma = somaAprov turma / contaAprov turma
 where
 somaAprov :: Turma -> Float
 somaAprov (Node (_,_,_,classificacao) l r) = case classificacao of
  Aprov a -> fromIntegral a + somaAprov l + somaAprov r
  _       -> 0 + somaAprov l + somaAprov r

 contaAprov :: Turma -> Float
 contaAprov (Node (_,_,_,classificacao) l r) = case classificacao of
  Aprov a -> 1 + contaAprov l + contaAprov r
  _       -> 0 + contaAprov l + contaAprov r


aprovAv :: Turma -> Float
aprovAv Empty = 0
aprovAv turma = aprovados / avaliados
 where
 (aprovados, avaliados) = aprovAvAux turma
 aprovAvAux :: Turma -> (Float,Float)
 aprovAvAux Empty = (0,0)
 aprovAvAux (Node (_,_,_,classificacao) l r) = case classificacao of
  Aprov avaliacao -> (a+1,b+1)
  Rep             -> (a,b+1)
  _               -> (a,b)
  where
  (a,b)   = (a1+a2,b1+b2)
  (a1,b1) = aprovAvAux l
  (a2,b2) = aprovAvAux r 

        