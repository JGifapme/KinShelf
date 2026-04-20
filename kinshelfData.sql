-- GENRES
INSERT IGNORE INTO genres (name) VALUES
('Fantasy'),
('Tranche de vie'),
('Aventure'),
('Historique'),
('Thriller');

-- CATEGORIES
INSERT IGNORE INTO categories (name) VALUES
('Roman'),
('Manga'),
('Bande dessinée');

-- PUBLISHERS
INSERT IGNORE INTO publishers (name) VALUES
('Gallimard'),
('Pocket'),
('Le Lombard'),
('Kana'),
('Rue de Sèvres'),
('Rageot'),
('Glénat'),
('Delcourt');

-- AUTHORS
INSERT IGNORE INTO authors (fname, lname) VALUES
('J.K.', 'Rowling'),
('J.R.R.', 'Tolkien'),
('Jean', 'Van Hamme'),
('Grzegorz', 'Rosinski'),
('Kamome', 'Shirahama'),
('Lewelyn', ''),
('Jérôme', 'Lereculey'),
('Makoto', 'Yukimura'),
('Pierre', 'Bottero'),
('Timothé', 'Le Boucher');

-- SERIES
INSERT IGNORE INTO series (name, nb_tomes, status) VALUES
('Harry Potter', 7, 'FINIE'),
('Le Seigneur des Anneaux', 3, 'FINIE'),
('Thorgal', NULL, 'EN_COURS'),
('L\'atelier des sorciers', NULL, 'EN_COURS'),
('Les 5 Terres', NULL, 'EN_COURS'),
('Vinland Saga', 29, 'FINIE'),
('Ellana', 3, 'FINIE');

-- INSERTION DES LIVRES :

INSERT INTO books (title, description, nb_pages, publication_date, series_id, publisher_id, category_id)
VALUES
-- Harry Potter 1
('Harry Potter à l\'école des sorciers', 'Un jeune sorcier découvre ses pouvoirs.', 320, '1997-06-26',
 (SELECT id_series FROM series WHERE name='Harry Potter'),
 (SELECT id_publisher FROM publishers WHERE name='Gallimard'),
 (SELECT id_category FROM categories WHERE name='Roman')),

-- LOTR 1
('La Communauté de l\'Anneau', 'Début de la quête de l\'anneau.', 423, '1954-07-29',
 (SELECT id_series FROM series WHERE name='Le Seigneur des Anneaux'),
 (SELECT id_publisher FROM publishers WHERE name='Pocket'),
 (SELECT id_category FROM categories WHERE name='Roman')),

-- Thorgal 11
('Thorgal - Tome 11 : Les Yeux de Tanatloc', NULL, 48, '1988-01-01',
 (SELECT id_series FROM series WHERE name='Thorgal'),
 (SELECT id_publisher FROM publishers WHERE name='Le Lombard'),
 (SELECT id_category FROM categories WHERE name='Bande dessinée')),

-- Atelier des sorciers 1
('L\'atelier des sorciers - Tome 1', NULL, 192, '2018-01-01',
 (SELECT id_series FROM series WHERE name='L\'atelier des sorciers'),
 (SELECT id_publisher FROM publishers WHERE name='Kana'),
 (SELECT id_category FROM categories WHERE name='Manga')),

-- Les 5 terres tome 6
('Les 5 Terres - Tome 6', NULL, 56, '2021-01-01',
 (SELECT id_series FROM series WHERE name='Les 5 Terres'),
 (SELECT id_publisher FROM publishers WHERE name='Delcourt'),
 (SELECT id_category FROM categories WHERE name='Bande dessinée')),

-- Vinland Saga 7
('Vinland Saga - Tome 7', NULL, 200, '2007-01-01',
 (SELECT id_series FROM series WHERE name='Vinland Saga'),
 (SELECT id_publisher FROM publishers WHERE name='Kana'),
 (SELECT id_category FROM categories WHERE name='Manga')),

-- Ellana 1
('Ellana - L\'Envol', NULL, 300, '2008-01-01',
 (SELECT id_series FROM series WHERE name='Ellana'),
 (SELECT id_publisher FROM publishers WHERE name='Rageot'),
 (SELECT id_category FROM categories WHERE name='Roman')),

-- Ces jours qui disparaissent
('Ces jours qui disparaissent', NULL, 192, '2017-01-01',
 NULL,
 (SELECT id_publisher FROM publishers WHERE name='Glénat'),
 (SELECT id_category FROM categories WHERE name='Bande dessinée'));
 
 -- RELATIONS LIVRES <-> AUTEURS :
 
 INSERT INTO books_authors (book_id, author_id, role)
VALUES
-- Harry Potter
((SELECT id_book FROM books WHERE title LIKE 'Harry Potter%'),
 (SELECT id_author FROM authors WHERE lname='Rowling'),
 'SCENARISTE'),

-- LOTR
((SELECT id_book FROM books WHERE title LIKE 'La Communauté%'),
 (SELECT id_author FROM authors WHERE lname='Tolkien'),
 'SCENARISTE'),

-- Thorgal
((SELECT id_book FROM books WHERE title LIKE 'Thorgal - Tome 11%'),
 (SELECT id_author FROM authors WHERE lname='Van Hamme'),
 'SCENARISTE'),
((SELECT id_book FROM books WHERE title LIKE 'Thorgal - Tome 11%'),
 (SELECT id_author FROM authors WHERE lname='Rosinski'),
 'DESSINATEUR'),

-- Atelier des sorciers
((SELECT id_book FROM books WHERE title LIKE 'L\'atelier des sorciers%'),
 (SELECT id_author FROM authors WHERE lname='Shirahama'),
 'SCENARISTE'),

-- Les 5 terres
((SELECT id_book FROM books WHERE title LIKE 'Les 5 Terres%'),
 (SELECT id_author FROM authors WHERE fname='Lewelyn'),
 'SCENARISTE'),
((SELECT id_book FROM books WHERE title LIKE 'Les 5 Terres%'),
 (SELECT id_author FROM authors WHERE lname='Lereculey'),
 'DESSINATEUR'),

-- Vinland Saga
((SELECT id_book FROM books WHERE title LIKE 'Vinland Saga%'),
 (SELECT id_author FROM authors WHERE lname='Yukimura'),
 'SCENARISTE'),

-- Ellana
((SELECT id_book FROM books WHERE title LIKE 'Ellana%'),
 (SELECT id_author FROM authors WHERE lname='Bottero'),
 'SCENARISTE'),

-- Ces jours qui disparaissent
((SELECT id_book FROM books WHERE title='Ces jours qui disparaissent'),
 (SELECT id_author FROM authors WHERE lname='Le Boucher'),
 'SCENARISTE');
 
 -- RELATIONS LIVRES <-> GENRES :
 
 INSERT INTO books_genres (book_id, genre_id)
VALUES
-- Harry Potter
((SELECT id_book FROM books WHERE title LIKE 'Harry Potter%'),
 (SELECT id_genre FROM genres WHERE name='Fantasy')),

-- LOTR
((SELECT id_book FROM books WHERE title LIKE 'La Communauté%'),
 (SELECT id_genre FROM genres WHERE name='Fantasy')),

-- Thorgal
((SELECT id_book FROM books WHERE title LIKE 'Thorgal - Tome 11%'),
 (SELECT id_genre FROM genres WHERE name='Aventure')),

-- Atelier des sorciers
((SELECT id_book FROM books WHERE title LIKE 'L\'atelier des sorciers%'),
 (SELECT id_genre FROM genres WHERE name='Fantasy')),

-- Les 5 terres
((SELECT id_book FROM books WHERE title LIKE 'Les 5 Terres%'),
 (SELECT id_genre FROM genres WHERE name='Fantasy')),

-- Vinland Saga
((SELECT id_book FROM books WHERE title LIKE 'Vinland Saga%'),
 (SELECT id_genre FROM genres WHERE name='Historique')),

-- Ellana
((SELECT id_book FROM books WHERE title LIKE 'Ellana%'),
 (SELECT id_genre FROM genres WHERE name='Fantasy')),

-- Ces jours qui disparaissent
((SELECT id_book FROM books WHERE title='Ces jours qui disparaissent'),
 (SELECT id_genre FROM genres WHERE name='Tranche de vie'));
 
 
 
 