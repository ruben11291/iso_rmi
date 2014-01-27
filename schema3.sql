SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `bdiso` DEFAULT CHARACTER SET latin1 ;
USE `bdiso` ;

-- -----------------------------------------------------
-- Table `bdiso`.`Jugador`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bdiso`.`Jugador` ;

CREATE  TABLE IF NOT EXISTS `bdiso`.`Jugador` (
  `email` VARCHAR(45) NOT NULL ,
  `passwd` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`email`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bdiso`.`Tablero9x9`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bdiso`.`Tablero9x9` ;

CREATE  TABLE IF NOT EXISTS `bdiso`.`Tablero9x9` (
  `idTablero` INT NOT NULL AUTO_INCREMENT ,
  PRIMARY KEY (`idTablero`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bdiso`.`Tablero3x3`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bdiso`.`Tablero3x3` ;

CREATE  TABLE IF NOT EXISTS `bdiso`.`Tablero3x3` (
  `idTablero` INT NOT NULL AUTO_INCREMENT ,
  `fila` INT NULL DEFAULT NULL ,
  `col` INT NULL DEFAULT NULL ,
  `idTableroGrande` INT NOT NULL ,
  PRIMARY KEY (`idTablero`) ,
  INDEX `fk_Tablero3x3_Tablero9x9` (`idTableroGrande` ASC) ,
  CONSTRAINT `fk_Tablero3x3_Tablero9x9`
    FOREIGN KEY (`idTableroGrande` )
    REFERENCES `bdiso`.`Tablero9x9` (`idTablero` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bdiso`.`Casilla`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bdiso`.`Casilla` ;

CREATE  TABLE IF NOT EXISTS `bdiso`.`Casilla` (
  `idCasilla` INT NOT NULL AUTO_INCREMENT ,
  `fila` INT NULL DEFAULT NULL ,
  `col` INT NULL DEFAULT NULL ,
  `idTablero` INT NOT NULL ,
  `valor` INT NULL DEFAULT 0 ,
  PRIMARY KEY (`idCasilla`) ,
  INDEX `fk_Casilla_Tablero3x31` (`idTablero` ASC) ,
  CONSTRAINT `fk_Casilla_Tablero3x31`
    FOREIGN KEY (`idTablero` )
    REFERENCES `bdiso`.`Tablero3x3` (`idTablero` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `bdiso`.`Movimiento`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bdiso`.`Movimiento` ;

CREATE  TABLE IF NOT EXISTS `bdiso`.`Movimiento` (
  `idMovimiento` INT NOT NULL AUTO_INCREMENT ,
  `idCasilla` INT NOT NULL ,
  `Jugador_email` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`idMovimiento`) ,
  INDEX `fk_Movimiento_Casilla1` (`idCasilla` ASC) ,
  INDEX `fk_Movimiento_Jugador1` (`Jugador_email` ASC) ,
  CONSTRAINT `fk_Movimiento_Casilla1`
    FOREIGN KEY (`idCasilla` )
    REFERENCES `bdiso`.`Casilla` (`idCasilla` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Movimiento_Jugador1`
    FOREIGN KEY (`Jugador_email` )
    REFERENCES `bdiso`.`Jugador` (`email` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- procedure delete_player
-- -----------------------------------------------------

USE `bdiso`;
DROP procedure IF EXISTS `bdiso`.`delete_player`;

DELIMITER $$
USE `bdiso`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_player`(jEmail VARCHAR(45))
BEGIN
    delete from Jugador where email=jEmail;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure insert_board
-- -----------------------------------------------------

USE `bdiso`;
DROP procedure IF EXISTS `bdiso`.`insert_board`;

DELIMITER $$
USE `bdiso`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_board`(id INT)
BEGIN
    insert into Tablero9x9(idTablero) values(id);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure insert_cell
-- -----------------------------------------------------

USE `bdiso`;
DROP procedure IF EXISTS `bdiso`.`insert_cell`;

DELIMITER $$
USE `bdiso`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_cell`(id INT, f INT, c INT, id3x3 INT)
BEGIN
    insert into Casilla(idCasilla, fila, col, idTablero)
    values(id, f, c, id3x3);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure insert_movement
-- -----------------------------------------------------

USE `bdiso`;
DROP procedure IF EXISTS `bdiso`.`insert_movement`;

DELIMITER $$
USE `bdiso`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_movement`(id INT, idCas INT, email VARCHAR(45))
BEGIN
    insert into Movimiento(idMovimiento, idCasilla, Jugador_email)
    values(id, idCas, email);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure insert_piece
-- -----------------------------------------------------

USE `bdiso`;
DROP procedure IF EXISTS `bdiso`.`insert_piece`;

DELIMITER $$
USE `bdiso`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_piece`(id INT, v INT)
BEGIN
    update Casilla set valor=v where idCasilla=id;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure insert_player
-- -----------------------------------------------------

USE `bdiso`;
DROP procedure IF EXISTS `bdiso`.`insert_player`;

DELIMITER $$
USE `bdiso`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_player`(jemail VARCHAR(45), passwd VARCHAR(45))
BEGIN
    insert into Jugador values(jemail,passwd);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure insert_sub_board
-- -----------------------------------------------------

USE `bdiso`;
DROP procedure IF EXISTS `bdiso`.`insert_sub_board`;

DELIMITER $$
USE `bdiso`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_sub_board`(id INT, f INT, c INT, id9x9 INT)
BEGIN
    insert into Tablero3x3(idTablero, fila, col, idTableroGrande)
    values (id, f, c, id9x9);
END$$

DELIMITER ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
