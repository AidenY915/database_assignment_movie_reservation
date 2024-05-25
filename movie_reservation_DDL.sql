-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema db1
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema db1
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `db1` DEFAULT CHARACTER SET utf8 ;
USE `db1` ;

-- -----------------------------------------------------
-- Table `db1`.`movie`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db1`.`movie` (
  `movie_no` INT NOT NULL,
  `moive_name` CHAR(45) NOT NULL,
  `running_time` INT NOT NULL,
  `age_rating` INT NOT NULL,
  `director_name` CHAR(20) NOT NULL,
  `Genre` CHAR(45) NOT NULL,
  `release_date` DATE NOT NULL,
  `movie_info` TEXT(500) NOT NULL,
  `rating_information` DECIMAL(3,1) NOT NULL,
  PRIMARY KEY (`movie_no`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db1`.`screening_hall`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db1`.`screening_hall` (
  `hall_no` INT NOT NULL,
  `standard_price` INT NOT NULL,
  `hall_name` CHAR(20) NOT NULL,
  PRIMARY KEY (`hall_no`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db1`.`screening_schedule`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db1`.`screening_schedule` (
  `schedule_no` INT NOT NULL,
  `hall_no` INT NOT NULL,
  `screening_date` DATE NOT NULL,
  `screening_day` CHAR(3) NOT NULL,
  `screening_session` INT NOT NULL,
  `screening_start_time` TIME NOT NULL,
  `movie_no` INT NOT NULL,
  PRIMARY KEY (`schedule_no`, `hall_no`, `movie_no`),
  INDEX `fk_screening_schedule_screening_hall1_idx` (`hall_no` ASC) VISIBLE,
  INDEX `fk_screening_schedule_movie1_idx` (`movie_no` ASC) VISIBLE,
  CONSTRAINT `fk_screening_schedule_screening_hall1`
    FOREIGN KEY (`hall_no`)
    REFERENCES `db1`.`screening_hall` (`hall_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_screening_schedule_movie1`
    FOREIGN KEY (`movie_no`)
    REFERENCES `db1`.`movie` (`movie_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db1`.`seat`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db1`.`seat` (
  `hall_no` INT NOT NULL,
  `seat_no` CHAR(3) NOT NULL,
  PRIMARY KEY (`hall_no`, `seat_no`),
  CONSTRAINT `fk_seat_screening_hall1`
    FOREIGN KEY (`hall_no`)
    REFERENCES `db1`.`screening_hall` (`hall_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db1`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db1`.`user` (
  `user_id` CHAR(30) NOT NULL,
  `user_name` CHAR(30) NOT NULL,
  `phone_no` CHAR(11) NOT NULL,
  `email` CHAR(100) NOT NULL,
  `password` CHAR(30) NOT NULL,
  `is_admin` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db1`.`booking`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db1`.`booking` (
  `booking_no` INT NOT NULL AUTO_INCREMENT,
  `payment_method` CHAR(20) NOT NULL,
  `payment_status` CHAR(20) NOT NULL DEFAULT 'pending',
  `payment_amount` INT NOT NULL,
  `payment_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `schedule_no` INT NOT NULL,
  `seat_no` CHAR(3) NOT NULL,
  `user_id` CHAR(30) NOT NULL,
  PRIMARY KEY (`booking_no`, `schedule_no`, `seat_no`, `user_id`),
  INDEX `fk_booking_info_screening_schedule1_idx` (`schedule_no` ASC) VISIBLE,
  INDEX `fk_booking_info_seat1_idx` (`seat_no` ASC) VISIBLE,
  INDEX `fk_booking_info_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_booking_info_screening_schedule1`
    FOREIGN KEY (`schedule_no`)
    REFERENCES `db1`.`screening_schedule` (`schedule_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_booking_info_seat1`
    FOREIGN KEY (`seat_no`)
    REFERENCES `db1`.`seat` (`seat_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_booking_info_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `db1`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db1`.`movie_ticket`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db1`.`movie_ticket` (
  `ticket_no` INT NOT NULL,
  `booking_no` INT NOT NULL,
  PRIMARY KEY (`ticket_no`, `booking_no`),
  INDEX `fk_movie_ticket_booking_info1_idx` (`booking_no` ASC) VISIBLE,
  CONSTRAINT `fk_movie_ticket_booking_info1`
    FOREIGN KEY (`booking_no`)
    REFERENCES `db1`.`booking` (`booking_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db1`.`actor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db1`.`actor` (
  `actor_no` INT NOT NULL,
  `actor_name` CHAR(20) NOT NULL,
  PRIMARY KEY (`actor_no`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db1`.`casting`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db1`.`casting` (
  `actor_no` INT NOT NULL,
  `movie_no` INT NOT NULL,
  PRIMARY KEY (`actor_no`, `movie_no`),
  INDEX `fk_appearance_actor_idx` (`actor_no` ASC) VISIBLE,
  INDEX `fk_appearance_movie1_idx` (`movie_no` ASC) VISIBLE,
  CONSTRAINT `fk_appearance_actor`
    FOREIGN KEY (`actor_no`)
    REFERENCES `db1`.`actor` (`actor_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_appearance_movie1`
    FOREIGN KEY (`movie_no`)
    REFERENCES `db1`.`movie` (`movie_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
