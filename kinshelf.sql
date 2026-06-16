CREATE DATABASE IF NOT EXISTS `kinshelf`;
USE `kinshelf`;

/*Tables with foreign key : */
DROP TABLE IF EXISTS `books_genres`;
DROP TABLE IF EXISTS `books_authors`;
DROP TABLE IF EXISTS `books_users`;
DROP TABLE IF EXISTS `user_roles`;
DROP TABLE IF EXISTS `loans`;
DROP TABLE IF EXISTS `books`;

/*Tables without foreign key : */
DROP TABLE IF EXISTS `series`;
DROP TABLE IF EXISTS `publishers`;
DROP TABLE IF EXISTS `genres`;
DROP TABLE IF EXISTS `categories`;
DROP TABLE IF EXISTS `authors`;
DROP TABLE IF EXISTS `users`;

/*Tables without foreign key : */
/*--------------------------------------------------USERS*/
CREATE TABLE IF NOT EXISTS `users` (
  `id_user` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  `username` VARCHAR(150) NOT NULL UNIQUE,
  `slug` VARCHAR(150) NOT NULL UNIQUE,
  `dob` DATE NOT NULL,
  `email` VARCHAR(255) UNIQUE,
  `password` VARCHAR(255),
  `is_deleted` BOOLEAN NOT NULL DEFAULT FALSE
) ENGINE=InnoDB;
/*-------------------------------------------------AUTHORS*/
CREATE TABLE IF NOT EXISTS `authors` (
  `id_author` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(150) NOT NULL UNIQUE,
  `slug` VARCHAR(150) NOT NULL UNIQUE,
  `is_deleted` BOOLEAN NOT NULL DEFAULT FALSE
) ENGINE=InnoDB;
/*--------------------------------------------------GENRES*/
CREATE TABLE IF NOT EXISTS `genres` (
  `id_genre` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(150) NOT NULL UNIQUE,
  `slug` VARCHAR(150) NOT NULL UNIQUE,
  `is_deleted` BOOLEAN NOT NULL DEFAULT FALSE
) ENGINE=InnoDB;
/*------------------------------------------------CATEGORIES*/
CREATE TABLE IF NOT EXISTS `categories` (
  `id_category` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(150) NOT NULL UNIQUE,
  `slug` VARCHAR(150) NOT NULL UNIQUE,
  `is_deleted` BOOLEAN NOT NULL DEFAULT FALSE
) ENGINE=InnoDB;
/*------------------------------------------------PUBLISHERS*/
CREATE TABLE IF NOT EXISTS `publishers` (
  `id_publisher` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(150) NOT NULL UNIQUE,
  `slug` VARCHAR(150) NOT NULL UNIQUE,
  `is_deleted` BOOLEAN NOT NULL DEFAULT FALSE
) ENGINE=InnoDB;
/*---------------------------------------------------SERIES*/
CREATE TABLE IF NOT EXISTS `series` (
  `id_series` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(150) NOT NULL INDEX,
  `slug` VARCHAR(150) NOT NULL UNIQUE,
  `status` ENUM('EN_COURS','FINIE','ARRET'),
  `is_deleted` BOOLEAN NOT NULL DEFAULT FALSE
) ENGINE=InnoDB;

/*Tables with foreign key : */
/*--------------------------------------------------BOOKS*/
CREATE TABLE IF NOT EXISTS `books` (
  `id_book` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  `isbn` VARCHAR(25) UNIQUE,
  `title` VARCHAR(255) NOT NULL INDEX,
  `slug` VARCHAR(255) NOT NULL UNIQUE,
  `description` TEXT,
  `nb_pages` INT UNSIGNED,
  `cover_url` TEXT,
  `publication_date` DATE,
  `series_id` INT UNSIGNED,
  `publisher_id` INT UNSIGNED,
  `category_id` INT UNSIGNED NOT NULL,
  `date_ajout` DATE NOT NULL DEFAULT CURRENT_DATE,
  `is_deleted` BOOLEAN NOT NULL DEFAULT FALSE,
   CONSTRAINT FOREIGN KEY (`series_id`) REFERENCES `series` (`id_series`),
   CONSTRAINT FOREIGN KEY (`publisher_id`) REFERENCES `publishers` (`id_publisher`),
   CONSTRAINT FOREIGN KEY (`category_id`) REFERENCES `categories` (`id_category`)
) ENGINE=InnoDB;
/*------------------------------------------------BOOKS / AUTHORS*/
CREATE TABLE IF NOT EXISTS `books_authors` (
  `book_id` INT UNSIGNED NOT NULL,
  `author_id` INT UNSIGNED NOT NULL,
  `role` ENUM('AUTEUR','SCENARISTE','DESSINATEUR','ILLUSTRATEUR','COLORISTE','AUTEUR_COMPLET'),
  UNIQUE (`book_id`, `author_id`),
   CONSTRAINT FOREIGN KEY (`book_id`) REFERENCES `books` (`id_book`) ON DELETE CASCADE,
   CONSTRAINT FOREIGN KEY (`author_id`) REFERENCES `authors` (`id_author`) ON DELETE CASCADE,
   PRIMARY KEY (`book_id`, `author_id`)
) ENGINE=InnoDB;
/*------------------------------------------------BOOKS / GENRES*/
CREATE TABLE IF NOT EXISTS `books_genres` (
  `book_id` INT UNSIGNED NOT NULL,
  `genre_id` INT UNSIGNED NOT NULL,
  UNIQUE (`book_id`, `genre_id`),
   CONSTRAINT FOREIGN KEY (`book_id`) REFERENCES `books` (`id_book`) ON DELETE CASCADE,
   CONSTRAINT FOREIGN KEY (`genre_id`) REFERENCES `genres` (`id_genre`) ON DELETE CASCADE,
   PRIMARY KEY (`book_id`, `genre_id`)
) ENGINE=InnoDB;
/*------------------------------------------------USERS / ROLES*/
CREATE TABLE IF NOT EXISTS `user_roles` (
  `user_id` INT UNSIGNED NOT NULL,
  `role` VARCHAR(255) DEFAULT NULL,
   CONSTRAINT FOREIGN KEY (`user_id`) REFERENCES `users` (`id_user`) ON DELETE CASCADE
) ENGINE=InnoDB;
/*------------------------------------------------BOOKS / USERS*/
CREATE TABLE IF NOT EXISTS `books_users` (
  `is_own` BOOLEAN,
  `is_read` BOOLEAN,
  `is_interested` BOOLEAN,
  `rating` INT CHECK (rating BETWEEN 0 AND 5),
  `comment` TEXT,
  `date_comment` DATE,
  `book_id` INT UNSIGNED NOT NULL,
  `user_id` INT UNSIGNED NOT NULL,
  UNIQUE (`book_id`, `user_id`),
   CONSTRAINT FOREIGN KEY (`book_id`) REFERENCES `books` (`id_book`) ON DELETE CASCADE,
   CONSTRAINT FOREIGN KEY (`user_id`) REFERENCES `users` (`id_user`) ON DELETE CASCADE,
   PRIMARY KEY (`book_id`, `user_id`)
) ENGINE=InnoDB;
/*--------------------------------------------------LOANS*/
CREATE TABLE `loans` (
    `id_loan` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    `book_id` INT UNSIGNED NOT NULL,
    `owner_id` INT UNSIGNED NOT NULL,
    `borrower_id` INT UNSIGNED NOT NULL,
    `loan_date` DATE,
    `return_date` DATE,
    FOREIGN KEY (`book_id`) REFERENCES `books` (`id_book`) ON DELETE CASCADE,
    FOREIGN KEY (`owner_id`) REFERENCES `users` (`id_user`) ON DELETE CASCADE,
    FOREIGN KEY (`borrower_id`) REFERENCES `users` (`id_user`) ON DELETE CASCADE
);

