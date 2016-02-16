SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `vop` ;
CREATE SCHEMA IF NOT EXISTS `vop` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `vop` ;

-- -----------------------------------------------------
-- Table `vop`.`Traject`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `vop`.`Traject` ;

CREATE TABLE IF NOT EXISTS `vop`.`Traject` (
  `id` INT NOT NULL,
  `letter` VARCHAR(2) NULL,
  `naam` VARCHAR(20) NULL,
  `lengte` INT NULL,
  `optimale_reistijd` INT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `vop`.`Provider`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `vop`.`Provider` ;

CREATE TABLE IF NOT EXISTS `vop`.`Provider` (
  `id` INT NOT NULL,
  `naam` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `vop`.`Meting`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `vop`.`Meting` ;

CREATE TABLE IF NOT EXISTS `vop`.`Meting` (
  `timestamp` DATETIME NULL,
  `reistijd` INT NULL,
  `traject_id` INT NOT NULL,
  `provider_id` INT NOT NULL,
  INDEX `fk_Meting_Traject_idx` (`traject_id` ASC),
  INDEX `fk_Meting_Provider1_idx` (`provider_id` ASC),
  CONSTRAINT `fk_Meting_Traject`
    FOREIGN KEY (`traject_id`)
    REFERENCES `vop`.`Traject` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Meting_Provider1`
    FOREIGN KEY (`provider_id`)
    REFERENCES `vop`.`Provider` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
