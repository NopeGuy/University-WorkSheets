import Data.List 


-- 1 
myisSorted :: (Ord a) => [a] -> Bool
myisSorted []  = True
myisSorted [a] = True
myisSorted (h:h2:t) = if h <= h2 then myisSorted (h2:t)
                    else False 
myinits :: [a] -> [[a]]
myinits [] = [[]]
myinits l = (myinits i)++[l]
    where i = init l

t = [Just 3, Just 5, Just 89, Nothing, Just 100] 

maximumMB :: (Ord a) => [Maybe a] -> Maybe a
maximumMB l = Just $ maximum (aux l) 
    where aux [] = []
          aux ((Just x):t) = x : aux t
          aux (Nothing:t)  =     aux t 










type RelP a = [(a , a)]
type RelL a = [( a , [a] )]
type RelF a = ([a] ,a -> [a] )

relp :: RelP Int
relp = [(1,3),(1,4),(2,1),(2,4),(2,5),(3,7),(4,7),(5,7),(6,5),(7,6)] 
rell :: RelL Int
rell = [(1,[3,4]),(2,[1,4,5]),(3,[7]),(4,[7]),(5,[7]),(6,[5]),(7,[6])] 

relf :: RelF Int
relf = ([1,2,3,4,5,6,7],f)
    where f 1 = [3,4]
          f 2 = [1,4,5] 
          f 3 = [7]
          f 4 = [7]
          f 5 = [7]
          f 6 = [5]
          f 7 = [6]

convLP :: RelL a -> RelP a
convLP l = concat (map junta l)
    where junta (x,xs) = map (\y->(x,y)) xs


convPL :: (Eq a) => RelP a -> RelL a
convPL [] = []
convPL l@((x,y):t) = (x, listn x l): convPL (drop c l)
    where lisfil x l = filter (\(z,y) -> z == x) l
          listn m l  = [ y | (x,y) <- lisfil m l]
          c = length $ listn x l 



criaRelPint :: Int -> IO (RelP Int)
criaRelPint 0 = return []
criaRelPint n = do putStr " insira um par de interios: "
                   par <- getLine-- pode ser preciao usar um read"(2,3)" :: (Int,Int)
                   l <- criaRelPint (n-1)
                   return (read par:l)

{-
convFP :: (Eq a) => RelF a -> RelP a
convFP (l,f) = [ (x,z) | x <- l,z <- (f x) ]
--convFP (l,g) = convLP map(\x -> (x, g x)) l
--FIXME
convPF ::  (Eq a) => RelP a -> RelF a
convPF l = (nub ((map fst l)++(map snd l)) , fun l)
    where fun :: (Eq a) => RelP a -> a -> [a]
          fun l x = map snd (filter (\(y,_)-> y == x) l)
-}
data LTree a = Tip a | Fork (LTree a) (LTree a) deriving (Eq)

ex :: LTree Int
ex = Fork (Tip 4) (Fork (Fork (Tip 2) (Tip 3) ) (Tip 6) ) 

listaLT :: LTree a -> [a]
listaLT (Tip x ) = [x]
listaLT (Fork e d) = (listaLT e) ++ (listaLT d)

-- b
al :: LTree a -> [(Int,a)]
al (Tip x) = [(0,x)]
al (Fork e d) = map (\(a,b) -> (a+1,b) ) (re++rd)
    where re = al e
          rd = al d 


im :: Show a => LTree a -> [String]
im l = map (\(a,b) -> replicate a '.'  ++ show b) (al l)

instance (Show a) => Show (LTree a) where
    show x = unlines $ im x








avalia :: FilmesAval -> IO FilmesAval 
avalia m@((x,xs):t) = do
    putStrLn "Insira o título do filme" 
    y <- getLine
    putStrLn "Insira avaliação"
    z <- getLine 
    if igual y m == True then return ((x,(xs++[z])):t)
    else avalia t 

igual :: Titulo -> FilmesAval -> Bool
igual x [] = False 
igual x (((tit,real,l,gen,ano),ys):t)
    | x == tit = True 
    | otherwise = igual x t 
