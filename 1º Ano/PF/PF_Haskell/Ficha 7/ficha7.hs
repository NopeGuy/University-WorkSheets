module Ficha7 where



--1
data ExpInt = Const Int
 | Simetrico ExpInt
 | Mais ExpInt ExpInt
 | Menos ExpInt ExpInt
 | Mult ExpInt ExpInt
{-            Mais
            /      \
        Const 3     Menos
                /         \
           Const 2       Const 5
-}
-- 3 + (2 - 5)
a1 = Mais (Const 3) (Menos (Const 2) (Const 5))
a2 = Menos (Const 1) (Const 3)

--Exercico 1---------------------------
calcula :: ExpInt -> Int
calcula er = case er of
 Const n     -> n
 Simetrico e -> - calcula e
 Mais e1 e2  -> (calcula e1) + (calcula e2)
 Menos e1 e2 -> (calcula e1) - (calcula e2)
 Mult e1 e2  -> (calcula e1) * (calcula e2)

infixa :: ExpInt -> String
infixa expr = case expr of
 Const n     -> show n
 Simetrico e -> "-(" ++ infixa e ++ ")"
 Mais e1 e2  -> "(" ++ (infixa e1) ++ "+" ++ (infixa e2) ++ ")"
 Menos e1 e2 -> "(" ++ (infixa e1) ++ "-" ++ (infixa e2) ++ ")"
 Mult e1 e2  -> "(" ++ (infixa e1) ++ "*" ++ (infixa e2) ++ ")" 


posfixa :: ExpInt -> String
posfixa expr = case expr of
 Const n     -> show n ++ " "
 Simetrico e -> "-" ++ posfixa e
 Mais e1 e2  -> posfixa e1 ++ posfixa e2 ++ "+ "
 Menos e1 e2 -> posfixa e1 ++ posfixa e2 ++ "- "
 Mult e1 e2  -> posfixa e1 ++ posfixa e2 ++ "* "posfixa :: ExpInt -> String

--Exercicio 2 Rose trees -------------------------
data RTree a = R a [RTree a] deriving Show
{-        R 10
      /    |    \    \
  [  5     4     2    1  ]
   /  \    |     |    |
 [12  13] [ ]   [ ]  [ ]
  |   | 
 [ ] [ ]
-}
a3 = R 10 [R 5 [R 12 [], R 13 []], R 4 [], R 2 [], R 1 []] 


soma :: Num a => RTree a -> a
soma (R n l) = n + sum(map soma l)

altura :: RTree a -> Int
altura (R n []) = 1
altura (R n l) = 1 + maximum (map altura l)


prune :: Int -> RTree a -> RTree a
prune 0 (R n l) = R n []
prune x (R n l) = R n (map (prune (x-1)) l)


mirror :: RTree a -> RTree a
mirror (R n l) = R n (reverse (map mirror l))


postorder :: RTree a -> [a]
postorder (R n l) = concat(map postorder l) ++ [n]

--Exercicio 3-------------------------
data BTree a = Empty | Node a (BTree a) (BTree a) deriving Show
data LTree a = Tip a | Fork (LTree a) (LTree a) deriving Show

a4 = Fork (Fork (Tip 2) (Tip 3)) (Fork (Tip 2) (Fork (Fork (Tip 2) (Tip 4)) (Tip 2)))

{- 
              No 'b'
              /    \
      No 'c'        leaf 4
     /     \
 Leaf 1    Leaf 2
-}
a6 = No 'b' (No 'c' (Leaf 1) (Leaf 2)) (Leaf 4)
--(BTree Char,Ltree Int)
{-   Node 'b'                 Fork
    /      \                 /     \
 Node 'c'                  Fork     Tip 4
 /   \                     / \
                        Tip 1 Tip 2
-}

splitFTree :: FTree a b -> (BTree a, LTree b)
splitFTree a = case a of
 Leaf b   -> (Empty, Tip b)
 No a l r -> (Node a x y, Fork x' y')
  where
  (x,x') = splitFTree l
  (y,y') = splitFTree r


joinTrees:: BTree a -> LTree b -> Maybe (FTree a b)
joinTrees Empty (Tip b) = Just (Leaf b)
joinTrees (Node x l1 r1) (Fork l2 r2) = case joinTrees l1 l2 of 
    Nothing -> Nothing
    Just f -> case joinTrees r1 r2 of
        Just g -> Just (No x f g)
        Nothing -> Nothing
joinTrees _ _ = Nothing

