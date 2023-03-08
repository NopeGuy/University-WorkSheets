module Ficha7 where

import Data.Char

-- Ficha 7 Programacao Funcional
--------------------------------------------------------------------------------------- exercicio 1 ----------------------------------------------------------------------------------------------
data ExpInt = Const Int
            | Simetrico ExpInt
            | Mais ExpInt ExpInt
            | Menos ExpInt ExpInt
            | Mult ExpInt ExpInt

-- Os termos deste tipo ExpInt podem ser vistos como arvores cujas folhas sao inteiros e cujos nodos (nao folhas) sao operadores

-- alinea A
-- dada uma destas expressoes calcula o seu valor
calcula :: ExpInt -> Int
calcula (Const i)     = i 
calcula (Simetrico i) = - (calcula i)
calcula (Mais i j)    = (calcula i) + (calcula j)
calcula (Menos i j)   = (calcula i) - (calcula j)
calcula (Mult i j)    = (calcula i) * (calcula j) 

-- alinea B
-- de forma a que infixa (Mais (Const 3) (Menos (Const 2) (Const 5)))
-- de como resultado "(3 + (2 - 5))"

infixa :: ExpInt -> String
infixa (Const i)     = [intToDigit (i)]
					   -- show i
infixa (Simetrico i) = ['-']++(infixa i)
infixa (Mais i j)    = ['(']++(infixa i)++['+']++(infixa j)++[')']
infixa (Menos i j)   = ['(']++(infixa i)++['-']++(infixa j)++[')']
infixa (Mult i j)    = ['(']++(infixa i)++['*']++(infixa j)++[')']

-- alinea C
-- de forma a que quando aplicada a expressao acima de como resultado "3 2 5 - +"
posfixa :: ExpInt -> String
posfixa (Const i)     = [intToDigit(i)]
posfixa (Simetrico i) = ['-']++(posfixa i)
posfixa (Mais i j)    = (posfixa i)++(posfixa j)++['+']
posfixa (Menos i j)   = (posfixa i)++(posfixa j)++['-']
posfixa (Mult i j)    = (posfixa i)++(posfixa j)++['*']

--------------------------------------------------------------------------------------- exercicio 2 ----------------------------------------------------------------------------------------------
-- Considere o seguinte tipo para representar arvores irregulares (rose trees)
data RTree a = R a [RTree a]

-- alinea A
-- soma os elementos da arvore
soma :: Num a => RTree a -> a
soma (R v []) = v
soma (R v l)  = v + sum (map (soma) l)

-- alinea B
-- calcula a altura da arvore
altura :: RTree a -> Int
altura (R _ []) = 1
altura (R _ l)  = 1 + maximum (map altura l)

-- alinea C
-- remove de uma arvore todos os elementos a partir de uma determinada profundidade
prune :: Int -> RTree a -> RTree a
prune 0 (R v l) = (R v [])
prune n (R v l) = (R v (map (prune (n-1)) l) )

-- alinea D
-- gera a arvore simetrica
mirror :: RTree a -> RTree a
mirror (R v []) = (R v [])
mirror (R v l)  = (R v (reverse (map (mirror) l)) )

-- alinea E
-- corresponde a travessia postorder da arvore (ESQ, DIR, RAIZ)
postorder :: RTree a -> [a]
postorder (R a []) = [a]
postorder (R a l)  = (my_concatMap (postorder) l) ++ [a]

-- funcao auxiliar
my_concatMap :: (a -> [b]) -> [a] -> [b]
my_concatMap f [] = []
my_concatMap f (h:t) = (f h) ++ (my_concatMap f t )

--------------------------------------------------------------------------------------- exercicio 3 ----------------------------------------------------------------------------------------------
-- Nestas arvores a informacao esta nos nodos (as extermidades da arvore tem apenas uma marca – Empty).
data BTree a = Empty | Node a (BTree a) (BTree a)
 

-- E tambem habitual definirem-se arvores em que a informacao esta apenas nas extermidades (leaf trees):
data LTree a = Tip a | Fork (LTree a) (LTree a)

-- alinea A
-- soma as folhas de uma arvore
ltSum :: Num a => LTree a -> a
ltSum (Tip a) = a
ltSum (Fork esq dir) = ltSum esq + ltSum dir

-- alinea B
-- e lista as folhas de uma arvore (da esquerda para a direita)
listaLT :: LTree a -> [a]
listaLT (Tip a) = [a]
listaLT (Fork esq dir) = (listaLT esq) ++ (listaLT dir)

-- alinea C
-- calcula a altura de uma arvore
ltHeight :: LTree a -> Int
ltHeight (Tip _) = 1
ltHeight (Fork esq dir) = 1 + max (ltHeight esq) (ltHeight dir)

--------------------------------------------------------------------------------------- exercicio 4 ----------------------------------------------------------------------------------------------
-- Estes dois conceitos podem ser agrupados num so, definindo o seguinte tipo:
data FTree a b = Leaf b | No a (FTree a b) (FTree a b)

-- Sao as chamadas full trees onde a informacao esta nao so nos nodos, como tambem nas folhas 
-- (note que o tipo da informacao nos nodos e nas folhas nao tem que ser o mesmo)

-- alinea A
-- separa uma arvore com informacao nos nodos e nas folhas em duas arvores de tipos diferentes
splitFTree :: FTree a b -> (BTree a, LTree b)
splitFTree (Leaf n) = (Empty, Tip n)
splitFTree (No a esq dir) = (Node a (fst (splitFTree esq)) (fst (splitFTree dir)), 
                             Fork   (snd (splitFTree esq)) (snd (splitFTree dir)))

-- alinea B
-- que sempre que as arvores sejam compatıveis as junta numa so
joinTrees :: BTree a -> LTree b -> Maybe (FTree a b)
joinTrees (Empty) (Tip n)             = Just (Leaf n)
joinTrees (Node e esq dir) (Fork a b) = Just (No e junta_esquerda junta_direita)
                                        where 
                                            Just junta_esquerda  = joinTrees esq a
                                            Just junta_direita   = joinTrees dir b
joinTrees _ _ = Nothing
