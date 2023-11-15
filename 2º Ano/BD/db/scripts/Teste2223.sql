-- a)

ALTER TABLE Investigador
	ADD DataNascimento DATE,
    ADD eMail VARCHAR(75);
    
-- b)

SELECT Projeto from Investigador
	JOIN Projeto where Investigador.Projeto = Projeto.Id
    AND 
    Projeto.orcamento > 100000
    AND Investigador.categoria = 'A';
    
-- c)

CREATE VIEW QUERY1 AS
	SELECT Nome FROM Investigador JOIN Projeto where Projeto.Id = Investigador.id ORDER BY Projeto.Orcamento DESC;
    
select * from query1;

-- d)
DELETE Tarefa  FROM InvestigadorTarefa 
JOIN Tarefa ON InvestigadorTarefa.Tarefa = Tarefa.Id 
WHERE Tarefa.Designacao = 'Limpeza de Microscópio';

DELETE FROM Tarefa 
	WHERE Tarefa.Designacao = 'Limpeza de Microscópio';

-- e)
DELIMITER //

CREATE FUNCTION GetTotalTime(investigador INT) RETURNS INT
BEGIN
    DECLARE totalTempo INT;
    
    SELECT SUM(DURACAO) INTO totalTempo
    FROM INVESTIGADORTAREFA
    WHERE INVESTIGADOR = investigador;
    
    RETURN totalTempo;
END //

DELETE FROM InvestigadorTarefa
WHERE Tarefa IN (SELECT ID FROM Tarefa WHERE Designacao = 'Limpeza de Microscópio');
DELETE FROM Tarefa
WHERE Designacao = 'Limpeza de Microscópio';

DELETE t
FROM InvestigadorTarefa t
INNER JOIN Tarefa c ON t.Tarefa = c.ID
WHERE c.Designacao = 'Limpeza de Microscópio';


DELIMITER ;
