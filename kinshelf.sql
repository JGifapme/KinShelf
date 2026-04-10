CREATE DATABASE IF NOT EXISTS `kinshelf`;
USE `kinshelf`;

/*Tables with foreign key : */
DROP TABLE IF EXISTS `books_genres`;
DROP TABLE IF EXISTS `books_authors`;
DROP TABLE IF EXISTS `books`;
DROP TABLE IF EXISTS `loans`;

/*Tables without foreign key : */
DROP TABLE IF EXISTS `series`;
DROP TABLE IF EXISTS `publishers`;
DROP TABLE IF EXISTS `genres`;
DROP TABLE IF EXISTS `categories`;
DROP TABLE IF EXISTS `authors`;
DROP TABLE IF EXISTS `users`;

/*Tables without foreign key : */
CREATE TABLE IF NOT EXISTS `users` (
  `id_user` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  `fname` VARCHAR(75) NOT NULL,
  `lname` VARCHAR(75) NOT NULL,
  `dob` DATE NOT NULL,
  `email` VARCHAR(255) UNIQUE,
  `password` VARCHAR(255)
) ENGINE=InnoDB;
CREATE TABLE IF NOT EXISTS `authors` (
  `id_author` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  `fname` VARCHAR(75) NOT NULL,
  `lname` VARCHAR(75) NOT NULL,
  UNIQUE(fname, lname)
) ENGINE=InnoDB;
CREATE TABLE IF NOT EXISTS `genres` (
  `id_genre` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(150) NOT NULL UNIQUE
) ENGINE=InnoDB;
CREATE TABLE IF NOT EXISTS `categories` (
  `id_category` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(150) NOT NULL UNIQUE
) ENGINE=InnoDB;
CREATE TABLE IF NOT EXISTS `publishers` (
  `id_publisher` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(150) NOT NULL UNIQUE
) ENGINE=InnoDB;
CREATE TABLE IF NOT EXISTS `series` (
  `id_series` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(150) NOT NULL,
  `nb_tomes` TINYINT UNSIGNED,
  `status` ENUM('EN_COURS','FINIE','ARRET')
) ENGINE=InnoDB;

/*Tables with foreign key : */
CREATE TABLE IF NOT EXISTS `books` (
  `id_book` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  `title` VARCHAR(255) NOT NULL,
  `description` TEXT,
  `nb_pages` INT UNSIGNED,
  `cover_url` TEXT,
  `publication_date` DATE,
  `series_id` INT UNSIGNED,
  `publisher_id` INT UNSIGNED,
  `category_id` INT UNSIGNED NOT NULL,
   CONSTRAINT FOREIGN KEY (`series_id`) REFERENCES `series` (`id_series`),
   CONSTRAINT FOREIGN KEY (`publisher_id`) REFERENCES `publishers` (`id_publisher`),
   CONSTRAINT FOREIGN KEY (`category_id`) REFERENCES `categories` (`id_category`)
) ENGINE=InnoDB;
CREATE TABLE IF NOT EXISTS `books_authors` (
  `book_id` INT UNSIGNED NOT NULL,
  `author_id` INT UNSIGNED NOT NULL,
  `role` ENUM('SCENARISTE','DESSINATEUR','COLORISTE'),
  UNIQUE (`book_id`, `author_id`),
   CONSTRAINT FOREIGN KEY (`book_id`) REFERENCES `books` (`id_book`) ON DELETE CASCADE,
   CONSTRAINT FOREIGN KEY (`author_id`) REFERENCES `authors` (`id_author`) ON DELETE CASCADE,
   PRIMARY KEY (`book_id`, `author_id`)
) ENGINE=InnoDB;
CREATE TABLE IF NOT EXISTS `books_genres` (
  `book_id` INT UNSIGNED NOT NULL,
  `genre_id` INT UNSIGNED NOT NULL,
  UNIQUE (`book_id`, `genre_id`),
   CONSTRAINT FOREIGN KEY (`book_id`) REFERENCES `books` (`id_book`) ON DELETE CASCADE,
   CONSTRAINT FOREIGN KEY (`genre_id`) REFERENCES `genres` (`id_genre`) ON DELETE CASCADE,
   PRIMARY KEY (`book_id`, `genre_id`)
) ENGINE=InnoDB;
CREATE TABLE IF NOT EXISTS `books_users` (
  `is_own` BOOLEAN,
  `is_read` BOOLEAN,
  `rating` TINYINT UNSIGNED,
  `comment` TEXT,
  `book_id` INT UNSIGNED NOT NULL,
  `user_id` INT UNSIGNED NOT NULL,
  UNIQUE (`book_id`, `user_id`),
   CONSTRAINT FOREIGN KEY (`book_id`) REFERENCES `books` (`id_book`) ON DELETE CASCADE,
   CONSTRAINT FOREIGN KEY (`user_id`) REFERENCES `users` (`id_user`) ON DELETE CASCADE,
   PRIMARY KEY (`book_id`, `user_id`)
) ENGINE=InnoDB;
CREATE TABLE `loans` (
    `id_loan` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    `book_id` INT UNSIGNED NOT NULL,
    `owner_id` INT UNSIGNED NOT NULL,
    `borrower_id` INT UNSIGNED NOT NULL,
    `loan_date` DATE,
    `return_date` DATE,
    `status` ENUM('EN_COURS','RENDU'),
    FOREIGN KEY (`book_id`) REFERENCES `books` (`id_book`) ON DELETE CASCADE,
    FOREIGN KEY (`owner_id`) REFERENCES `users` (`id_user`) ON DELETE CASCADE,
    FOREIGN KEY (`borrower_id`) REFERENCES `users` (`id_user`) ON DELETE CASCADE
);
