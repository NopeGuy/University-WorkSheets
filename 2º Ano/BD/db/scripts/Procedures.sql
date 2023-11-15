-- Procedures --

-- Query 18: Get games based on the desired launch platform
CREATE PROCEDURE GetGamesByPlatform(IN platform VARCHAR(50))
SELECT *
FROM FullJogo
WHERE Plataforma_De_Lançamento = platform;

-- Query 19: Sort games by critics' score, price, and release date
CREATE PROCEDURE SortGamesByCriteria()
SELECT *
FROM FullJogo
ORDER BY critics_score DESC, valor_de_aluguer ASC, data_lançamento DESC;

-- Query 20: Sort games by the number of rentals
CREATE PROCEDURE SortGamesByRentalCount()
SELECT Nome, SUM(quantidade) AS amount 
FROM Jogo
INNER JOIN AluguerLinhas ON Jogo.ID = AluguerLinhas.Jogo_ID
GROUP BY Jogo.ID



UNION 

SELECT Nome, 0 AS amount 
FROM Jogo 
WHERE ID NOT IN (SELECT Jogo_ID FROM AluguerLinhas)
ORDER BY amount DESC;

-- Query 21: Insert a new game into Jogo table
CREATE PROCEDURE AddJogo(
  IN p_ID INT,
  IN p_Valor_De_Aluguer DOUBLE,
  IN p_Critics_Score VARCHAR(75),
  IN p_Pedidos_De_Aluguer INT,
  IN p_Plataforma_De_lançamento VARCHAR(75),
  IN p_Nome VARCHAR(75),
  IN p_Data_lançamento DATE,
  IN p_Sinopse VARCHAR(512),
  IN p_Unidades_Em_Stock VARCHAR(75)
)
INSERT INTO `GameBlockbuster`.`Jogo` (
  `ID`,
  `Valor_De_Aluguer`,
  `Critics_Score`,
  `Pedidos_De_Aluguer`,
  `Plataforma_De_lançamento`,
  `Nome`,
  `Data_lançamento`,
  `Sinopse`,
  `Unidades_Em_Stock`
) VALUES (
  p_ID,
  p_Valor_De_Aluguer,
  p_Critics_Score,
  p_Pedidos_De_Aluguer,
  p_Plataforma_De_lançamento,
  p_Nome,
  p_Data_lançamento,
  p_Sinopse,
  p_Unidades_Em_Stock
);

-- Query 22: Insert a new client into Cliente table
CREATE PROCEDURE AddCliente(
  IN p_Num_telem INT,
  IN p_Nome VARCHAR(75),
  IN p_Idade INT,
  IN p_NIF INT
)
INSERT INTO `GameBlockbuster`.`Cliente` (
  `Num_telem`,
  `Nome`,
  `Idade`,
  `NIF`
) VALUES (
  p_Num_telem,
  p_Nome,
  p_Idade,
  p_NIF
);

DELIMITER //

DELIMITER //

-- Create the event
CREATE EVENT backup_event
    ON SCHEDULE EVERY 5 MINUTE
    STARTS CURRENT_TIMESTAMP 
    COMMENT 'Event to perform backup'
    DO
    BEGIN
        -- Variables
        SET @backup_file := CONCAT('db/backup/backup_', DATE_FORMAT(NOW(), '%Y%m%d%H%i%s'), '.sql');
        SET @backup_cmd := CONCAT('mysqldump -u username -p password database_name > "', @backup_file, '"');

        -- Execute the backup command
        PREPARE stmt FROM @backup_cmd;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;

        -- Log the backup information
        INSERT INTO backup_logs (event_name, backup_date, backup_file)
        VALUES ('backup_event', NOW(), @backup_file);
    END //

DELIMITER ;



