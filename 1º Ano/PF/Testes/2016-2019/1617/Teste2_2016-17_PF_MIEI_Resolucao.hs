
module Teste1617 where


{--

1 - Considere o tipo MSet a para representar multi-conjuntos de tipo a
Considere ainda que nestas listas não há pares cuja primeira componente coincida, nem cuja segunda
componente seja menor ou igual a zero. Para alem disso, os multi-conjuntos estao organizados
por ordem decrescente da muiltplicidade. O multi-conjunto {’b’,’a’,’c’,’b’,’b’,’a’,’b’} ´e
representado pela lista [(’b’,4),(’a’,2),(’c’,1)], por exemplo. 

--}

type MSet a = [(a,Int)]

{--

(a) Defina a função cardMSet :: MSet a -> Int que calcula a cardinalidade de um multiconjunto.
Por exemplo, cardMSet [(’b’,4),(’a’,2),(’c’,1)] devolve 7.

--}

cardMSet :: MSet a -> Int
cardMSet [] = 0
cardMSet ((a,b):t) = b + cardMSet t

{--

(b) Defina a função moda :: MSet a -> [a] que devolve a lista dos elementos com maior numero
de ocorrencias.

--}

moda :: MSet a -> [a]
moda [] = []
moda [(a,b)] = [a]
moda ((a,b):(c,d):t) | b > d = [a]
                     | b == d = a : moda ((c,d):t)
                     | otherwise = []

{--

(c) Defina a função converteMSet :: MSet a -> [a] que converte um multi-conjunto numa
lista. Por exemplo, converteMSet [(’b’,4),(’a’,2),(’c’,1)] devolve ‘‘bbbbaac’’.

--}

converteMSet :: MSet a -> [a]
converteMSet [] = []
converteMSet [(a,b)] | b /= 0 = a : converteMSet [(a,(b-1))]
                     | otherwise = []
converteMSet ((a,b):(c,d):t) | b /= 0 = a : converteMSet ((a,(b-1)):(c,d):t)
                             | otherwise = converteMSet ((c,d):t)

{--

(d) Defina a função addNcopies :: Eq a => MSet a -> a -> Int -> MSet a que faz a inserção
de um dado número de ocorrências de um elemento no multi-conjunto, mantendo a ordenação
por ordem decrescente da multiplicidade. Não use uma função de ordenação.

--}

addNcopies :: Eq a => MSet a -> a -> Int -> MSet a
addNcopies [] a n = [(a,n)]
addNcopies ((b,x):t) a n 
    | b == a      = (a,x+n):t
    | otherwise = adjust (b,x) (addNcopies t a n)
  where
        adjust :: Eq a => (a,Int) -> MSet a -> MSet a
        adjust (c,y) ((d,z):resto)
            | y >= z      = (c,y):(d,z):resto
            | otherwise = (d,z):(c,y):resto


{--


2. Considere o seguinte tipo de dados para representar subconjuntos de números reais (Doubles).

--}

data SReais = AA Double Double | FF Double Double
            | AF Double Double | FA Double Double
            | Uniao SReais SReais

{--

(AA x y) representa o intervalo aberto ]x, y[, (FF x y) representa o intervalo fechado [x, y],
(AF x y) representa ]x, y], (FA x y) representa [x, y[ e (Uniao a b) a união de conjuntos.


(a) Defina a SReais como instância da classe Show, de forma a que, por exemplo, a apresentação
do termo Uniao (Uniao (AA 4.2 5.5) (AF 3.1 7.0)) (FF (-12.3) 30.0) seja
((]4.2,5.5[ U ]3.1,7.0]) U [-12.3,30.0])

--}

instance Show SReais where
    show (AA x y) = "]" ++ show x ++ "," ++ show y ++ "["
    show (FF x y) = "[" ++ show x ++ "," ++ show y ++ "]"
    show (AF x y) = "]" ++ show x ++ "," ++ show y ++ "]"
    show (FA x y) = "[" ++ show x ++ "," ++ show y ++ "["
    show (Uniao a b) = "(" ++ show a ++ " " ++ "U" ++ " " ++ show b ++ ")"

{--

(b) Defina a função pertence :: Double-> SReais -> Bool que testa se um elemento pertence
a um conjunto.

--}

pertence :: Double -> SReais -> Bool
pertence n (AA x y) = (n > x) && (n < y)
pertence n (FF x y) = (n >= x) && (n <= y)
pertence n (AF x y) = (n > x) && (n <= y)
pertence n (FA x y) = (n >= x) && (n < y)
pertence n (Uniao a b) = (pertence n a) && (pertence n b)

{--

(c) Defina a função tira :: Double -> SReais -> SReais que retira um elemento de um conjunto.

--}

tira :: Double -> SReais -> SReais
tira n (AA x y) | pertence n (AA x y) = retira n (AA x y)
                | otherwise = (AA x y)
tira n (FF x y) | pertence n (FF x y) = retira n (FF x y)
                | otherwise = (FF x y)
tira n (AF x y) | pertence n (AF x y) = retira n (AF x y)
                | otherwise = (AF x y)
tira n (FA x y) | pertence n (FA x y) = retira n (FA x y)
                | otherwise = (FA x y)
tira n (Uniao x y) | pertence n (Uniao x y) = retira n (Uniao x y)
                | otherwise = (Uniao x y)

retira :: Double -> SReais -> SReais
retira n (AA x y) | ((x < n) && (n < y)) = (Uniao (AA x n) (AA n y))
retira n (FF x y) | ((x <= n) && (n <= y)) = (Uniao (FA x n) (AF n y))
retira n (AF x y) | ((x < n) && (n <= y)) = (Uniao (AA x n) (AF n y))
retira n (FA x y) | ((x <= n) && (n < y)) = (Uniao (FA x n) (AA n y))
retira n (Uniao a b) = (Uniao (retira n a) (retira n b))


{--

3. Considere o seguinte tipo para representar árvores irregulares (rose trees).

--}

data RTree a = R a [RTree a]


arv1 :: RTree Int
arv1 = R 1 [R 3 [R 5 [], R 7 [R 6 [],R 11 []]], R 8 [R 3 [], R 2 []]]

{--

(a) Defina a função percorre :: [Int] -> RTree a -> Maybe [a] que recebe um caminho e
uma árvore e dá a lista de valores por onde esse caminho passa. Se o caminho não for válido
a função deve retornar Nothing. O caminho é representado por uma lista de inteiros (1 indica
seguir pela primeira sub-árvore, 2 pela segunda, etc)

--}

percorre :: [Int] -> RTree a -> Maybe [a]
percorre [] (R x _) = Just [x]
percorre (h:t) (R x l) | h>(length l) = Nothing
                       | otherwise = case (percorre t (l!!(h-1))) of
                                          Just c -> Just (x:c)
                                          Nothing -> Nothing

{--

(b) Defina a função procura :: Eq a => a -> RTree a -> Maybe [Int] que procura um elemento
numa árvore e, em caso de sucesso, calcula o caminho correspondente.

--}

procura :: Eq a => a -> RTree a -> Maybe [Int]
procura x (R y l) | x==y = Just []
procura x (R y []) | x/=y = Nothing
procura x (R y (h:t)) = case (procura x h) of
                               Just c -> Just (1:c)
                               Nothing -> case (procura x (R y t)) of
                                                    Nothing -> Nothing
                                                    Just [] -> Just []
                                                    Just (n:ns) -> Just ((n+1):ns)


