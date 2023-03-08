module Ficha9 where


import Data.Char
import System.Random


geraChave :: IO Aposta
geraChave = do
            n1 <- randomRIO (1,50)
            n2 <- randomRIO (1,50)
            n3 <- randomRIO (1,50)
            n4 <- randomRIO (1,50)
            n5 <- randomRIO (1,50)
            s1 <- randomRIO (1,9)
            s2 <- randomRIO (1,9)

            let ap = (Ap [n1,n2,n3,n4,n5] (s1,s2))
            return ap
