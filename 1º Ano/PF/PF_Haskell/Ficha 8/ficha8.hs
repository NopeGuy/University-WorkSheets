module Ficha8 where

data Frac = F Integer Integer

--1
--a)
normaliza :: Frac -> Frac
normaliza (F x y) = F (div x z) (div y z)
 where
 z = mdc x y
mdc :: Integer -> Integer -> Integer
mdc x 0 = x
mdc x y = mdc y (mod x y)
--b)
instance Eq Frac where
 (F x y) == (F x1 y1) = compara (normaliza (F x y)) (normaliza (F x1 y1))
  where
  compara (F x y) (F x1 y1) = x == x1 && y == y1 
--c)
instance Ord Frac where
 compare (F x y) (F x1 y1)
  | c1 < c2 = LT
  | c1 == c2 = EQ
  | c1 > c2 = GT
   where
   c1 = (fromIntegral x)/(fromIntegral y)
   c2 = (fromIntegral x1)/(fromIntegral y1)
--d)
instance Show Frac where
 show (F x y) = show x ++ "/" ++ show y
--e)
{- class (Eq a, Show a) => Num a where
(+), (*), (-) :: a -> a -> a
negate, abs, signum :: a -> a
fromInteger :: Integer -> a
-}
instance Num Frac where
 (+) = somaFrac
 (*) = multFrac
 negate (F x y) = F (-x) y
 abs(F x y) = F (abs x) (abs y)
 signum (F x y) = normaliza (F (signum x) (signum y))
 --ou signum (F x y) = (F ((signum x)*(signum y)) 1)
 fromInteger x = F x 1

somaFrac :: Frac -> Frac -> Frac
somaFrac (F x1 y1) (F x2 y2) = normaliza (F (x1*y2+x2*y1) (y1*y2))
multFrac :: Frac -> Frac -> Frac
multFrac (F x1 y1) (F x2 y2) = normaliza (F (x1*x2) (y1*y2))
--f)
selfrac :: Frac -> [Frac] -> [Frac]
selfrac f = filter (\x -> f*(fromInteger 2) < x)