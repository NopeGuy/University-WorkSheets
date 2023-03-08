module Ficha6 where

import Data.Char

-- Ficha 6 Programacao Funcional
--------------------------------------------------------------------------------------- exercicio 1 ----------------------------------------------------------------------------------------------
data BTree a = Empty
             | Node a (BTree a) (BTree a)
             deriving Show

-- alinea A
-- calcula a altura da arvore
altura :: BTree a -> Int
altura Empty            = 0
altura (Node _ esq dir) = max (1 + altura esq) (1 + altura dir)

-- alinea B
-- calcula o numero de nodos da arvore
contaNodos :: BTree a -> Int 
contaNodos Empty            = 0
contaNodos (Node _ esq dir) = 1 + contaNodos (esq) + contaNodos (dir)

-- alinea C
-- calcula o numero de folhas (i.e., nodos sem descendentes) da arvore
folhas :: BTree a -> Int
folhas Empty                = 0
folhas (Node a Empty Empty) = 1
folhas (Node _ esq dir)     = folhas (esq) + folhas (dir)

-- alinea D
-- remove de uma arvore todos os elementos a partir de uma determinada profundidade
prune :: Int -> BTree a -> BTree a
prune _ Empty            = Empty
prune 0 _                = Empty
prune x (Node a esq dir) = Node a (prune (x-1) esq) (prune (x-1) dir)

-- alinea E
-- e dado um caminho (False correspondea esquerda e True a direita) e uma arvore,
-- da a lista com a informacao dos nodos por onde esse caminho passa
path :: [Bool] -> BTree a -> [a]
path _ Empty                = []
path [] (Node e esq dir)    = [e]
path (h:t) (Node a esq dir) = a : path t (if h then dir else esq)

-- alinea F
-- da a arvore simetrica
mirror :: BTree a -> BTree a
mirror Empty            = Empty
mirror (Node a esq dir) = Node a (mirror dir) (mirror esq)

-- alinea G
-- generaliza a funcao zipWith para arvores binarias
zipWithBT :: (a -> b -> c) -> BTree a -> BTree b -> BTree c
zipWithBT f (Node a esq dir) (Node a1 esq1 dir1) = Node (f a a1) (zipWithBT f esq esq1) (zipWithBT f dir dir1)
zipWithBT _ _ _                                  = Empty

-- alinea H
-- generaliza a funcao unzip (neste caso de triplos) para arvores binarias
unzipBT :: BTree (a,b,c) -> (BTree a,BTree b,BTree c)
unzipBT Empty                     = (Empty, Empty, Empty)
unzipBT (Node (r1,r2,r3) esq dir) = (Node r1 unzip_esq1 unzip_dir1,
                                     Node r2 unzip_esq2 unzip_dir2,
                                     Node r3 unzip_esq3 unzip_dir3)
                                where 
                                    (unzip_esq1,unzip_esq2,unzip_esq3) = unzipBT esq
                                    (unzip_dir1,unzip_dir2,unzip_dir3) = unzipBT dir

--------------------------------------------------------------------------------------- exercicio 2 ----------------------------------------------------------------------------------------------
--  Defina as seguintes funcoes, assumindo agora que as arvores sao BINARIAS DE PROCURA

-- alinea A
-- calcula o menor elemento de uma arvore binaria de procura nao vazia
minimo :: Ord a => BTree a -> a
minimo (Node r Empty _) = r
minimo (Node r esq dir) = minimo esq

-- alinea B
-- remove o menor elemento de uma arvore binaria de procura nao vazia
semMinimo :: Ord a => BTree a -> BTree a
semMinimo (Node _ Empty _) = Empty
semMinimo (Node a esq dir) = Node a (semMinimo esq) dir

-- alinea C
-- calcula, com uma unica travessia da arvore o resultado das duas funcoes anteriores
minSmin :: Ord a => BTree a -> (a,BTree a)
minSmin (Node r Empty _) = (r,Empty)
minSmin (Node r esq dir) = (a,Node r b dir)
                         where 
                            (a,b) = minSmin esq

-- alinea D
-- remove um elemento de uma arvore binaria de procura, usando a funcao anterior
{-
remove :: Ord a => a -> BTree a -> BTree a
remove _ Empty        = Empty
remove x (Node a esq dir) | x < a = Node a (remove x esq) dir
                          | x > a = Node a esq (remove x dir)
                          | otherwise = aux x (Node a esq dir)
                          where
                            aux n (Node a1 esq1 dir1) = case esq1 of Empty -> dir1
                                                                     otherwise -> case dir1 of Empty -> esq1
                                                                                               otherwise -> Node g esq1 h
                            (g,h) = minSmin dir
-}
--------------------------------------------------------------------------------------- exercicio 3 ----------------------------------------------------------------------------------------------

type Aluno = (Numero,Nome,Regime,Classificacao)
type Numero = Int
type Nome = String
data Regime = ORD | TE | MEL deriving Show
data Classificacao = Aprov Int
                   | Rep
                   | Faltou
                   deriving Show
type Turma = BTree Aluno -- arvore binaria de procura (ordenada por numero)

-- alinea A
-- verifica se um aluno, com um dado numero, esta inscrito
inscNum :: Numero -> Turma -> Bool
inscNum _ Empty                           = False
inscNum num (Node (numero,_,_,_) esq dir) = num == numero ||
                                            inscNum num (if num < numero then esq else dir) 

-- alinea B
-- verifica se um aluno, com um dado nome, esta inscrito
inscNome :: Nome -> Turma -> Bool
inscNome _ Empty                           = False
inscNome name (Node (_,nome,_,_) esq dir) = name == nome ||
                                            inscNome name esq ||
                                            inscNome name dir 

-- alinea C
-- lista o numero e nome dos alunos trabalhadores-estudantes (ordenados por numero)
trabEst :: Turma -> [(Numero,Nome)]
trabEst Empty = []
trabEst (Node (numero, nome, reg, _) esq dir) = (case reg of TE -> [(numero, nome)]; otherwise -> []) ++ trabEst esq ++ trabEst dir

-- alinea D
-- calcula a classificacao de um aluno (se o aluno nao estiver inscrito a funcao deve retornar Nothing)
nota :: Numero -> Turma -> Maybe Classificacao
nota num (Node (numero,_,_,clsf) esq dir) = if num == numero then Just clsf
                                                             else nota num (if num < numero then esq else dir) 
nota _ _ = Nothing

-- alinea E
-- calcula a percentagem de alunos que faltaram a avaliacao
percFaltas :: Turma -> Float
percFaltas Empty = 0.0
percFaltas turma = (calcula_faltosos turma / calcula_alunos_total turma) * 100
                    where
                    calcula_faltosos Empty = 0
                    calcula_faltosos (Node (_,_,_,classf) esq dir) = (case classf of Faltou -> 1; otherwise -> 0) + calcula_faltosos esq + calcula_faltosos dir
                                                    
                    calcula_alunos_total Empty = 0
                    calcula_alunos_total (Node r esq dir) = 1 + calcula_alunos_total esq + calcula_alunos_total dir

-- alinea F
-- calcula a media das notas dos alunos que passaram
mediaAprov :: Turma -> Float
mediaAprov Empty = 0.0
mediaAprov turma =  (fromIntegral (calcula_aprovados turma) / fromIntegral (calcula_numero_de_aprovados turma))
                   where
                    calcula_aprovados Empty = 0
                    calcula_aprovados (Node (_,_,_,classf) esq dir) = (case classf of Aprov a -> a; otherwise -> 0) 
                                                                     + calcula_aprovados esq 
                                                                     + calcula_aprovados dir

                    calcula_numero_de_aprovados Empty = 0
                    calcula_numero_de_aprovados (Node (_,_,_,classf) esq dir) = (case classf of Aprov a -> 1; otherwise -> 0) 
                                                                               + calcula_numero_de_aprovados esq 
                                                                               + calcula_numero_de_aprovados dir



-- alinea G
-- calcula o racio de alunos aprovados por avaliados. Implemente esta funcao fazendo apenas uma travessia da arvore
{-
aprovAv :: Turma -> Float
aprovAv Empty = 0
aprovAv turma = a / b
    where (a,b) = aux turma
          aux Empty = (0,0)
          aux (Node (_,_,_,clas) l r) = case clas of Aprov nota -> (x+1,y) ; Rep -> (x,y+1) ; otherwise -> (x,y)
            where (x,y) = (c+e,d+f)
                  (c,d) = aux l
                  (e,f) = aux r
-}

-- EXERCICIO EXTRA 1
-- Dada uma arvore binaria, coloca os seus elementos numa lista
toList :: BTree a -> [a]
toList Empty = []
toList (Node x esq dir) = [x] ++ toList esq ++ toList dir  -- travessia preorder 
                       -- toList esq ++ [x] ++ toList dir  -- travessia inorder
                       -- toList esq ++ toList dir ++ [x]  -- travessia posorder

-- EXERCICIO EXTRA 2
-- Dada uma lista, coloca os seus elementos numa arvore binaria
fromList :: [a] -> BTree a
fromList []    = Empty
fromList (h:t) = (Node h Empty fromList t) 