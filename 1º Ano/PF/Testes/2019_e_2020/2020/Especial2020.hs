import Data.List
subst :: Eq a => (a,a) -> [a] -> [a]
subst p l = map (\x -> muda p x) l

muda ::Eq a => (a,a) -> a-> a
muda (x,z) y = if x == y 
               then z
               else y




posicoes:: [a] -> [Int] -> [a]
posicoes l l2 = [l !! (k-1) | k <- l2]

data Tree a b = Leaf b | Node a (Tree a b) (Tree a b)

t2 = Node 5 (Node 4 (Leaf 'C') (Leaf 'b'))
            (Node 1 (Node 6 (Leaf 'S') (Leaf '5')) (Leaf 'h'))
t3 :: Tree Float Int
t3 = Node 5.1 (Node 4.0 (Leaf 3) (Leaf 3))
              (Node 10.1 (Node 6.1 (Leaf 2) (Leaf 3)) (Leaf 3))

folhas :: Tree a b -> [b]
folhas (Leaf x) = [x]
folhas (Node x e d) = folhas e ++ folhas d


somas :: Tree Float Int -> (Float,Int)
somas (Leaf x)     = ( 0 , x )
somas (Node x e d) = ( x + p2 + p, q + q2)
    where (p,q)    = somas e
          (p2,q2)  = somas d


type Mat a = [[a]]

ma :: Mat Int
ma = [[1,2,3],[0,4,5],[0,0,6]]
rotateLeft :: Mat a -> Mat a
rotateLeft m = reverse $ trans m 

trans :: Mat a -> Mat a
trans m = [ map (!!k) m | k <- [0.. length m -1]]


type Filme = (Titulo,Realizador,[Actor],Genero,Ano)
type Titulo = String
type Realizador = String
type Actor = String
type Ano = Int
data Genero = Comedia | Drama | Ficcao | Accao | Animacao | Documentario deriving (Eq,Show)
type Filmes = [ Filme ]


-- 4


doRealizador :: Filmes -> Realizador -> [Titulo]
doRealizador l rea = [ t | (t,r,_,_,_) <- l , rea == r ]


doActor :: Filmes -> Actor -> [Titulo]
doActor l ator = [ t | (t,_,la,_,_) <- l, elem ator la]

aux :: Filme -> (Ano,Titulo)
aux (t,_,_,_,a) = (a,t)








data Avaliacao = NaoVi | Pontos Int deriving (Eq,Show)

type FilmesAval = [(Filme,[Avaliacao])]

lalala :: Filmes
lalala = [ ("eque"    , "gay", ["gay1"  , "gay2", "gay3"], Comedia, 2000),
           ("nada"    , "gay", ["lesb1", "lesb2", "gay3"], Comedia, 3000),
           ("queia"   , "les", ["gay1"  ,"lesb1", "les2"], Ficcao , 1000),
           ("manteiga", "gay", ["lesb1"   ,"gay2","gay3"], Comedia, 4000)
         ]

fa :: FilmesAval
fa = [ (("eque", "gay", ["gay1"  , "gay2", "gay3"], Comedia, 2000),[Pontos 3]),
       (("nada", "gay", ["lesb1", "lesb2", "gay3"], Comedia, 3000),[Pontos 4])
         ]


avalia a = do 
    putStr " dame um filme"
    x <- getLine
    putStr " dame ava"
    xa <- getLine
    
    let k = read xa :: Int
        filme = [ ((nome,m,l,n,b),(Pontos k) : av) | ((nome,m,l,n,b),av) <- a , nome == x]
        
    if null filme 
    then return a
    else return $ filme ++ [ ((n,m,l,b,v),av) | ((n,m,l,b,v),av) <- a , n/= x]




listaPorGeneros :: FilmesAval -> [(Genero,[(Titulo,Avaliacao)])]
listaPorGeneros l = nub [ t b | ((n,m,l,b,v),av) <- l]
    where t genero = (genero , [(n,mediaf av) | ((n,m,l,b,v),av) <- l , b == genero] )

mediaf :: [Avaliacao] -> Avaliacao
mediaf l = if null m
           then NaoVi
           else Pontos $ div (sum m) (length l)
    where m = media l

media :: [Avaliacao] -> [Int]
media [] = []
media ((Pontos x):t) = x : media t
media (NaoVi:t) = media t
