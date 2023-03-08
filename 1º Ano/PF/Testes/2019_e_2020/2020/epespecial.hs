import Data.List


{-que retorna a lista resultante de remover da primeira
lista os elementos que não pertencem à segunda.-}

myintersect :: Eq a => [a] -> [a] -> [a]
myintersect [] [] = []
myintersect (h:t) l = if elem h l then h: myintersect t l
                    else myintersect t l

myintersect2 l l2 = [ x | x <- l , elem x l2]

myintersect3 l l2 = filter (\x -> elem x l2 ) l



{-que calcula a lista dos sufixos de uma lista. Por exemplo, mytails [1,2,3]
corresponde a [[1,2,3],[2,3],[3],[]].-}

mytails :: [a] -> [[a]]
mytails [] = [[]]
mytails l  = l : mytails (tail l)

mytails2 l = [ drop x l | x <- [0..length l]] 


type ConjInt = [Intervalo]
type Intervalo = (Int,Int)

{-que, dado um conjunto, dá como resultado a lista
dos elementos desse conjunto.-}

elems :: ConjInt -> [Int]
elems l = concat $ map (uncurry enumFromTo) l

elems2 l = concat [  [x..y]  | (x,y) <- l]
{--geraconj [1,2,3,4,7,8,19,21,22,23] = [(1,4),(7,8),(19,19),(21,23)].-}


{-
geraconj :: [Int] -> ConjInt
geraconj [] = []
geraconj l@(h:t)  = (h,m) : geraconj (drop (m-h) l) 
    where (h,m)   = aux h h l
aux :: Int -> Int -> [Int] -> (Int,Int)
aux h m [] = (h,m)
aux h m (h2:t) = if h2 - m == 1 then aux h h2 t 
                 else (h,m) 
-}
--[1,1,1,1] -> [(1,1)]
--[1,2,3,4,7,8,19,21,22,23]
--[1,2,3,4]
-- (1,4) :    f [7,8,19,21,22,23]


geraconj :: [Int] -> ConjInt
geraconj [] = [] 
geraconj l  =  par: geraconj (drop (length pseq) l)
    where pseq = gaux l
          par  = pu pseq 

pu:: [Int] -> (Int, Int)
pu [x]   = (x, x)
pu (h:t) = (h, last t)

gaux :: [Int] -> [Int]
gaux []    = [] 
gaux [a]   = [a]
gaux (h:h2:t) = if h2-h <= 1 then h : gaux (h2:t)
                else [h]






data Contacto = Casa Integer
              | Trab Integer
              | Tlm Integer
              | Email String
              deriving (Show)

type Nome = String
type Agenda = [(Nome, [Contacto])]

agen = [("Manela",[Email "manela@gmail.com",Tlm 934662832,Email "222@gmail.com"])] 


acrescEmail :: Nome -> String -> Agenda -> Agenda
acrescEmail no email agenda = if  null pessoa then (no,[Email email ]):agenda
                              else pessoa ++ naopessoa 
    where pessoa    = [(nome,(Email email):l) | (nome,l) <- agenda,nome == no]
          naopessoa = [(nome,(Email email):l) | (nome,l) <- agenda,nome /= no]


verEmails :: Nome -> Agenda -> Maybe [String]
verEmails no agenda = if null pessoa 
                      then Nothing 
                      else Just (fm ( snd (head pessoa )))
    where pessoa = [(nome,l) | (nome,l) <- agenda,nome == no]
          fm [] = []
          fm (Email x:t) = x : fm t
          fm ( _:t) = fm t   

consulta :: [Contacto] -> ([Integer],[String])
consulta [] = ([],[])
consulta (Email x:t) = (p,x:q)
    where (p,q) = consulta t

consulta (Tlm x:t) = (x:p,q)
    where (p,q) = consulta t

consulta (Casa x:t) = (x:p,q)
    where (p,q) = consulta t

consulta (Trab x:t) = (x:p,q)
    where (p,q) = consulta t


consultaIO :: Agenda -> IO ()
consultaIO agenda = do 
    con <- getLine
    let pessoa = [(nome,l) | (nome,l) <- agenda,nome == con]
    putStr $ show $ snd $ head pessoa


data RTree a = R a [RTree a] deriving (Show, Eq)

ar = R 1 [R 2 [], 
          R 3 [R 4 [R 5 [], 
                    R 6 []]],
          R 7 []]




paths :: RTree a -> [[a]]
paths (R x []) = [[x]]
paths (R x l ) = map (x:) $ concat $ map paths l

-- [  [[2]], [[3,4,5],[3,4,6]], [[7]]  ]

paths2 :: RTree a -> [[a]]
paths2 (R x []) = [[x]]
paths2 (R x l)  =     [ x : f    | f <- lf]
    where lf = concat [ paths2 f | f <- l ]



--[[1,2],[1,3,4,5],[1,3,4,6],[1,7]]

unpaths :: Eq a => [[a]] -> RTree a
unpaths l = head $ foldl (\la l2 -> ins l2 la) [] l


--[1,3,4,5] -> R 1 [R 3 [R 4 [R 5 [] ]]]
frt :: [a] -> RTree a
frt [x] = R x [] 
frt (h:t) = R h l 
    where l = [ frt t ]

ins :: Eq a => [a] -> [RTree a] -> [RTree a]
ins ln [] = [frt ln ]

ins (x:xs) as
    | null j = (frt (x:xs)) : as 
    | otherwise = R k (ins xs lk) : ldx 
    where (R k lk) = if null j then (R x []) else head j
          j   = [(R z l) | (R z l) <- as, x == z]
          ldx = [(R z l) | (R z l) <- as, x /= z]
-------------------------------------------------
--
unpaths2 :: Eq a => [[a]] -> RTree a
unpaths2 l = foldl (\la l2 -> ins2 l2 la) ari (tail l)
    where ari = frt $ head l 

ins2 :: Eq a => [a] -> RTree a -> RTree a
ins2 [x] _    = R x []
ins2 (x:x1:xs) (R z l) 
    | x == z && p = R z [ if r == x1 then ins2 (x1:xs) (R r z) else (R r z) | (R r z) <- l ]
    | otherwise   = R z (frt (x1:xs): l)
    where p = any (\(R r _) -> r == x1) l