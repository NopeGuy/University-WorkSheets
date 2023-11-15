SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

CREATE SCHEMA IF NOT EXISTS `GameBlockbuster` DEFAULT CHARACTER SET utf8;
USE `GameBlockbuster`;

CREATE TABLE IF NOT EXISTS `GameBlockbuster`.`Vendedor` (
  `ID` INT NOT NULL,
  `Nome` VARCHAR(75) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `GameBlockbuster`.`Jogo` (
  `ID` INT NOT NULL,
  `Valor_De_Aluguer` DOUBLE NOT NULL,
  `Critics_Score` VARCHAR(75) NULL,
  `Pedidos_De_Aluguer` INT NULL,
  `Plataforma_De_Lançamento` VARCHAR(75) NOT NULL,
  `Nome` VARCHAR(75) NOT NULL,
  `Data_Lançamento` DATE NOT NULL,
  `Sinopse` VARCHAR(512) NULL,
  `Unidades_Em_Stock` VARCHAR(75) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `GameBlockbuster`.`Cliente` (
  `Num_telem` INT NOT NULL,
  `Nome` VARCHAR(75) NOT NULL,
  `Idade` INT NOT NULL,
  `NIF` VARCHAR(75) NOT NULL,
  PRIMARY KEY (`NIF`)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `GameBlockbuster`.`Inquerito` (
  `ID` INT NOT NULL,
  `Jogo_que_quer_jogar` INT NOT NULL,
  `Jogo_que_mais_jogou` INT NOT NULL,
  `Jogo_que_menos_jogou` INT NOT NULL,
  `Vendedor_ID` INT NOT NULL,
  `Cliente_NIF` VARCHAR(75) NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_Inquerito_Vendedor1_idx` (`Vendedor_ID` ASC) VISIBLE,
  INDEX `fk_Inquerito_Cliente1_idx` (`Cliente_NIF` ASC) VISIBLE,
  CONSTRAINT `fk_Inquerito_Vendedor1`
    FOREIGN KEY (`Vendedor_ID`)
    REFERENCES `GameBlockbuster`.`Vendedor` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Inquerito_Cliente1`
    FOREIGN KEY (`Cliente_NIF`)
    REFERENCES `GameBlockbuster`.`Cliente` (`NIF`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `GameBlockbuster`.`AluguerCab` (
  `ID` INT NOT NULL,
  `PrecoTotal` DOUBLE NOT NULL,
  `Periodo_Aluguer` DOUBLE NOT NULL,
  `data` DATE NOT NULL,
  `Cliente_NIF` VARCHAR(75) NOT NULL,
  `Vendedor_ID` INT NOT NULL,
  `Metodo_Pagamento` VARCHAR(75) NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_Aluguer_Cliente1_nifx` (`Cliente_NIF` ASC) VISIBLE,
  INDEX `fk_AluguerCab_Vendedor1_idx` (`Vendedor_ID` ASC) VISIBLE,
  CONSTRAINT `fk_Aluguer_Cliente1`
    FOREIGN KEY (`Cliente_NIF`)
    REFERENCES `GameBlockbuster`.`Cliente` (`NIF`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_AluguerCab_Vendedor1`
    FOREIGN KEY (`Vendedor_ID`)
    REFERENCES `GameBlockbuster`.`Vendedor` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `GameBlockbuster`.`AluguerLinhas` (
  `Quantidade` INT NOT NULL,
  `PrecoUnit` DOUBLE NOT NULL,
  `PrecoTotal` DOUBLE NOT NULL,
  `AluguerCab_ID` INT NOT NULL,
  `Jogo_ID` INT NOT NULL,
  INDEX `fk_AluguerLinhas_AluguerCab1_idx` (`AluguerCab_ID` ASC) VISIBLE,
  INDEX `fk_AluguerLinhas_Jogo1_idx` (`Jogo_ID` ASC) VISIBLE,
  PRIMARY KEY (`AluguerCab_ID`, `Jogo_ID`),
  CONSTRAINT `fk_AluguerLinhas_AluguerCab1`
    FOREIGN KEY (`AluguerCab_ID`)
    REFERENCES `GameBlockbuster`.`AluguerCab` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_AluguerLinhas_Jogo1`
    FOREIGN KEY (`Jogo_ID`)
    REFERENCES `GameBlockbuster`.`Jogo` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE = InnoDB;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
