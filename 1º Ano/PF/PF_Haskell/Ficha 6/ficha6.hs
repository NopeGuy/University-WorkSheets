module Ficha6 where



data BTree a = Empty
             | Node a (BTree a) (BTree a)
		    deriving Show

a1 = Node 5 (Node 3 Empty (Node 4 Empty Empty)) (Node 9 Empty (Node 10 Empty Empty))
{-
            5 
          /   \
         3     9
          \     \
           4     10
-}


--1. Árvore Binária --------------------------------------------------
altura :: BTree a -> Int	
altura Empty = 0
altura (Node n l r) = max (1 + altura l) (1+ altura r)

contaNodos :: BTree a -> Int
contaNodos Empty = 0
contaNodos (Node n l r) = 1 + contaNodos l + contaNodos r

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



{-

trabEst :: Turma -> [(Numero,Nome)]
nota :: Numero -> Turma -> Maybe Classificacao
percFaltas :: Turma -> Float
mediaAprov :: Turma -> Float
aprovAv :: Turma -> Float
-}
