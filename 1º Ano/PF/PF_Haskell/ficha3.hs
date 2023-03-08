module Ficha3 where

import Data.Char

-- Ficha 3 Programacao Funcional
--------------------------------------------------------------------------------------- exercicio 1 ----------------------------------------------------------------------------------------------
data Hora = H Int Int deriving Show
type Etapa = (Hora,Hora)
type Viagem = [Etapa]

-- alinea A
-- Testar se uma etapa esta bem construıda (i.e., o tempo de chegada e superior ao de partida e as horas sao validas)
testa_etapa :: Etapa -> Bool
testa_etapa ((H a b),(H c d)) = (testaHoras_validas1 (H a b)) && (testaHoras_validas1 (H c d)) && (testaHoras_comparacao1 (H a b) (H c d) == False)

-- funcoes auxiliares

testaHoras_validas1 :: Hora -> Bool -- testar se um par de inteiros representa uma hora do dia valida
testaHoras_validas1 (H a b )= if (a >= 0 && a < 24) && (b >= 0 && b <= 59) then True else False

testaHoras_comparacao1 :: Hora -> Hora -> Bool -- testar se uma hora e ou nao depois de outra (comparacao) - referimos a primeira hora introduzida
testaHoras_comparacao1 (H a b) (H c d) | (a > c)  && testaHoras_validas1 (H a b) && testaHoras_validas1 (H c d)            = True
                                       | (a == c) && testaHoras_validas1 (H a b) && testaHoras_validas1 (H c d) && (b > d) = True
                                       | otherwise = False


-- alinea B
-- Testa se uma viagem esta bem construıda (i.e., se para cada etapa, o tempo de chegada e superior ao de partida, e se a etapa seguinte comeca depois da etapa anterior ter terminado)
testa_viagem :: Viagem -> Bool
testa_viagem [] = True
testa_viagem [h] = True
testa_viagem (((H a b),(H c d)):((H e f),(H g h)):t) = if (testaHoras_comparacao1 (H a b) (H c d) == False) && 
                                                          (testaHoras_comparacao1 (H c d) (H e f) == False) 

                                                       then testa_viagem (((H e f),(H g h)):t) 
                                                       else False

-- alinea C
-- Calcular a hora de partida e de chegada de uma dada viagem
calculaHoras_partida_chegada :: Viagem -> (Hora,Hora)
calculaHoras_partida_chegada []  = (H 0 0, H 0 0)
calculaHoras_partida_chegada [h] = (fst h, snd h)
calculaHoras_partida_chegada v   = (fst (head v), snd (last v))

-- alinea D
-- Dada uma viagem valida, calcular o tempo total de viagem efectiva
calcula_tempo_viagem_efetiva :: Viagem -> Hora
calcula_tempo_viagem_efetiva [((H a b),(H c d))] = converte_minutos_horas1 (calcula_diferenca_horas1 (H a b) (H c d))
calcula_tempo_viagem_efetiva ((a,b):t)           = acrescenta_minutos_a_horas1 (calcula_diferenca_horas1 a b) (calcula_tempo_viagem_efetiva t)

-- funcoes auxiliares
converte_minutos_horas1 :: Int -> Hora
converte_minutos_horas1 i = H a b
                          where
                            a = div i 60
                            b = mod i 60

converte_horas_minutos1 :: Hora -> Int 
converte_horas_minutos1 (H a b ) = a * 60 + b

calcula_diferenca_horas1 :: Hora -> Hora -> Int 
calcula_diferenca_horas1 (H a b) (H c d) = abs (converte_horas_minutos1 (H a b) - converte_horas_minutos1 (H c d))

acrescenta_minutos_a_horas1 :: Int -> Hora -> Hora
acrescenta_minutos_a_horas1 min (H a b) = converte_minutos_horas1 (converte_horas_minutos1 (H a b) + min)

-- alinea E
-- Calcular o tempo total de espera
calcula_tempo_espera :: Viagem -> Hora
calcula_tempo_espera ((a,b):(c,d):t) = acrescenta_minutos_a_horas1 (calcula_diferenca_horas1 b c) (calcula_tempo_espera ((c,d):t))
calcula_tempo_espera _               = (H 0 0)

-- alinea F
-- Calcular o tempo total da viagem (a soma dos tempos de espera e de viagem efectiva)
calcula_tempo_total :: Viagem -> Hora 
calcula_tempo_total v = acrescenta_minutos_a_horas1 (converte_horas_minutos1(calcula_tempo_espera v)) (calcula_tempo_viagem_efetiva v)

--------------------------------------------------------------------------------------- exercicio 2 ----------------------------------------------------------------------------------------------
data Ponto = Cartesiano Double Double 
           | Polar Double Double 
           deriving (Show,Eq)

type Poligonal = [Ponto]

data Figura = Circulo Ponto Double
            | Rectangulo Ponto Ponto
            | Triangulo Ponto Ponto Ponto
            deriving (Show,Eq)


-- alinea A
-- calcular o comprimento de uma linha poligonal
calcula_comprimento :: Poligonal -> Double
calcula_comprimento (h:x:t) = (distan h x) + calcula_comprimento (x:t)

-- funcoes auxiliares
distan :: Ponto -> Ponto -> Double -- distancia entre 2 pontos
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


posx :: Ponto -> Double -- valor da abcissa
posx (Cartesiano a b) = a
posx (Polar a b)      = a * acos b 


posy :: Ponto -> Double -- valor da ordenada
posy (Cartesiano a b) = b
posy (Polar a b)      = a * asin b 

-- alinea B
-- testar se uma dada linha poligonal e ou nao fechada
testa_poligonal_fechada :: Poligonal -> Bool
testa_poligonal_fechada [p1,p2]       = False
testa_poligonal_fechada [p1,p2,p3]    = p1 == p3
testa_poligonal_fechada (p1:p2:p3:t)  = testa_poligonal_fechada (p1:p3:t)


-- alinea C
-- dada uma linha poligonal fechada e convexa, calcule uma lista de triangulos cuja soma das areas seja igual a area delimitada pela linha poligonal. O tipo Figura e identico ao definido na Ficha 1
triangula :: Poligonal -> [Figura]
triangula [p1,p2,p3]    = [(Triangulo p1 p2 p3)]
triangula (p1:p2:p3:t)  = (Triangulo p1 p2 p3) : triangula (p1:p3:t)

-- alinea D
-- calcular a area delimitada por uma linha poligonal fechada e convexa
calcula_area_poligono_convexo:: Poligonal -> Double
calcula_area_poligono_convexo p = areaAux (triangula p)

-- funcoes auxiliares
area :: Figura -> Double
area (Triangulo p1 p2 p3) =
                            let a = distan p1 p2
                                b = distan p2 p3
                                c = distan p3 p1
                                s = (a+b+c) / 2 -- semi-perimetro
                            in sqrt (s*(s-a)*(s-b)*(s-c)) -- formula de Heron

area (Circulo _ r)        = pi * (r ^ 2)
area (Rectangulo p1 p2)   = abs (posx p2 - posx p1) * abs (posy p2 - posy p1)

areaAux :: [Figura] -> Double
areaAux [] = 0.0
areaAux [x] = area x
areaAux (h:x:t) = area h + areaAux (x:t) 

-- alinea E
-- dada uma linha poligonal e um ponto, da como resultado uma linha poligonal identica a primeira mas tendo como ponto inicial o ponto dado
mover :: Poligonal -> Ponto -> Poligonal
mover l p = p : l

-- alinea F
-- dada um factor de escala e uma linha poligonal, de como resultado uma linha poligonal semelhante e com o mesmo ponto inicial mas 
-- em que o comprimento de cada segmento de recta e multiplicado pelo factor dado
zoom :: Double -> Poligonal -> Poligonal
zoom _ []                   = []
zoom x ((Cartesiano a b):t) = (Cartesiano (a*x) (b*x)) : (zoom x t)
zoom x ((Polar a b):t)      = (Polar (a*x) b)          : (zoom x t)

--------------------------------------------------------------------------------------- exercicio 3 ----------------------------------------------------------------------------------------------
data Contacto = Casa Integer
              | Trab Integer
              | Tlm Integer
              | Email String
     deriving Show
type Nome = String
type Agenda = [(Nome, [Contacto])]

-- alinea A
-- dado um nome, um email e uma agenda, acrescenta essa informacao a agenda
acrescEmail :: Nome -> String -> Agenda -> Agenda
acrescEmail nome email [] = [(nome, [(Email email)])]
acrescEmail nome email ((name,(contacts)) : t) | nome == name = (name,([(Email email)] ++ contacts)) :t
                                               | otherwise    = (name,contacts) : acrescEmail nome email t

-- alinea B
-- dado um nome e uma agenda, retorna a lista dos emails associados a esse nome. Se esse nome nao existir na agenda a funcao deve retornar Nothing
verEmails :: Nome -> Agenda -> Maybe [String]
verEmails _ []                     = Nothing
verEmails nome ((name,contacts):t) = if nome == name then Just (retorna_emails contacts)
                                                     else verEmails nome t


-- funcao auxiliar
retorna_emails :: [Contacto] -> [String]
retorna_emails []            = []
retorna_emails ((Email e):t) = e: retorna_emails t
retorna_emails (_:t)         = retorna_emails t


-- alinea C
-- dada uma lista de contactos, retorna a lista de todos os numeros de telefone dessa lista (tanto telefones fixos como telemoveis)
consTelefs :: [Contacto] -> [Integer]
consTelefs [(Casa a)]  = [a]
consTelefs [(Trab a)]  = [a]
consTelefs [(Tlm a)]   = [a]
consTelefs (h:t)       = consTelefs [h] ++ consTelefs t
consTelefs _           = []


-- alinea D
-- dado um nome e uma agenda, retorna o numero de telefone de casa (caso exista)
casa :: Nome -> Agenda -> Maybe Integer
casa nome []      = Nothing
casa nome [(a,b)] = if nome == a       then devolve_numero_telefone b        else Nothing
casa nome (h:t)   = if nome == fst (h) then casa nome [h]                    else casa nome t 

devolve_numero_telefone :: [Contacto] -> Maybe Integer
devolve_numero_telefone [(Casa a)] = Just a
devolve_numero_telefone [_]        = Nothing
devolve_numero_telefone (h:t)      = if devolve_numero_telefone [h] == Nothing then devolve_numero_telefone t 
                                                                               else devolve_numero_telefone [h]

--------------------------------------------------------------------------------------- exercicio 4 ----------------------------------------------------------------------------------------------

type Dia = Int
type Mes = Int
type Ano = Int
type Nome = String
data Data = D Dia Mes Ano
            deriving Show
type TabDN = [(Nome,Data)]

-- alinea A
-- indica a data de nascimento de uma dada pessoa, caso o seu nome exista na tabela
procura :: Nome -> TabDN -> Maybe Data
procura nome [] = Nothing
procura nome [(n,d)] = if nome == n     then Just d           else Nothing
procura nome (h:t)   = if nome == fst h then procura nome [h] else procura nome t

-- alinea B
-- calcula a idade de uma pessoa numa dada data
idade :: Data -> Nome -> TabDN -> Maybe Int
idade _ _ [] = Nothing
idade (D d m a) nome [(n,(D dia mes ano))] | a == ano && m == mes = Just (calculo_dias)
                                           | a == ano && d == dia = Just (calculo_meses)
                                           | a == ano             = Just (calculo_dias + (30 * calculo_meses))
                                           | m == mes && d == dia = Just (calculo_anos)
                                           | otherwise            = Just (calculo_dias + (30 * calculo_meses) + (30 * 12 * calculo_anos))
                                           where
                                           calculo_dias  = d - dia
                                           calculo_meses = m - mes
                                           calculo_anos  = a - ano  


-- alinea C
-- esta se uma data e anterior a outra data
anterior :: Data -> Data -> Bool
anterior (D d m a) (D dia mes ano) = a < ano || 
                                    (m < mes && a == ano )|| 
                                    (d < dia && m == mes && a == ano)


-- alinea D
-- ordena uma tabela de datas de nascimento, por ordem crescente das datas de nascimento
ordena :: TabDN -> TabDN
ordena [] = []
ordena ((n,d):ts) = insere_no_inicio (n,d) (ordena ts)

-- funcao auxiliar    
insere_no_inicio :: (Nome,Data) -> TabDN -> TabDN 
insere_no_inicio (n,d) [] = [(n,d)]
insere_no_inicio (n,d) ((nh,dh):t) | anterior dh d = (nh,dh):insere_no_inicio (n,d) t
                                   | otherwise     = (n,d)  :(nh,dh):t


-- alinea E
-- apresenta o nome e a idade das pessoas, numa dada data, por ordem crescente da idade das pessoas
porIdade:: Data -> TabDN -> [(Nome,Int)]
porIdade data_aux [(a,b)]   = [(a, idade_sem_maybe data_aux a [(a,b)])]
porIdade data_aux ((a,b):t) = porIdade data_aux [(a,b)] ++ porIdade data_aux t  


idade_sem_maybe :: Data -> Nome -> TabDN -> Int
idade_sem_maybe _ _ [] = 0
idade_sem_maybe (D d m a) nome [(n,(D dia mes ano))] | a == ano && m == mes =  (calculo_dias)
                                                     | a == ano && d == dia =  (calculo_meses)
                                                     | a == ano             =  (calculo_dias + (30 * calculo_meses))
                                                     | m == mes && d == dia =  (calculo_anos)
                                                     | otherwise            =  (calculo_dias + (30 * calculo_meses) + (30 * 12 * calculo_anos))
                                                     where
                                                     calculo_dias  = d - dia
                                                     calculo_meses = m - mes
                                                     calculo_anos  = a - ano  

--------------------------------------------------------------------------------------- exercicio 5 ----------------------------------------------------------------------------------------------

data Movimento = Credito Float | Debito Float
                 deriving Show

data Data = D Int Int Int
            deriving Show

data Extracto = Ext Float [(Data, String, Movimento)]
                deriving Show

-- alinea A
-- produz uma lista de todos os movimentos (creditos ou debitos) superiores a um determinado valor
extValor :: Extracto -> Float -> [Movimento] 
extValor (Ext vinicial [(d,desc,mov)]) valor   = if valor < retorna_valor_movimento (mov) then [mov] else [] 
extValor (Ext vinicial ((d,desc,mov):t)) valor = if valor < retorna_valor_movimento (mov) then [mov] ++ chamada_recursiva else chamada_recursiva
                                               where
                                                chamada_recursiva = extValor (Ext vinicial t) valor

-- funcao auxiliar
retorna_valor_movimento :: Movimento -> Float
retorna_valor_movimento (Credito v) = v
retorna_valor_movimento (Debito v)  = v

-- alinea B
-- retorna informacao relativa apenas aos movimentos cuja descricao esteja incluıda na lista fornecida no segundo parametro
filtro :: Extracto -> [String] -> [(Data,Movimento)]
filtro (Ext vinicial [(d,desc,mov)]) []       = []

filtro (Ext vinicial [(d,desc,mov)]) [h]      = if h == desc then [(d,mov)] else [] 

filtro (Ext vinicial [(d,desc,mov)]) (h:t)    = if h == desc then [(d,mov)] ++ chamada_recursiva1 
                                                             else chamada_recursiva1
                                           where 
                                            chamada_recursiva1 = filtro (Ext vinicial [(d,desc,mov)]) t

filtro (Ext vinicial ((d,desc,mov):xs)) (h:t) = if h == desc then [(d,mov)] ++ chamada_recursiva1 ++ chamada_recursiva2 
                                                             else chamada_recursiva1 ++ chamada_recursiva2
                                           where
                                            chamada_recursiva1  = filtro (Ext vinicial [(d,desc,mov)]) t
                                            chamada_recursiva2  = filtro (Ext vinicial xs) (h:t)
-- alinea C
-- retorna o total de creditos e de debitos de um extracto no primeiro e segundo elementos de um par, respectivamente 
creDeb :: Extracto -> (Float,Float)
creDeb (Ext vinicial [(d,desc,mov)]) = if verificaCredito mov then (retorna_valor_movimento mov, 0)
                                                              else (0, retorna_valor_movimento mov)

creDeb (Ext vinicial ((d,desc,mov):t)) = soma_pares (calcula_valor_primeiro_elemento) (calcula_valor_resto_lista)
                                       where
                                        calcula_valor_primeiro_elemento   = creDeb (Ext vinicial [(d,desc,mov)])
                                        calcula_valor_resto_lista         = creDeb (Ext vinicial t)

-- funcoes auxiliares
soma_pares :: (Float,Float) -> (Float,Float) -> (Float,Float)
soma_pares (a,b) (c,d) = (a+c,b+d)

verificaCredito :: Movimento -> Bool
verificaCredito (Credito _) = True
verificaCredito _           = False 

-- alinea D
-- devolve o saldo final que resulta da execucao de todos os movimentos no extracto sobre o saldo inicial
saldo :: Extracto -> Float
saldo (Ext vinicial [(d,desc,mov)])   = somar (creDeb (Ext vinicial [(d,desc,mov)]))   - vinicial
saldo (Ext vinicial ((d,desc,mov):t)) = somar (creDeb (Ext vinicial ((d,desc,mov):t))) - vinicial

-- funcao auxiliar
somar :: (Float,Float) -> Float
somar (a,b) = a+b