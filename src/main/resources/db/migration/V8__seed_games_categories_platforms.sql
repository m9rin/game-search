INSERT INTO category (name) VALUES
('Action'),
('Adventure'),
('RPG'),
('Strategy'),
('Sports'),
('Racing'),
('Simulation'),
('Horror'),
('Puzzle'),
('Platformer');

INSERT INTO platform (name) VALUES
('PC'),
('PlayStation 5'),
('Xbox Series X'),
('Nintendo Switch'),
('PlayStation 4'),
('Xbox One'),
('Steam Deck'),
('Android'),
('iOS'),
('macOS');

INSERT INTO game (title, genre, release_date, rating, description, developer, publisher, created_at, updated_at) VALUES
('The Witcher 3: Wild Hunt', 'RPG', '2015-05-19', 9.8, 'Open-world fantasy RPG.', 'CD Projekt Red', 'CD Projekt', now(), now()),
('God of War', 'Action-Adventure', '2018-04-20', 9.6, 'Mythological action adventure.', 'Santa Monica Studio', 'Sony Interactive Entertainment', now(), now()),
('The Legend of Zelda: Breath of the Wild', 'Adventure', '2017-03-03', 9.7, 'Open-world adventure game.', 'Nintendo', 'Nintendo', now(), now()),
('Elden Ring', 'Action RPG', '2022-02-25', 9.7, 'Dark fantasy action RPG.', 'FromSoftware', 'Bandai Namco', now(), now()),
('Forza Horizon 5', 'Racing', '2021-11-09', 9.2, 'Open-world racing game.', 'Playground Games', 'Xbox Game Studios', now(), now()),
('Stardew Valley', 'Simulation', '2016-02-26', 9.1, 'Farming and life simulation.', 'ConcernedApe', 'ConcernedApe', now(), now()),
('Resident Evil 4 Remake', 'Horror', '2023-03-24', 9.3, 'Survival horror remake.', 'Capcom', 'Capcom', now(), now()),
('Portal 2', 'Puzzle', '2011-04-18', 9.5, 'First-person puzzle game.', 'Valve', 'Valve', now(), now()),
('Super Mario Odyssey', 'Platformer', '2017-10-27', 9.6, '3D platform adventure.', 'Nintendo', 'Nintendo', now(), now()),
('Civilization VI', 'Strategy', '2016-10-21', 8.8, 'Turn-based strategy game.', 'Firaxis Games', '2K', now(), now());

INSERT INTO game_category (game_id, category_id) VALUES
(1, 3), (1, 2),
(2, 1), (2, 2),
(3, 2), (3, 10),
(4, 1), (4, 3),
(5, 6), (5, 7),
(6, 7), (6, 3),
(7, 8), (7, 1),
(8, 9), (8, 2),
(9, 10), (9, 2),
(10, 4), (10, 7);

INSERT INTO game_platform (game_id, platform_id) VALUES
(1, 1), (1, 2), (1, 3), (1, 7),
(2, 2), (2, 5), (2, 1),
(3, 4),
(4, 1), (4, 2), (4, 3),
(5, 1), (5, 3), (5, 6),
(6, 1), (6, 4), (6, 8), (6, 9),
(7, 1), (7, 2), (7, 3),
(8, 1), (8, 7), (8, 10),
(9, 4),
(10, 1), (10, 4), (10, 10);