-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema claims_management_db
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema claims_management_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `claims_management_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `claims_management_db` ;

-- -----------------------------------------------------
-- Table `claims_management_db`.`benefits`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `claims_management_db`.`benefits` (
  `id` BIGINT NOT NULL,
  `benefits_id` BIGINT NOT NULL,
  `benefits_list` VARCHAR(1024) NOT NULL,
  `max_claimable` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `benefits_id` (`benefits_id` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `claims_management_db`.`policy`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `claims_management_db`.`policy` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `policy_id` BIGINT NOT NULL,
  `subscription_date` DATE NULL DEFAULT NULL,
  `premium` DECIMAL(10,2) NULL DEFAULT NULL,
  `tenure` BIGINT NULL DEFAULT NULL,
  `group_id` BIGINT NULL DEFAULT NULL,
  `eligible_claim_amount` DECIMAL(10,2) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `policy_id` (`policy_id` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `claims_management_db`.`provider_policy`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `claims_management_db`.`provider_policy` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `hospital_id` BIGINT NOT NULL,
  `hospital_name` VARCHAR(1024) NOT NULL,
  `group_id` BIGINT NULL DEFAULT NULL,
  `location` VARCHAR(1024) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `hospital_id` (`hospital_id` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `claims_management_db`.`claims`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `claims_management_db`.`claims` (
  `claim_id` BIGINT NOT NULL AUTO_INCREMENT,
  `policy_id` BIGINT NULL DEFAULT NULL,
  `member_id` BIGINT NULL DEFAULT NULL,
  `claim_status` VARCHAR(255) NOT NULL,
  `claim_status_details` VARCHAR(1500) NULL DEFAULT NULL,
  `policy_details` VARCHAR(512) NULL DEFAULT NULL,
  `hospital_details` BIGINT NULL DEFAULT NULL,
  `benefits_availed` BIGINT NULL DEFAULT NULL,
  `billed_amount` DECIMAL(10,2) NOT NULL,
  `amount_claimed` DECIMAL(10,2) NOT NULL,
  `amount_settled` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`claim_id`),
  UNIQUE INDEX `claim_id` (`claim_id` ASC) VISIBLE,
  INDEX `policy_id` (`policy_id` ASC) VISIBLE,
  INDEX `hospital_details` (`hospital_details` ASC) VISIBLE,
  INDEX `benefits_availed` (`benefits_availed` ASC) VISIBLE,
  CONSTRAINT `claims_ibfk_1`
    FOREIGN KEY (`policy_id`)
    REFERENCES `claims_management_db`.`policy` (`policy_id`),
  CONSTRAINT `claims_ibfk_2`
    FOREIGN KEY (`hospital_details`)
    REFERENCES `claims_management_db`.`provider_policy` (`hospital_id`),
  CONSTRAINT `claims_ibfk_3`
    FOREIGN KEY (`benefits_availed`)
    REFERENCES `claims_management_db`.`benefits` (`benefits_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 65
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `claims_management_db`.`member_details`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `claims_management_db`.`member_details` (
  `member_id` BIGINT NOT NULL,
  `policy_id` BIGINT NOT NULL,
  `member_name` VARCHAR(500) NULL DEFAULT NULL,
  `member_age` BIGINT NULL DEFAULT NULL,
  `member_address` VARCHAR(500) NULL DEFAULT NULL,
  `member_phone` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`member_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `claims_management_db`.`member_claim`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `claims_management_db`.`member_claim` (
  `claim_id` BIGINT NOT NULL,
  `member_id` BIGINT NULL DEFAULT NULL,
  `policy_id` BIGINT NULL DEFAULT NULL,
  `claim_date` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`claim_id`),
  INDEX `member_id` (`member_id` ASC) VISIBLE,
  INDEX `policy_id` (`policy_id` ASC) VISIBLE,
  CONSTRAINT `member_claim_ibfk_1`
    FOREIGN KEY (`member_id`)
    REFERENCES `claims_management_db`.`member_details` (`member_id`),
  CONSTRAINT `member_claim_ibfk_2`
    FOREIGN KEY (`policy_id`)
    REFERENCES `claims_management_db`.`policy` (`policy_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `claims_management_db`.`member_policy`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `claims_management_db`.`member_policy` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `policy_id` BIGINT NULL DEFAULT NULL,
  `member_id` BIGINT NULL DEFAULT NULL,
  `member_details` VARCHAR(1500) NULL DEFAULT NULL,
  `benefits_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `policy_id` (`policy_id` ASC) VISIBLE,
  INDEX `member_policy_ibfk_2` (`benefits_id` ASC) VISIBLE,
  CONSTRAINT `member_policy_ibfk_1`
    FOREIGN KEY (`policy_id`)
    REFERENCES `claims_management_db`.`policy` (`policy_id`),
  CONSTRAINT `member_policy_ibfk_2`
    FOREIGN KEY (`benefits_id`)
    REFERENCES `claims_management_db`.`benefits` (`benefits_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `claims_management_db`.`member_premium_details`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `claims_management_db`.`member_premium_details` (
  `member_premium_id` BIGINT NOT NULL AUTO_INCREMENT,
  `primary_member_id` BIGINT NOT NULL,
  `policy_id` BIGINT NOT NULL,
  `due_date` DATE NOT NULL,
  `due_amount` DECIMAL(10,2) NOT NULL,
  `late_payment_charges` DECIMAL(10,2) NOT NULL,
  `payment_status` VARCHAR(200) NOT NULL,
  `payment_date` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`member_premium_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 100003
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `claims_management_db`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `claims_management_db`.`users` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(255) NULL DEFAULT NULL,
  `password` VARCHAR(255) NULL DEFAULT NULL,
  `created_at` DATETIME(6) NULL DEFAULT NULL,
  `policy_id` JSON NULL DEFAULT NULL,
  `role` VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_r43af9ap4edm43mmtq01oddj6` (`username` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `claims_management_db`.`users2`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `claims_management_db`.`users2` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(255) NULL DEFAULT NULL,
  `password` VARCHAR(255) NULL DEFAULT NULL,
  `created_at` DATETIME(6) NULL DEFAULT NULL,
  `policy_id` JSON NULL DEFAULT NULL,
  `role` VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username` (`username` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
