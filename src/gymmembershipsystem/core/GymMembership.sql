CREATE DATABASE IF NOT EXISTS `GymMembershipSystem`;

USE `GymMembershipSystem`;

-- --------------------------------------------------------

-- create a user table with the following fields

-- - first_name

--  - last_name

--  - middle_name

--  - weight_in_kg

--  - height_in_ft

--  - birthdate

--  - gender

--  - role = 0, 1 (0 for staff and 1 for member)

--  - username

--  - password

--  - created_at

--  - updated_at

CREATE TABLE
    IF NOT EXISTS `User` (
        `id` INT NOT NULL AUTO_INCREMENT,
        `first_name` VARCHAR(255) NOT NULL,
        `last_name` VARCHAR(255) NOT NULL,
        `middle_name` VARCHAR(255) NULL,
        `weight_in_kg` FLOAT DEFAULT 0.0,
        `height_in_ft` FLOAT DEFAULT 0.0,
        `birthdate` DATE NULL,
        `gender` VARCHAR(255) NOT NULL,
        `role` INT NOT NULL,
        `username` VARCHAR(255) NOT NULL,
        `password` VARCHAR(255) NOT NULL,
        `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
        `updated_at` DATETIME on update CURRENT_TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
        PRIMARY KEY (`id`)
    ) ENGINE = InnoDB;

-- alter the middle_name to null

ALTER TABLE `User` MODIFY COLUMN `middle_name` VARCHAR(255) NULL;

ALTER TABLE
    `User` MODIFY COLUMN `weight_in_kg` FLOAT DEFAULT 0.0;

ALTER TABLE
    `User` MODIFY COLUMN `height_in_ft` FLOAT DEFAULT 0.0;

ALTER TABLE `User` MODIFY COLUMN `birthdate` VARCHAR(255) NULL;

CREATE TABLE
    IF NOT EXISTS `Program` (
        `id` int not null auto_increment,
        `name` varchar(255) not null,
        `description` varchar(255) not null,
        `created_at` datetime not null default current_timestamp,
        `updated_at` datetime on update current_timestamp not null default current_timestamp,
        primary key (`id`)
    );

ALTER TABLE `Program`
ADD
    COLUMN `amount` FLOAT NULL DEFAULT 0.0 AFTER `description`;

-- add column

CREATE TABLE
    IF NOT EXISTS `ProgramSession` (
        `id` int not null auto_increment,
        `name` varchar(255) not null,
        `program_id` int not null,
        `created_at` datetime not null default current_timestamp,
        `updated_at` datetime on update current_timestamp not null default current_timestamp,
        primary key (`id`),
        index `fk_program_session_program_idx` (`program_id` asc) visible,
        constraint `fk_program_session_program` foreign key (`program_id`) references `Program` (`id`) on delete cascade on update no action
    );

CREATE TABLE
    IF NOT EXISTS `Member` (
        `id` INT NOT NULL AUTO_INCREMENT,
        `user_id` INT NOT NULL,
        `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
        `updated_at` DATETIME on update CURRENT_TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
        PRIMARY KEY (`id`),
        INDEX `fk_member_user_idx` (`user_id` ASC) VISIBLE,
        CONSTRAINT `fk_member_user` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
    );

CREATE TABLE
    IF NOT EXISTS `MemberProgram` (
        `id` INT NOT NULL AUTO_INCREMENT,
        `member_id` INT NOT NULL,
        `start_date` DATE NOT NULL,
        `end_date` DATE NOT NULL,
        `payment_status` VARCHAR(255) NOT NULL,
        `payment_type` VARCHAR(255) NOT NULL,
        `program_id` INT NOT NULL,
        PRIMARY KEY (`id`),
        CONSTRAINT `fk_member_program_member` FOREIGN KEY (`member_id`) REFERENCES `Member` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
        CONSTRAINT `fk_member_program_program` FOREIGN KEY (`program_id`) REFERENCES `Program` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
    );

ALTER TABLE `Member` MODIFY COLUMN `start_date` DATE NULL;

ALTER TABLE
    `MemberProgram` MODIFY CONSTRAINT `memberprogram_ibfk_2` FOREIGN KEY (`program_id`) REFERENCES `program` (`id`) on DELETE CASCADE;

-- modify contstraint

ALTER TABLE `Member` MODIFY COLUMN `end_date` DATE NULL;

ALTER TABLE `Member` MODIFY COLUMN `program_id` INT NULL;

ALTER TABLE
    `Member` MODIFY COLUMN `payment_status` VARCHAR(255) NULL;

ALTER TABLE `Member` MODIFY COLUMN `payment_type` VARCHAR(255) NULL;

CREATE TABLE
    IF NOT EXISTS `Staff` (
        `id` INT NOT NULL AUTO_INCREMENT,
        `user_id` INT NOT NULL,
        `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
        `updated_at` DATETIME on update CURRENT_TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
        PRIMARY KEY (`id`),
        INDEX `fk_staff_user_idx` (`user_id` ASC) VISIBLE,
        CONSTRAINT `fk_staff_user` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
    );