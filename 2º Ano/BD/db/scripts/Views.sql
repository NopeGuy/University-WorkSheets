-- Views --

-- Query 1: Most rented games by customers
CREATE VIEW MostRentedGames AS
SELECT Nome, SUM(quantidade) AS amount 
FROM Jogo
INNER JOIN AluguerLinhas ON Jogo.ID = AluguerLinhas.Jogo_ID
GROUP BY Jogo.ID
ORDER BY amount DESC
LIMIT 3;

-- Query 2: Least rented games by customers
CREATE VIEW LeastRentedGames AS
SELECT Nome, SUM(quantidade) AS amount 
FROM Jogo
INNER JOIN AluguerLinhas ON Jogo.ID = AluguerLinhas.Jogo_ID
GROUP BY Jogo.ID
ORDER BY amount ASC
LIMIT 3;

-- Query 3: Most rented platforms by customers
CREATE VIEW MostRentedPlatforms AS
SELECT plataforma_de_lançamento, SUM(quantidade) AS amount 
FROM Jogo
INNER JOIN AluguerLinhas ON Jogo.ID = AluguerLinhas.Jogo_ID
GROUP BY jogo.plataforma_de_lançamento
ORDER BY amount DESC
LIMIT 3;

-- Query 4: Release dates with the highest volume of rentals
CREATE VIEW ReleaseDatesWithMostRentals AS
SELECT YEAR(data_lançamento) AS release_year, SUM(quantidade) AS amount
FROM jogo
INNER JOIN aluguerlinhas ON jogo.id = aluguerlinhas.Jogo_ID
GROUP BY YEAR(data_lançamento)
ORDER BY amount DESC
LIMIT 3;

-- Query 5: Top spending customers in the store
CREATE VIEW TopSpendingCustomers AS
SELECT nome, SUM(precototal) AS Gasto_Total
FROM cliente
INNER JOIN aluguercab ON cliente.nif = aluguercab.cliente_nif
GROUP BY nif
ORDER BY Gasto_Total DESC
LIMIT 3;

-- Query 6: Low spending customers in the store
CREATE VIEW LowSpendingCustomers AS
SELECT nome, SUM(precototal) AS Gasto_Total
FROM cliente
INNER JOIN aluguercab ON cliente.nif = aluguercab.cliente_nif
GROUP BY nif
ORDER BY Gasto_Total ASC
LIMIT 3;

-- Query 7: Customers who have rented the same game more than once
CREATE VIEW CustomersWithMultipleGameRentals AS
SELECT nome
FROM cliente
INNER JOIN aluguercab ON cliente.nif = aluguercab.cliente_nif
INNER JOIN aluguerlinhas ON aluguercab.id = aluguerlinhas.aluguercab_id
GROUP BY cliente.nif, aluguerlinhas.jogo_id
HAVING COUNT(*) > 1;

-- Query 8: Games that customers have enjoyed playing the most
CREATE VIEW MostLikedGames AS
SELECT jogo.nome, COUNT(*) AS amount
FROM jogo
INNER JOIN inquerito ON jogo.id = inquerito.jogo_que_mais_jogou
GROUP BY jogo.nome
ORDER BY amount DESC;

-- Query 9: Games that customers have enjoyed playing the least
CREATE VIEW LeastLikedGames AS
SELECT jogo.nome, COUNT(*) AS amount
FROM jogo
INNER JOIN inquerito ON jogo.id = inquerito.jogo_que_menos_jogou
GROUP BY jogo.nome
ORDER BY amount DESC;

-- Query 10: Games that customers want to play
CREATE VIEW DesiredGames AS
SELECT jogo.nome AS game_name, COUNT(*) AS occurrences
FROM jogo
INNER JOIN inquerito ON jogo.id = inquerito.jogo_que_quer_jogar
GROUP BY jogo.nome
ORDER BY occurrences DESC;

-- Query 11: Sellers who have made the most rentals in the store
CREATE VIEW TopRentingSellers AS
SELECT nome, COUNT(*) AS amount
FROM vendedor
INNER JOIN aluguercab ON vendedor.id = aluguercab.vendedor_id
GROUP BY vendedor.id
ORDER BY amount DESC
LIMIT 3;

-- Query 12: Sellers who have made the least rentals in the store
CREATE VIEW LowRentingSellers AS
SELECT nome, COUNT(*) AS amount
FROM vendedor
INNER JOIN aluguercab ON vendedor.id = aluguercab.vendedor_id
GROUP BY vendedor.id
ORDER BY amount ASC
LIMIT 3;

-- Query 13: Sellers who have issued the most surveys in the store
CREATE VIEW TopSurveyIssuingSellers AS
SELECT nome, COUNT(*) AS amount
FROM vendedor
INNER JOIN inquerito ON vendedor.id = inquerito.vendedor_id
GROUP BY vendedor.id
ORDER BY amount DESC
LIMIT 3;

-- Query 14: Sellers who have not issued any surveys
CREATE VIEW SellersWithoutSurveys AS
SELECT nome
FROM vendedor
WHERE vendedor.id NOT IN (SELECT vendedor_id FROM inquerito);


-- Query 15: Full content of the Jogo table
CREATE VIEW FullJogo AS
SELECT ID, Valor_De_Aluguer,Critics_Score,Pedidos_De_Aluguer, Plataforma_De_Lançamento, Nome, Data_Lançamento,Unidades_Em_Stock
FROM Jogo;

-- Query 16: Full content of the Inquerito table
CREATE VIEW FullInquerito AS
SELECT *
FROM Inquerito;

-- Query 17: Full content of the Cliente table
CREATE VIEW FullCliente AS
SELECT *
FROM Cliente;