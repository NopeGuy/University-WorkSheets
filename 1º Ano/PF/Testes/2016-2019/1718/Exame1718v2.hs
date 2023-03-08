p :: [a] -> Int -> a
p [] x = error "nao pertence ao dominio"
p l  x = if length l-1 == x then last l
         else p (init l) x

p2 (h:t) x 
    | x == 0    = h 
    | otherwise = p2 t (x-1) 

-- 2 

data Movimento = Norte | Sul | Este | Oeste deriving Show

posicao :: (Int,Int) -> [Movimento] -> (Int,Int)
posicao (x,y) [] = (x,y)
posicao (x,y) (Norte:t) = posicao (x,y+1) t
posicao (x,y) (Este:t)  = posicao (x+1,y) t
posicao (x,y) (Oeste:t) = posicao (x-1,y) t
posicao (x,y) (Sul:t)   = posicao (x,y-1) t

-- 3

myany :: (a -> Bool) -> [a] -> Bool
myany f [] = False
myany f (h:t) = f h || myany f t

-- 4 

type Mat a = [[a]]

m = [[1,0,0],
     [0,4,0], 
     [0,0,7]]

triSup :: Eq a => Num a => Mat a -> Bool
triSup m = all (==0) [tira x y m | (x,y) <- t m]


t m = [ (x,y) | (x,y) <- todas n, k <- [(-n+1)..(-1)] , (x-y) == k ]
    where n = length (head m )
          todas n = [ (x,y) | x <- [0..n-1], y <- [0..n-1]]  

tira x y m = m !! y !! x

triSup2 :: Eq a => Num a => Mat a -> Bool
triSup2 m = null [ "batatas" | x <- [0..n-1], y <- [0..n-1], x-y < 0 , tira x y m /= 0] 
        where n = length $ head m 

-- 4 

mov :: String -> [Movimento]
mov []      = [] 
mov ('S':t) = Sul  :mov t 
mov ('N':t) = Norte:mov t 
mov ('O':t) = Oeste:mov t 
mov ('E':t) = Este :mov t 




movimenta :: IO (Int,Int)
movimenta = do 
    k <-  ques
    return (posicao (0,0) (mov k))

ques :: IO String
ques = do 
    putStrLn "\ndame um N,S,E,O"
    x <- getChar --dialogo
    if elem x "NSOE"
        then do  
            xs <- ques
            return (x:xs)

    else return []





pede :: IO [Int]
pede = do 
    putStrLn "dame um número"
    x  <- getChar
    --xs <- pede
    if x == '0' then return []
    else do
        xs <- pede
        return (read [x]:xs) 








-- 6 

data Imagem = Quadrado Int
            | Mover (Int,Int) Imagem
            | Juntar [Imagem]

ex = Mover (5,5) (Juntar [Mover (0,1) (Quadrado 5),Quadrado 4,Mover (4,3) (Quadrado 2)])

vazia :: Imagem -> Bool
vazia i = conta i == 0

conta :: Imagem -> Int
conta (Mover v i) = conta i
conta (Quadrado x ) = 1
conta (Juntar l) = sum $ map conta l 



maior :: Imagem -> Maybe Int
maior i 
    | conta i == 0 = Nothing
    | otherwise = Just $ maximum $ pv i 


pv :: Imagem -> [Int]
pv (Mover v i)  = pv i
pv (Quadrado x) = [x]
pv (Juntar l)   = concat $ map pv l
{-
instance Eq Imagem where
    (Mover v i) == (Mover v2 i2) = v2 == v && i == i2
    (Quadrado x) == (Quadrado x2) = x == x2
    (Juntar l) == (Juntar l2) = l == l2
-}

instance Eq Imagem where
   x == y = aux (moverQ x) (moverQ y)

aux :: Imagem -> Imagem -> Bool
aux (Mover v i) (Mover v2 i2) = v2 == v && i == i2
aux (Quadrado x) (Quadrado x2) = x == x2
aux (Juntar []) (Juntar []) = True
aux (Juntar l) (Juntar (h:t)) = h `elem` l && (Juntar (re2 l h)) == (Juntar t)


moverQ :: Imagem -> Imagem
moverQ (Quadrado a) = (Quadrado a)
moverQ (Mover (a,b) (Quadrado x)) = (Mover (a,b) (Quadrado x))
moverQ (Mover (a,b) (Juntar i)) = Juntar (map moverQ (map (Mover (a,b)) i))
moverQ (Mover (a,b) (Mover (x,y) i)) = Mover (a+x,b+y) (moverQ i)
moverQ (Juntar l) = Juntar (map moverQ l)

re2 :: (Eq a) => [a] -> a -> [a]
re2 [] _ = []
re2 (x:[]) a = if x==a then [] else [x]
re2 (x:xs) a = if x==a then xs else x:re2 xs a

