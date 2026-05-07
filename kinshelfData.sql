-- GENRES
INSERT IGNORE INTO genres (name, slug) VALUES
('Fantasy', 'fantasy'),
('Tranche de vie', 'tranche-de-vie'),
('Aventure','aventure'),
('Historique','historique'),
('Thriller','thriller');

-- CATEGORIES
INSERT IGNORE INTO categories (name, slug) VALUES
('Roman', 'roman'),
('Manga', 'manga'),
('Bande dessinée','bande-dessinee');

-- PUBLISHERS
INSERT IGNORE INTO publishers (name, slug) VALUES
('Gallimard', 'gallimard'),
('Pocket', 'pocket'),
('Le Lombard', 'le-lombard'),
('Kana', 'kana'),
('Rue de Sèvres', 'rue-de-sevres'),
('Rageot', 'rageot'),
('Glénat', 'glenat'),
('Delcourt', 'delcourt');

-- AUTHORS
INSERT IGNORE INTO authors (fname, lname, slug) VALUES
('J.K.', 'Rowling', 'jk-rowling'),
('J.R.R.', 'Tolkien', 'jrr-tolkien'),
('Jean', 'Van Hamme', 'jean-van-hamme'),
('Grzegorz', 'Rosinski', 'grzegorz-rosinski'),
('Kamome', 'Shirahama', 'kamome-shirahama'),
('Lewelyn', '', 'lewelyn'),
('Jérôme', 'Lereculey', 'jerome-lereculey'),
('Makoto', 'Yukimura', 'makoto-yukimura'),
('Pierre', 'Bottero', 'pierre-bottero'),
('Timothé', 'Le Boucher', 'timothe-le-boucher');

-- SERIES
INSERT IGNORE INTO series (name, slug, status) VALUES
('Harry Potter', 'harry-potter', 'FINIE'),
('Le Seigneur des Anneaux', 'le-seigneur-des-anneaux', 'FINIE'),
('Thorgal', 'thorgal', 'EN_COURS'),
('L\'atelier des sorciers', 'l-atelier-des-sorciers', 'EN_COURS'),
('Les 5 Terres', 'les-5-terres', 'EN_COURS'),
('Vinland Saga', 'vinland-saga', 'FINIE'),
('Ellana', 'ellana', 'FINIE');

-- INSERTION DES LIVRES :

INSERT INTO books (title, slug, description, nb_pages, publication_date, series_id, publisher_id, category_id)
VALUES
-- Harry Potter 1
('Harry Potter à l\'école des sorciers', 'harry-potter-a-l-ecole-des-sorciers', 'Un jeune sorcier découvre ses pouvoirs.', 320, '1997-06-26',
 (SELECT id_series FROM series WHERE name='Harry Potter'),
 (SELECT id_publisher FROM publishers WHERE name='Gallimard'),
 (SELECT id_category FROM categories WHERE name='Roman')),

-- LOTR 1
('La Communauté de l\'Anneau', 'la-communaute-de-l-anneau', 'Début de la quête de l\'anneau.', 423, '1954-07-29',
 (SELECT id_series FROM series WHERE name='Le Seigneur des Anneaux'),
 (SELECT id_publisher FROM publishers WHERE name='Pocket'),
 (SELECT id_category FROM categories WHERE name='Roman')),

-- Thorgal 11
('Thorgal - Tome 11 : Les Yeux de Tanatloc', 'thorgal-tome-11-les-yeux-de-tanatloc', NULL, 48, '1988-01-01',
 (SELECT id_series FROM series WHERE name='Thorgal'),
 (SELECT id_publisher FROM publishers WHERE name='Le Lombard'),
 (SELECT id_category FROM categories WHERE name='Bande dessinée')),

-- Atelier des sorciers 1
('L\'atelier des sorciers - Tome 1', 'l-atelier-des-sorciers-tome-1', NULL, 192, '2018-01-01',
 (SELECT id_series FROM series WHERE name='L\'atelier des sorciers'),
 (SELECT id_publisher FROM publishers WHERE name='Kana'),
 (SELECT id_category FROM categories WHERE name='Manga')),

-- Les 5 terres tome 6
('Les 5 Terres - Tome 6', 'les-5-terres-tome-6', NULL, 56, '2021-01-01',
 (SELECT id_series FROM series WHERE name='Les 5 Terres'),
 (SELECT id_publisher FROM publishers WHERE name='Delcourt'),
 (SELECT id_category FROM categories WHERE name='Bande dessinée')),

-- Vinland Saga 7
('Vinland Saga - Tome 7', 'vinland-saga-tome-7', NULL, 200, '2007-01-01',
 (SELECT id_series FROM series WHERE name='Vinland Saga'),
 (SELECT id_publisher FROM publishers WHERE name='Kana'),
 (SELECT id_category FROM categories WHERE name='Manga')),

-- Ellana 1
('Ellana - L\'Envol', 'ellana-l-envol', NULL, 300, '2008-01-01',
 (SELECT id_series FROM series WHERE name='Ellana'),
 (SELECT id_publisher FROM publishers WHERE name='Rageot'),
 (SELECT id_category FROM categories WHERE name='Roman')),

-- Ces jours qui disparaissent
('Ces jours qui disparaissent', 'ces-jours-qui-disparaissent', NULL, 192, '2017-01-01',
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
 
 
 -- Rajout Utilisateurs : 
 
 INSERT INTO `users` (`fname`, `lname`, `dob`, `email`, `password`) 
VALUES 
('Jean', 'Dupont', '1985-03-15', 'jean.dupont@email.com', '$2a$10$eBcD1234567890abcdefghij'), 
('Marie', 'Lefebvre', '1992-07-22', 'marie.lefe@email.com', '$2a$10$zXyV0987654321fedcbazyxw');

INSERT INTO `books_users` 
(`is_own`, `is_read`, `is_interested`, `rating`, `comment`, `book_id`, `user_id`) 
VALUES 
(TRUE, TRUE, FALSE, 5, 'Un chef-d''oeuvre absolu, à lire une fois dans sa vie !', 1, 1),
(FALSE, FALSE, TRUE, NULL, NULL, 2, 2),
(FALSE, TRUE, FALSE, 4, 'Des graphismes incroyables, j''adore l''ambiance.', 2, 1);