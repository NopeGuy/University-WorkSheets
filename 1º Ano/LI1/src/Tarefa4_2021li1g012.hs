{- |
Module      : Tarefa4_2021li1g012
Description : Movimentação do personagem
Copyright   : João Pedro Cardoso <a94595@alunos.uminho.pt>;
            : Luís Gomes Ferreira <a91672@alunos.uminho.pt>;

Módulo para a realização da Tarefa 4 do projeto de LI1 em 2021/22.
-}
module Tarefa4_2021li1g012 where

import LI12122
import Tarefa3_2021li1g012




moveJogador :: Jogo -> Movimento -> Jogo
moveJogador (Jogo l (Jogador (a,b) d f)) m | f == False && m == AndarDireita || f == False && m == AndarEsquerda = Jogo l (moveLeftRight (Jogador (a,b)d f) l m)
                                           | f == False && m == Trepar = Jogo l (climbLeftRight (Jogador (a,b)d f) l m) 
                                           | f == False && m == InterageCaixa = changeMap1 (Jogo l (Jogador (a,b) d f)) m  
                                           | f == True && m == InterageCaixa = changeMap1 (Jogo l (Jogador (a,b) d f)) m 
                                           | f == True && m == AndarEsquerda || f == True && m == AndarDireita = moveWithBox (Jogo l (Jogador (a,b) d f)) m
                                           | f == True && m == Trepar = climbWithBox (Jogo l (Jogador (a,b) d f)) m 
                                           | otherwise = (Jogo l (Jogador (a,b) d f)) 


correrMovimentos :: Jogo -> [Movimento] -> Jogo
correrMovimentos j@(Jogo l (Jogador (a,b) d f)) [] = j
correrMovimentos j@(Jogo l (Jogador (a,b) d f)) (m:ms) = correrMovimentos (moveJogador j m) ms 




--Auxs -----------------------


--retorna elem com coordenada mais a direita
-- >>> coordAdireita [(0,0),(1,0),(0,1),(2,1),(2,2),(1,1),(3,2)]  >> (3,2)
coordAdireita :: [Coordenadas] -> Coordenadas
coordAdireita [] = (0,0)
coordAdireita [(x,y)] = (x,y)
coordAdireita ((x,y):(a,b):z) | x >= a = coordAdireita ((x,y):z)
                              | otherwise = coordAdireita ((a,b):z)

 
--auxiliar para 'moveRight1'
lastAbscissa :: Coordenadas -> Int
lastAbscissa (x,y) = x                        



-- atribui coordenadas a cada peca nao vazia
coordTotal :: Coordenadas -> [Peca] -> [Coordenadas]
coordTotal (x,y) [] = []
coordTotal (x,y) (a:b) | a == Vazio = coordTotal (x+1,y) b
                       | otherwise = (x,y) : coordTotal (x+1,y) b 




-- o mesmo que a anterior mas com mapas
-- >> coordTotalMapa (0,0) [[Vazio,Bloco,Caixa,Porta],[Vazio,Bloco,Bloco,Bloco]]
-- >> [(1,0),(2,0),(3,0),(1,1),(2,1),(3,1)]
coordTotalMapa :: Coordenadas -> Mapa -> [Coordenadas]
coordTotalMapa (x,y) [] = []
coordTotalMapa (x,y) (a:b) = coordTotal (x,y) a ++ coordTotalMapa (0,y+1) b


------------------------------------------------------------------------
-- checka se existe obstáculos  à direita de uma Coordenada
checkObstacleRight :: Coordenadas -> [Coordenadas] -> Bool
checkObstacleRight (x,y) [] = False
checkObstacleRight (x,y) ((a,b):c) | y == b && x == a-1 = True
                                   | otherwise = checkObstacleRight (x,y) c

-- faz o jogador mover-se para a direita ou esquerda
move :: Jogador -> Movimento -> Jogador
move (Jogador (a,b) d f) m | m == AndarEsquerda = (Jogador (a-1,b) Oeste f)
                           | m == AndarDireita = (Jogador (a+1,b) Este f)

-- aux paraa moveRight1
moveRight :: Bool -> Jogador -> Movimento -> Jogador
moveRight t (Jogador (a,b) d f) m | t == True = (Jogador (a,b) Este f)
                                  | otherwise = move (Jogador (a,b) d f) m


-- faz o jogador mover-se para a direita, apenas quando possível e.g. sem obstáculos
moveRight1 :: Jogador -> Mapa -> Movimento -> Jogador
moveRight1 j@(Jogador (a,b) d f) l@((x:xs):y) m | a == lastAbscissa (coordAdireita (coordTotalMapa (0,0) l)) = (Jogador (a,b) Este f)
                                                | otherwise = moveRight (checkObstacleRight (a,b) (coordTotalMapa (0,0) l)) j m


-- checka se existe obstáculos  à esquerda de uma Coordenada
checkObstacleLeft :: Coordenadas -> [Coordenadas] -> Bool
checkObstacleLeft (x,y) [] = False
checkObstacleLeft (x,y) ((a,b):c) | y == b && x == a+1 = True
                                  | otherwise = checkObstacleLeft (x,y) c

-- aux de moveLeft1
moveLeft :: Bool -> Jogador -> Movimento -> Jogador
moveLeft t (Jogador (a,b) d f) m | t == True = (Jogador (a,b) Oeste f)
                                 | otherwise = move (Jogador (a,b) d f) m


-- faz o jogador mover quando possível. e.g. (sem obstáculos e não está numa abcissa igual a zero)
moveLeft1 :: Jogador -> Mapa -> Movimento -> Jogador
moveLeft1 j@(Jogador (a,b) d f) l@((x:xs):y) m | a == 0 = (Jogador (a,b) Oeste f)
                                               | otherwise = moveLeft (checkObstacleLeft(a,b) (coordTotalMapa (0,0) l)) j m


--O Jogador mexe-se para esquerda ou direita dependendo da escolha do jogador e do possível
moveLeftRight :: Jogador -> Mapa -> Movimento -> Jogador
moveLeftRight j l m | m == AndarDireita = groundPlayer (moveRight1 j l m) l
                    | m == AndarEsquerda = groundPlayer (moveLeft1 j l m) l

-- | Esta função garante que o Jogador tem sempre uma base, por outras palavras, nunca está a flutuar   
groundPlayer :: Jogador -> Mapa -> Jogador
groundPlayer j@(Jogador (a,b) d f) l@((x:xs):y) = gravity (highGround (removeAbove (a,b) (showColuna (a,b) (coordTotalMapa (0,0) l)))) (checkSupport (a,b) (coordTotalMapa (0,0) l)) j 






--verifica se determinada Coordenada pertence ou não a uma lista de Coordenadas, caso pertença retorna False
verifyExisteCoord :: Coordenadas -> [Coordenadas] -> Bool
verifyExisteCoord (x,y) [] = True
verifyExisteCoord (x,y) ((a,b):z) | x == a && y == b = False
                                  | otherwise = verifyExisteCoord (x,y) z 



--  se existe algum elemento por cima do que queremos trepar, caso exista a função retorna True                               
checkWall :: Coordenadas -> [Coordenadas] -> Bool
checkWall (x,y) [] = False
checkWall (x,y) ((a,b):c) | x == a && y == b+1 = True
                          | otherwise = checkWall (x,y) c



--  coordenadas do elemento à direita do Jogador
coordRightBase :: Jogador -> Coordenadas
coordRightBase (Jogador (a,b) d f) = (a+1,b)

-- coordenadas do elemento à esquerda do Jogador
coordLeftBase :: Jogador -> Coordenadas
coordLeftBase (Jogador (a,b) d f) = (a-1,b)





-- auxiliar para climbRight1
climbRight :: Bool -> Jogador -> Bool -> Movimento -> Jogador  
climbRight f (Jogador (a,b) m t) r s | f == True = (Jogador (a,b) m t)
                                     | f == False && r == False = (Jogador (a,b) m t)
                                     | otherwise = (Jogador (a+1,b-1) m t)
-- auxiliar para climbLeft1
climbLeft :: Bool -> Jogador -> Bool -> Movimento-> Jogador
climbLeft f (Jogador (a,b) m t) r s | f == True = (Jogador (a,b) m t)
                                    | f == False && r == False = (Jogador (a,b) m t)
                                    | otherwise = (Jogador (a-1,b-1) m t)




--Faz o jogador, fazer 'climb', quando possível e.g. (existe algo com nada em cima)
climbRight1 :: Jogador -> Mapa -> Movimento -> Jogador
climbRight1 j@(Jogador (a,b) d f) l m = climbRight (checkWall (coordRightBase j) (coordTotalMapa (0,0) l)) j (checkObstacleRight (a,b) (coordTotalMapa (0,0) l))  m


--  mesmo que a climbRight1, mas para esquerda
climbLeft1 :: Jogador -> Mapa -> Movimento -> Jogador
climbLeft1 j@(Jogador (a,b) d f) l m = climbLeft (checkWall (coordLeftBase j) (coordTotalMapa (0,0) l)) j (checkObstacleLeft (a,b) (coordTotalMapa (0,0) l)) m


--junta as duas anteriores para aplicar o movimento, quando permitido a todos os lados
climbLeftRight :: Jogador -> Mapa -> Movimento -> Jogador 
climbLeftRight j@(Jogador (a,b) d f) l@((x:xs):y) m | m == Trepar && d == Oeste = groundPlayer  (climbLeft1 j l m) l 
                                                          | m == Trepar && d == Este = groundPlayer (climbRight1 j l m) l
                                                          | otherwise = j





--retorna Coordenadas de todas as caixas numa lista de Pecas
getCoordBox :: Coordenadas -> [Peca] -> [Coordenadas]
getCoordBox (x,y) [] = []
getCoordBox (x,y) (z:zs) | z == Caixa = (x,y) : getCoordBox (x+1,y) zs
                         | otherwise = getCoordBox (x+1,y) zs



-- mesmo que a função anterior, contudo é capaz de analisar um Mapa                         
getCoordBoxMap :: Coordenadas -> Mapa -> [Coordenadas]
getCoordBoxMap (x,y) [] = []
getCoordBoxMap (x,y) (z:zs) = getCoordBox (x,y) z ++ getCoordBoxMap (0,y+1) zs





-- Tem caixa, imediatamente à direita do Jogador ? Se sim true
checkBoxRight :: Coordenadas -> [Coordenadas] -> Bool
checkBoxRight (x,y) [] = False
checkBoxRight (x,y) ((a,b):c) | x == a+1 && y == b = True
                              | otherwise = checkBoxRight (x,y) c

--memso que anterior mas para esquerda
checkBoxLeft :: Coordenadas -> [Coordenadas] -> Bool
checkBoxLeft (x,y) [] = False
checkBoxLeft (x,y) ((a,b):c) | x == a-1 && y == b = True
                             | otherwise = checkBoxLeft (x,y) c



-- existe alguma Peca imediatamente em cima de determinada Coordenada ? True
checkCeiling :: Coordenadas -> [Coordenadas] -> Bool
checkCeiling (x,y) [] = False
checkCeiling (x,y) ((a,b):c) | x == a && y == b+1 = True
                             | otherwise = checkCeiling (x,y) c



---Mudar Pecas ------------------------------------

-- permite saber coordenadas de cada Peca e substituir, mas apenas numa lista
auxChangePeca1 :: Coordenadas -> (Peca, Coordenadas) -> [Peca] -> [Peca]
auxChangePeca1 (c,d) (u,(x,y)) [] = []
auxChangePeca1 (c,d) (u,(x,y)) (a:b) | x == c && d == y = u:b
                                     | otherwise = a : auxChangePeca1 (c+1,d) (u,(x,y)) b

--faz o mesmo que a anterior, mas consegue analisar Mapas
auxChangePeca2 :: Coordenadas -> (Peca, Coordenadas) -> Mapa -> Mapa
auxChangePeca2 (c,d) (u,(x,y)) [] = []
auxChangePeca2 (c,d) (u,(x,y)) ((a:b):h) = auxChangePeca1 (c,d) (u,(x,y)) (a:b) : auxChangePeca2 (c,d+1) (u,(x,y)) h 


--trocar uma Peca (Bloco\Porta\Caixa) por Vazio no sitio adequado
changePeca :: (Peca, Coordenadas) -> Mapa -> Mapa
changePeca (u,(x,y)) [] = []
changePeca (u,(x,y)) ((a:b):h) = auxChangePeca2 (0,0) (u,(x,y)) ((a:b):h)



-- mesmo que anterior mas consegue substituir uma lista de Pecas
changeAllPeca :: [(Peca, Coordenadas)] -> Mapa -> Mapa
changeAllPeca [] ((a:b):h) = ((a:b):h)
changeAllPeca (x:xs) l = changeAllPeca xs (changePeca x l)



-- Lifts and dropps----------------------------------------------------------

--aux LiftBoxRight1
liftBoxRight :: Bool -> Jogador -> Movimento -> Bool -> Jogador
liftBoxRight r j@(Jogador (a,b) d h) m l | h == False && m == InterageCaixa && r == True && l == False = (Jogador (a,b) d True)   
                                         | otherwise = j

--aux LiftBoxLeft1
liftBoxLeft :: Bool -> Jogador -> Movimento -> Bool -> Jogador
liftBoxLeft r j@(Jogador (a,b) d h) m l | h == False && m == InterageCaixa && r == True && l == False = (Jogador (a,b) d True)   
                                        | otherwise = j

--Faz o jogador levantar uma caixa a direita quando possivel
liftBoxRight1 :: Jogador -> Mapa -> Movimento -> Jogador
liftBoxRight1 j@(Jogador (a,b) d f) l m = liftBoxRight (checkBoxRight (a,b) (getCoordBoxMap (0,0) l)) j m (checkCeiling (a,b) (coordTotalMapa (0,0) l))

--Faz o jogador levantar uma caixa a esquerda quando possivel
liftBoxLeft1 :: Jogador -> Mapa -> Movimento -> Jogador
liftBoxLeft1 j@(Jogador (a,b) d f) l m = liftBoxLeft (checkBoxLeft (a,b) (getCoordBoxMap (0,0) l)) j m (checkCeiling (a,b) (coordTotalMapa (0,0) l))

-- Junção de anteriores, fazendo uma ou outra, dependedno do input e do permitido
liftBoxLeftRight :: Jogador -> Mapa -> Movimento -> Jogador
liftBoxLeftRight j@(Jogador (a,b) d f)  l m | m == InterageCaixa && d == Oeste = liftBoxRight1 j l m
                                            | m == InterageCaixa && d == Este = liftBoxLeft1 j l m 
                                            | otherwise = j






--usa-se dropp para nao confundir com drop do prelude
-- aux de dropRight  e dropLeft
dropp :: Bool -> Jogador -> Movimento -> Jogador 
dropp r j@(Jogador (a,b) d h) m | h == True && r == False = (Jogador (a,b) d False) 
                                | otherwise = j

-- faz o jogador largar a caixa à sua direita, quando possivel
droppRight :: Jogador -> Mapa -> Movimento -> Jogador
droppRight j@(Jogador (a,b) d f) l m = dropp (checkObstacleRight (a,b) (coordTotalMapa (0,0) l)) j m 


-- faz o jogador largar a caixa à sua esquerda, quando possivel
droppLeft :: Jogador -> Mapa -> Movimento -> Jogador
droppLeft j@(Jogador (a,b) d f) l m = dropp (checkObstacleLeft (a,b) (coordTotalMapa (0,0) l)) j m 

-- largae uma Caixa para ambos os lados
droppLeftRight :: Jogador -> Mapa -> Movimento -> Jogador
droppLeftRight j@(Jogador (a,b) d f) l m | m == InterageCaixa && d == Este = droppRight j l m
                                         | m == InterageCaixa && d == Oeste = droppLeft j l m 
                                         | otherwise = j


-- | A função 'liftdroppBox' permite ao Jogador levantar e droppr uma Caixa quer para um lado quer para o outro
liftdroppBox :: Jogador -> Mapa -> Movimento -> Jogador
liftdroppBox j@(Jogador (a,b) d f) l m | f == True && m == InterageCaixa = droppLeftRight j l m
                                       | f == False && m == InterageCaixa = liftBoxLeftRight j l m 
                                       | otherwise = (Jogador (a,b) d f)



-- diz se o Jogador está a carregar uma Caixa ou não
isCarrying :: Jogador -> Bool
isCarrying (Jogador (a,b) c d) = d


--indica se o jogardor largou ou pegou numa caixa. True -> False = largou | False -> True = pegou
liftedOrdroppped :: Bool -> Bool -> String
liftedOrdroppped x y | x == True && y == False = "largou"
                    | x == False && y == True = "pegou"
                    | otherwise = "" 

-- coloca a Caixa sempre em cima do Jogador
placeAbove :: Coordenadas -> Coordenadas
placeAbove (x,y) = (x,y-1)                

--altera o mapa caso o jogador tenha pegado ou largado uma caixa
changeMap :: String -> Jogador -> Mapa -> Mapa 
changeMap s (Jogador (a,b) d f) l | s == "largou" && d == Oeste = changeAllPeca [(Vazio,(a,b-1)),(Caixa,(placeAbove(highGround (removeAbove (a,b) (showColuna (a-1,b) (coordTotalMapa (0,0) l))))))] l
                                  | s == "largou" && d == Este = changeAllPeca [(Vazio,(a,b-1)),(Caixa,(placeAbove(highGround (removeAbove (a,b) (showColuna (a+1,b) (coordTotalMapa (0,0) l))))))] l
                                  | s == "pegou" && d == Oeste = changeAllPeca [(Vazio,(a-1,b)),(Caixa,(a,b-1))] l
                                  | s == "pegou" && d == Este = changeAllPeca [(Vazio,(a+1,b)),(Caixa,(a,b-1))] l 
                                  | s == "" = l

-- semelhante à  anterior, mas recebe um Jogo e Movimento como argumentos
changeMap1 :: Jogo -> Movimento -> Jogo
changeMap1 (Jogo l (Jogador (a,b) d p)) m | m == InterageCaixa = (Jogo (changeMap (liftedOrdroppped p (isCarrying (liftdroppBox (Jogador (a,b) d p) l m))) (Jogador (a,b) d p) l) (liftdroppBox (Jogador (a,b) d p) l m))
                                          | otherwise = (Jogo l (Jogador (a,b) d p))


-- permite ao jogador mover com uma caixa, mas se existirem obstaculos à passagem não ocorre movimento
moveWithBox :: Jogo -> Movimento -> Jogo
moveWithBox (Jogo l (Jogador (a,b) d f)) m | f == True && m == AndarDireita && (checkObstacleRight (a,b) (coordTotalMapa (0,0) l)) == True = (Jogo (changeAllPeca [(Caixa,(a,b-1))] l) (Jogador (a,b) Este f))
                                           | f == True && m == AndarEsquerda && (checkObstacleLeft (a,b) (coordTotalMapa (0,0) l)) == True = (Jogo (changeAllPeca [(Caixa,(a,b-1))] l) (Jogador (a,b) Oeste f))
                                           | f == True && m == AndarEsquerda && (checkObstacleLeft (a,b-1) (coordTotalMapa (0,0) l)) == True = (Jogo (changeAllPeca [(Caixa,(a,b-1))] l) (Jogador (a,b) Oeste f))
                                           | f == True && m == AndarDireita && (checkObstacleRight (a,b-1) (coordTotalMapa (0,0) l)) == True = (Jogo (changeAllPeca [(Caixa,(a,b-1))] l) (Jogador (a,b) Este f))
                                           | f == True && m == AndarDireita = (Jogo (changeAllPeca [(Vazio,(a,b-1)),(Caixa,(groundPlayer2 (a+1,b) l))] l) (moveLeftRight (Jogador (a,b) d f) l m))
                                           | f == True && m == AndarEsquerda = (Jogo (changeAllPeca [(Vazio,(a,b-1)),(Caixa,(groundPlayer2 (a-1,b) l))] l) (moveLeftRight (Jogador (a,b) d f) l m))
                                           | otherwise = (Jogo l (moveLeftRight (Jogador (a,b) d f) l m))



-- aux do groudPlayer2. Se a caixa está em cima, está 2 coordenadas abaixo de onde o jogador está apoiado
place2Above :: Coordenadas -> Coordenadas
place2Above (x,y) = (x,y-2)

--Permite quedas sem que a caixa fica para trás
groundPlayer2 :: Coordenadas -> Mapa -> Coordenadas
groundPlayer2 (x,y) l = place2Above (highGround (removeAbove (x,y) (showColuna (x,y) (coordTotalMapa (0,0) l))))

--permite ao jogador trepar enquanto carrega uma caixa
climbWithBox :: Jogo -> Movimento -> Jogo
climbWithBox (Jogo l (Jogador (a,b) d f)) m | f == True && m == Trepar && d == Este && (checkObstacleRight (a,b) (coordTotalMapa (0,0) l)) == True && (checkWall (a+1,b) (coordTotalMapa (0,0) l)) == False && (checkWall (a+1,b-1) (coordTotalMapa (0,0) l)) == False = (Jogo (changeAllPeca [(Vazio,(a,b-1)),(Caixa,(a+1,b-2))] l) (climbLeftRight (Jogador (a,b) d f) l m))
                                            | f == True && m == Trepar && d == Oeste && (checkObstacleLeft (a,b) (coordTotalMapa (0,0) l)) == True && (checkWall (a-1,b) (coordTotalMapa (0,0) l)) == False && (checkWall (a-1,b-1) (coordTotalMapa (0,0) l)) == False = (Jogo (changeAllPeca [(Vazio,(a,b-1)),(Caixa,(a-1,b-2))] l) (climbLeftRight (Jogador (a,b) d f) l m))
                                            | a == 1 = (Jogo (changeAllPeca [(Caixa,(a,b-1))] l) (Jogador (a,b) d f)) 
                                            | otherwise = (Jogo (changeAllPeca [(Caixa,(a,b-1))] l) (Jogador (a,b) d f))
