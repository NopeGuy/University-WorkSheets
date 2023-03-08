module Ficha1 where
import Data.Char

-- Ficha 1 Programacao Funcional

--------------------------------------------------------------------------------------- exercicio 1 ----------------------------------------------------------------------------------------------

--definir o perimetro de um circulo, dando o raio
perimetro :: Double -> Double 
perimetro r = 2 * pi * r -- usar o pi requer que os argumentos sejam do tipo double

--calcula a distancia entre dois pontos no plano Cartesiano. Cada ponto e um par de valores do tipo Double.
dist :: (Double,Double) -> (Double, Double) -> Double
dist (x1,y1) (x2,y2) = sqrt ((abcissas^2)+(ordenadas^2))
                       where
                        abcissas = x1-x2 
                        ordenadas = y1-y2

--recebe uma lista e devolve um par com o primeiro e o ultimo elemento dessa lista
primUlt :: [a] -> (a,a)
primUlt l = (head l, last l)

--multiplo m n testa se o numero inteiro m e multiplo de n
multiplo :: Int -> Int -> Bool
multiplo m n = if mod m n == 0 then True 
                               else False

--recebe uma lista e, se o comprimento da lista for ımpar retira-lhe o primeiro elemento, caso contrario devolve a propria lista
truncaImpar :: [Int] -> [Int]
truncaImpar l = if mod (length l) 2 == 1 then tail l
                                         else l

--calcula o maior de dois numeros inteiros
max2 :: Int -> Int -> Int 
max2 i1 i2 = if i1 > i2 then i1
                        else i2

--calcula o maior de tres numeros inteiros, usando a funcao max2
max3 :: Int -> Int -> Int -> Int 
max3 i1 i2 i3 =  max2 (max2 i1 i2) i3

--------------------------------------------------------------------------------------- exercicio 2 ----------------------------------------------------------------------------------------------

-- alinea A
--recebe os (3) coeficientes de um polinomio de segundo grau e que calcula o numero de raızes (reais) desse polinomio
nRaizes :: Double -> Double -> Double -> Int 
nRaizes a b c |delta > 0  = 2
              |delta == 0 = 1
              |otherwise  = 0
              where
              delta = b^2 - 4 * a * c 

-- alinea B
--usando a funcao anterior, recebe os coeficientes do polinomio e calcula a lista das suas raızes reais
raizes :: Double -> Double -> Double -> [Double]
raizes a b c |nRaizes a b c  == 2 = [divid1/divisor, divid2/divisor]
             |nRaizes a b c  == 1 = [divid1/divisor]
             |otherwise           = []
    where
        delta   = b^2 - 4 * a * c 
        divid1  = -b + sqrt(delta)
        divid2  = -b - sqrt(delta)
        divisor = 2 * a

--------------------------------------------------------------------------------------- exercicio 3 ----------------------------------------------------------------------------------------------

type Hora = (Int,Int)

-- alinea A
-- testar se um par de inteiros representa uma hora do dia valida
testaHoras_validas :: Hora -> Bool
testaHoras_validas (a,b) = (a >= 0 && a < 24) && (b >= 0 && b <= 59) 

-- alinea B
-- testar se uma hora e ou nao depois de outra (comparacao) - referimos a primeira hora introduzida
testaHoras_comparacao :: Hora -> Hora -> Bool
testaHoras_comparacao (a,b) (c,d) | (a > c)  && testaHoras_validas (a,b) && testaHoras_validas (c,d)             = True
                                  | (a == c) && testaHoras_validas (a,b) && testaHoras_validas (c,d) && (b > d)  = True
                                  | otherwise = False

-- alinea C 
-- converter um valor em horas (par de inteiros) para minutos (inteiro)
converte_horas_minutos :: Hora -> Int 
converte_horas_minutos (a,b) = a * 60 + b

-- alinea D
-- converter um valor em minutos para horas
converte_minutos_horas :: Int -> Hora
converte_minutos_horas i = (a,b)
                         where
                            a = div i 60
                            b = mod i 60

-- alinea E 
-- calcular a diferenca entre duas horas (cujo resultado deve ser o numero de minutos)
calcula_diferenca_horas :: Hora -> Hora -> Int 
calcula_diferenca_horas (a,b) (c,d) = abs (converte_horas_minutos (a,b) - converte_horas_minutos (c,d))

-- alinea F 
-- adicionar um determinado numero de minutos a uma dada hora

acrescenta_minutos_a_horas :: Int -> Hora -> Hora
acrescenta_minutos_a_horas min (a,b) = converte_minutos_horas (converte_horas_minutos (a,b) + min)

--------------------------------------------------------------------------------------- exercicio 4 ----------------------------------------------------------------------------------------------
data Hora1 = H Int Int deriving (Show,Eq)

-- alinea A
-- testar se um par de inteiros representa uma hora do dia valida
testaHoras_validas1 :: Hora1 -> Bool
testaHoras_validas1 (H a b )= if (a >= 0 && a < 24) && (b >= 0 && b <= 59) then True else False

-- alinea B
-- testar se uma hora e ou nao depois de outra (comparacao) - referimos a primeira hora introduzida
testaHoras_comparacao1 :: Hora1 -> Hora1 -> Bool
testaHoras_comparacao1 (H a b) (H c d) | (a > c)  && testaHoras_validas1 (H a b) && testaHoras_validas1 (H c d)            = True
                                       | (a == c) && testaHoras_validas1 (H a b) && testaHoras_validas1 (H c d) && (b > d) = True
                                       | otherwise = False


-- alinea C 
-- converter um valor em horas (par de inteiros) para minutos (inteiro)
converte_horas_minutos1 :: Hora1 -> Int 
converte_horas_minutos1 (H a b ) = a * 60 + b

-- alinea D
-- converter um valor em minutos para horas
converte_minutos_horas1 :: Int -> Hora1
converte_minutos_horas1 i = H a b
                          where
                            a = div i 60
                            b = mod i 60

-- alinea E 
-- calcular a diferenca entre duas horas (cujo resultado deve ser o numero de minutos)
calcula_diferenca_horas1 :: Hora1 -> Hora1 -> Int 
calcula_diferenca_horas1 (H a b) (H c d) = abs (converte_horas_minutos1 (H a b) - converte_horas_minutos1 (H c d))

-- alinea F 
-- adicionar um determinado numero de minutos a uma dada hora
acrescenta_minutos_a_horas1 :: Int -> Hora1 -> Hora1
acrescenta_minutos_a_horas1 min (H a b) = converte_minutos_horas1 (converte_horas_minutos1 (H a b) + min)

--------------------------------------------------------------------------------------- exercicio 5 ----------------------------------------------------------------------------------------------

data Semaforo = Verde | Amarelo | Vermelho deriving (Show,Eq)

-- alinea A
-- calcula o proximo estado de um semaforo
next :: Semaforo -> Semaforo
next smf | smf == Verde = Amarelo
         | smf == Amarelo = Vermelho
         | otherwise = Verde

-- alinea B
-- determina se e obrigatorio parar num semaforo
stop :: Semaforo -> Bool
stop smf = smf == Vermelho  

-- alinea C
-- testa se o estado de dois semaforos num cruzamento e seguro   
safe :: Semaforo -> Semaforo -> Bool
safe smf1 smf2 = smf1 == Vermelho || smf2 == Vermelho 

--------------------------------------------------------------------------------------- exercicio 6 ----------------------------------------------------------------------------------------------

data Ponto = Cartesiano Double Double | Polar Double Double deriving (Show,Eq)

-- alinea A
-- calcula a distancia de um ponto ao eixo vertical
posx :: Ponto -> Double
posx (Cartesiano a b) = a
posx (Polar a b)      = a * acos b 

-- alinea B
-- calcula a distancia de um ponto ao eixo horizontal
posy :: Ponto -> Double
posy (Cartesiano a b) = b
posy (Polar a b)      = a * asin b 

-- alinea C
-- calcula a distancia de um ponto a origem
raio :: Ponto -> Double
raio (Cartesiano a b) = sqrt ((a^2) + (b^2))
raio (Polar a b)      = a

-- alinea D
-- calcula o angulo entre o vector que liga a origem a um ponto e o eixo horizontal
angulo :: Ponto -> Double
angulo (Cartesiano a b) = atan (ang)
                        where
                            ang = a/b
angulo (Polar a b)      = b

-- alinea E
-- calcula a distancia entre dois pontos
distan :: Ponto -> Ponto -> Double
distan (Cartesiano a b) (Cartesiano c d) = sqrt ((abcissas^2)+(ordenadas^2))
                                       where
                                        abcissas  = a-c 
                                        ordenadas = b-d

distan (Cartesiano a b) (Polar c d)      = sqrt ((abcissas^2)+(ordenadas^2))
                                       where
                                        abcissas  = a-(posx (Polar c d)) 
                                        ordenadas = b-(posy (Polar c d))

distan (Polar a b) (Polar c d)           = sqrt ((abcissas^2)+(ordenadas^2))
                                       where
                                        abcissas  = (posx (Polar a b)) - (posx (Polar c d))  
                                        ordenadas = (posx (Polar a b)) - (posy (Polar c d)) 

--------------------------------------------------------------------------------------- exercicio 7 ----------------------------------------------------------------------------------------------
data Figura = Circulo Ponto Double
            | Rectangulo Ponto Ponto
            | Triangulo Ponto Ponto Ponto
            deriving (Show,Eq)

-- alinea A
-- testa se uma figura e um polıgono
poligono :: Figura -> Bool
poligono (Circulo c r) = False 
poligono (Rectangulo p1 p2) = posx p1 /= posx p2 && 
                             posy p1 /= posy p2 
poligono (Triangulo p1 p2 p3) = (posy p2 - posy p1) / (posx p2 - posx p1) /= 
                                (posy p3 - posy p2) / (posx p3 - posx p2) 



-- alinea B
-- calcula a lista dos vertices de uma figura
vertices :: Figura -> [Ponto]
vertices (Circulo _ _)         = []
vertices (Rectangulo p1 p2)    = if poligono (Rectangulo p1 p2)    then p1:p2:(Cartesiano (posx p1)(posy p2)):[] else []
vertices (Triangulo p1 p2 p3)  = if poligono (Triangulo p1 p2 p3)  then p1:p2:p3:[]                              else []



-- alinea C
-- calcula a area das figuras
area :: Figura -> Double
area (Triangulo p1 p2 p3) =
                            let a = distan p1 p2
                                b = distan p2 p3
                                c = distan p3 p1
                                s = (a+b+c) / 2 -- semi-perimetro
                            in sqrt (s*(s-a)*(s-b)*(s-c)) -- formula de Heron

area (Circulo _ r)        = pi * (r ^ 2)
area (Rectangulo p1 p2)   = abs (posx p2 - posx p1) * abs (posy p2 - posy p1)

-- alinea D
-- calcula o perimetro de uma figura
perimetro1 :: Figura -> Double
perimetro1 (Circulo _ r)         = 2 * pi * r
perimetro1 (Rectangulo p1 p2)    = 2 * abs (posx p2 - posx p1) + 2 * abs (posy p2 - posy p1)
perimetro1 (Triangulo p1 p2 p3)  = distan p1 p2 + distan p2 p3 + distan p1 p3




--------------------------------------------------------------------------------------- exercicio 8 ----------------------------------------------------------------------------------------------
-- alinea A
-- testa se um Char e uma minuscula
isLower' :: Char -> Bool
isLower' ch = elem ch ['a'..'z']

-- alinea B
-- testa se um Char e um dıgito
isDigit' :: Char -> Bool
isDigit' d = elem d ['0'..'9']

-- alinea C
-- testa se um Char e uma letra

-- funcao auxiliar - testa se e maiuscula --
isUpper' :: Char -> Bool
isUpper' ch = elem ch ['A'..'Z']

isAlpha' :: Char -> Bool
isAlpha' ch = isLower' ch || isUpper' ch 

-- alinea D
-- converte uma letra para a respectiva maiuscula
toUpper' :: Char -> Char
toUpper' ch = if isLower ch then chr ((ord ch) - 32) --ver tabela ASCII
                            else ch

-- alinea E 
-- converte um numero entre 0 e 9 para o respectivo dıgito
intToDigit' :: Int -> Char
intToDigit' n = chr (n + 48)

-- alinea F
-- converte um dıgito para o respectivo inteiro
digitToInt' :: Char -> Int
digitToInt' ch = ord (ch) - 48
