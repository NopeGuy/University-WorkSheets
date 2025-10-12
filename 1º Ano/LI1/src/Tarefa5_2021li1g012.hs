-- |
-- Module      : Tarefa5_2021li1g012
-- Description : Aplicação Gráfica
--
-- Módulo para a realização da Tarefa 5 do projeto de LI1 em 2021/22.
module Main where

--graficos e misc

import Data.List (elemIndex)
import Data.Maybe
import qualified Data.Text as Tx
import Graphics.Gloss
import Graphics.Gloss.Interface.Pure.Game
import LI12122
import Mapas
import Tarefa1_2021li1g012
import Tarefa2_2021li1g012
import Tarefa3_2021li1g012
import Tarefa4_2021li1g012

type Mundo = (Menu, Imagens)

type OpcaoEscolheMapa = Int

type Imagens = [(Foto, Picture)]

data Menu = MenuPrincipal OpcaoMenuPrincipal | EscolheMapa OpcaoEscolheMapa | ModoJogo (Jogo, Int) | MenuVitoria OpcaoVitoria

data OpcaoMenuPrincipal = Jogar deriving (Eq, Show, Read)

data OpcaoVitoria = JogarNovamente | Sair deriving (Eq, Show, Read)

data Foto = Caixas | Blocos | Vazios | Portas | Image1 | Image2 | Image3 | Image4 | Image5 | Image6 | Image7 | Image8 | PersonagemEste | PersonagemOeste deriving (Eq)

-- Misc--------------------------------------------
timeReact :: Float -> Mundo -> Mundo
timeReact n x = x

fr :: Int
fr = 50

------------------------------------------
--Nome do processo
mainDisplay :: Display
mainDisplay = InWindow "BlockDude" displayDimension (0, 0)

-- Tamanho da janela
displayDimension :: (Int, Int)
displayDimension = (1280, 720)

--recebe key presses e progrede no menu ou jogo em conformidade
events :: Event -> Mundo -> Mundo
events (EventKey (SpecialKey KeyEnter) Down _ _) (MenuPrincipal Jogar, imagens) = (EscolheMapa 0, imagens)
events (EventKey (SpecialKey KeyEnter) Down _ _) (EscolheMapa 0, imagens) = (ModoJogo (Jogo mapa0 (Jogador (10, 6) Oeste False), 0), imagens)
events (EventKey (SpecialKey KeyEnter) Down _ _) (EscolheMapa 1, imagens) = (ModoJogo (Jogo mapa1 (Jogador (10, 2) Oeste False), 0), imagens)
events (EventKey (SpecialKey KeyEnter) Down _ _) (EscolheMapa 2, imagens) = (ModoJogo (Jogo mapa2 (Jogador (10, 0) Oeste False), 0), imagens)
events (EventKey (SpecialKey KeyLeft) Down _ _) (EscolheMapa x, imagens) = if (x == 0) then (EscolheMapa 0, imagens) else (EscolheMapa (x -1), imagens)
events (EventKey (SpecialKey KeyRight) Down _ _) (EscolheMapa x, imagens) = if (x == 2) then (EscolheMapa 2, imagens) else (EscolheMapa (x + 1), imagens)
events (EventKey (SpecialKey KeyUp) Down _ _) (ModoJogo (j@(Jogo l (Jogador (a, b) c d)), x), imagens) = if cimadaPorta (moveJogador j Trepar) (doorCoord l) then (MenuVitoria JogarNovamente, imagens) else (ModoJogo (moveJogador j Trepar, x), imagens)
events (EventKey (SpecialKey KeyLeft) Down _ _) (ModoJogo (j@(Jogo l (Jogador (a, b) c d)), x), imagens) = if cimadaPorta (moveJogador j AndarEsquerda) (doorCoord l) then (MenuVitoria JogarNovamente, imagens) else (ModoJogo (moveJogador j AndarEsquerda, x), imagens)
events (EventKey (SpecialKey KeyRight) Down _ _) (ModoJogo (j@(Jogo l (Jogador (a, b) c d)), x), imagens) = if cimadaPorta (moveJogador j AndarDireita) (doorCoord l) then (MenuVitoria JogarNovamente, imagens) else (ModoJogo (moveJogador j AndarDireita, x), imagens)
events (EventKey (SpecialKey KeyDown) Down _ _) (ModoJogo (jogo, x), imagens) = (ModoJogo (moveJogador jogo InterageCaixa, x), imagens)
events (EventKey (SpecialKey KeyRight) Down _ _) (MenuVitoria _, imagens) = (MenuVitoria Sair, imagens)
events (EventKey (SpecialKey KeyLeft) Down _ _) (MenuVitoria _, imagens) = (MenuVitoria JogarNovamente, imagens)
events (EventKey (SpecialKey KeyEnter) Down _ _) (MenuVitoria Sair, imagens) = undefined
events (EventKey (SpecialKey KeyEnter) Down _ _) (MenuVitoria JogarNovamente, imagens) = (MenuPrincipal Jogar, imagens)
events _ m = m

drawWorld :: Mundo -> Picture
drawWorld (MenuPrincipal Jogar, imagens) = fromJust (lookup Image2 imagens)
drawWorld (EscolheMapa 0, imagens) = fromJust (lookup Image4 imagens)
drawWorld (EscolheMapa 1, imagens) = fromJust (lookup Image5 imagens)
drawWorld (EscolheMapa 2, imagens) = fromJust (lookup Image6 imagens)
drawWorld (MenuVitoria JogarNovamente, imagens) = fromJust (lookup Image7 imagens)
drawWorld (MenuVitoria Sair, imagens) = fromJust (lookup Image8 imagens)
drawWorld (ModoJogo (Jogo mapa (Jogador (a, b) f g), x), imagens) = Pictures ([fromJust (lookup Image1 imagens)] ++ (drawMap mapa (0, 0) imagens) ++ (drawPlayerMap mapa (0, 0) (Jogador (a, b) f g) x imagens))

mapDimension :: Mapa -> (Int, Int)
mapDimension [] = (0, 0)
mapDimension m = (length m, length (head m))

drawMap :: Mapa -> (Int, Int) -> Imagens -> [Picture]
drawMap [] _ _ = []
drawMap (l : ls) (x, y) imagens = drawLine l (x, y) imagens ++ drawMap ls (0, y + 1) imagens

drawLine :: [Peca] -> (Int, Int) -> Imagens -> [Picture]
drawLine [] _ _ = []
drawLine (p : ps) (x, y) imagens
  | p == Vazio = [Translate (i -270) (j + 268) $ Scale (0.3) (0.3) (fromJust (lookup Vazios imagens))] ++ drawLine ps (x + 1, y) imagens
  | p == Bloco = [Translate (i -270) (j + 268) $ Scale (0.3) (0.3) (fromJust (lookup Blocos imagens))] ++ drawLine ps (x + 1, y) imagens
  | p == Porta = [Translate (i -270) (j + 268) $ Scale (0.3) (0.3) (fromJust (lookup Portas imagens))] ++ drawLine ps (x + 1, y) imagens
  | p == Caixa = [Translate (i -270) (j + 268) $ Scale (0.3) (0.3) (fromJust (lookup Caixas imagens))] ++ drawLine ps (x + 1, y) imagens
  where
    i = fromIntegral (x * 60)
    j = fromIntegral (- y * 60)

drawPlayerLine :: [Peca] -> (Int, Int) -> Jogador -> Int -> Imagens -> [Picture]
drawPlayerLine [] _ _ _ _ = []
drawPlayerLine (l : ls) (x, y) (Jogador (a, b) c d) n imagens
  | x == a && y == b && n == 0 && c == Este = [Translate (i -270) (j + 268) $ Scale (0.3) (0.3) (fromJust (lookup PersonagemEste imagens))]
  | x == a && y == b && n == 0 && c == Oeste = [Translate (i -270) (j + 268) $ Scale (0.3) (0.3) (fromJust (lookup PersonagemOeste imagens))]
  | otherwise = drawPlayerLine ls (x + 1, y) (Jogador (a, b) c d) n imagens
  where
    i = fromIntegral (x * 60)
    j = fromIntegral (- y * 60)

drawPlayerMap :: Mapa -> (Int, Int) -> Jogador -> Int -> Imagens -> [Picture]
drawPlayerMap [] _ _ _ _ = []
drawPlayerMap (l : ls) (x, y) (Jogador (a, b) c d) n imagens = drawPlayerLine l (x, y) (Jogador (a, b) c d) n imagens ++ drawPlayerMap ls (x, y + 1) (Jogador (a, b) c d) n imagens

doorCoord :: Mapa -> Coordenadas
doorCoord l = getLista (countCoord (Porta) l)

getLista :: [Coordenadas] -> Coordenadas
getLista [(x, y)] = (x, y)
getLista (x : xs) = getLista [x]

cimadaPorta :: Jogo -> Coordenadas -> Bool
cimadaPorta (Jogo l (Jogador (x, y) f g)) (a, b)
  | x == a && y == b -1 = True
  | otherwise = False

main :: IO ()
main = do
  caixa <- loadBMP "Imagens/caixa.bmp"
  porta <- loadBMP "Imagens/porta.bmp"
  vazio <- loadBMP "Imagens/vazio.bmp"
  bloco <- loadBMP "Imagens/bloco.bmp"
  image1 <- loadBMP "Imagens/image1.bmp"
  image2 <- loadBMP "Imagens/image2.bmp"
  image3 <- loadBMP "Imagens/image3.bmp"
  image4 <- loadBMP "Imagens/image4.bmp"
  image5 <- loadBMP "Imagens/image5.bmp"
  image6 <- loadBMP "Imagens/image6.bmp"
  image7 <- loadBMP "Imagens/image7.bmp"
  image8 <- loadBMP "Imagens/image8.bmp"
  personagemEste <- loadBMP "Imagens/personagem.bmp"
  personagemOeste <- loadBMP "Imagens/personagemOeste.bmp"
  let imagens = [(Caixas, caixa), (Portas, porta), (Blocos, bloco), (Vazios, vazio), (Image1, image1), (Image2, image2), (Image3, image3), (Image4, image4), (Image5, image5), (Image6, image6), (Image7, image7), (Image8, image8), (PersonagemEste, personagemEste), (PersonagemOeste, personagemOeste)]
  let estadoInicial = (MenuPrincipal Jogar, imagens)
  play
    mainDisplay
    (greyN 0.25)
    fr
    estadoInicial
    drawWorld
    events
    timeReact
    
