SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `library` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `library` ;

-- -----------------------------------------------------
-- Table `potlatch`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `library`.`user` ;

CREATE TABLE IF NOT EXISTS `library`.`user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(100) NOT NULL,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `birthdate` DATE NULL,
  `gender` CHAR NULL,
  `roles` VARCHAR(255) NULL,  
  PRIMARY KEY (`id`),
  UNIQUE KEY `ix_username` (`username`))
ENGINE = InnoDB
COMMENT = 'Represents the user of the library application';


-- -----------------------------------------------------
-- Table `library`.`book`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `library`.`book` ;

CREATE TABLE IF NOT EXISTS `library`.`book` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NULL,
  `isbn` VARCHAR(20) NULL,
  `description` VARCHAR(300) NULL,
  `year` BIGINT NULL,
  `currency` VARCHAR(10) NULL,
  `price` DECIMAL(8,2) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
COMMENT = 'The book entity';



