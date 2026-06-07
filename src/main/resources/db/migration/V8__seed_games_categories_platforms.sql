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

INSERT INTO game_category (game_id, category_id)
SELECT g.id, c.id
FROM game g, category c
WHERE g.title = 'The Witcher 3: Wild Hunt'
  AND c.name IN ('RPG', 'Adventure');

INSERT INTO game_category (game_id, category_id)
SELECT g.id, c.id
FROM game g, category c
WHERE g.title = 'God of War'
  AND c.name IN ('Action', 'Adventure');

INSERT INTO game_category (game_id, category_id)
SELECT g.id, c.id
FROM game g, category c
WHERE g.title = 'The Legend of Zelda: Breath of the Wild'
  AND c.name IN ('Adventure', 'Platformer');

INSERT INTO game_category (game_id, category_id)
SELECT g.id, c.id
FROM game g, category c
WHERE g.title = 'Elden Ring'
  AND c.name IN ('Action', 'RPG');

INSERT INTO game_category (game_id, category_id)
SELECT g.id, c.id
FROM game g, category c
WHERE g.title = 'Forza Horizon 5'
  AND c.name IN ('Racing', 'Simulation');

INSERT INTO game_category (game_id, category_id)
SELECT g.id, c.id
FROM game g, category c
WHERE g.title = 'Stardew Valley'
  AND c.name IN ('Simulation', 'RPG');

INSERT INTO game_category (game_id, category_id)
SELECT g.id, c.id
FROM game g, category c
WHERE g.title = 'Resident Evil 4 Remake'
  AND c.name IN ('Horror', 'Action');

INSERT INTO game_category (game_id, category_id)
SELECT g.id, c.id
FROM game g, category c
WHERE g.title = 'Portal 2'
  AND c.name IN ('Puzzle', 'Adventure');

INSERT INTO game_category (game_id, category_id)
SELECT g.id, c.id
FROM game g, category c
WHERE g.title = 'Super Mario Odyssey'
  AND c.name IN ('Platformer', 'Adventure');

INSERT INTO game_category (game_id, category_id)
SELECT g.id, c.id
FROM game g, category c
WHERE g.title = 'Civilization VI'
  AND c.name IN ('Strategy', 'Simulation');

INSERT INTO game_platform (game_id, platform_id)
SELECT g.id, p.id
FROM game g, platform p
WHERE g.title = 'The Witcher 3: Wild Hunt'
  AND p.name IN ('PC', 'PlayStation 5', 'Xbox Series X', 'Steam Deck');

INSERT INTO game_platform (game_id, platform_id)
SELECT g.id, p.id
FROM game g, platform p
WHERE g.title = 'God of War'
  AND p.name IN ('PC', 'PlayStation 5', 'PlayStation 4');

INSERT INTO game_platform (game_id, platform_id)
SELECT g.id, p.id
FROM game g, platform p
WHERE g.title = 'The Legend of Zelda: Breath of the Wild'
  AND p.name IN ('Nintendo Switch');

INSERT INTO game_platform (game_id, platform_id)
SELECT g.id, p.id
FROM game g, platform p
WHERE g.title = 'Elden Ring'
  AND p.name IN ('PC', 'PlayStation 5', 'Xbox Series X');

INSERT INTO game_platform (game_id, platform_id)
SELECT g.id, p.id
FROM game g, platform p
WHERE g.title = 'Forza Horizon 5'
  AND p.name IN ('PC', 'Xbox Series X', 'Xbox One');

INSERT INTO game_platform (game_id, platform_id)
SELECT g.id, p.id
FROM game g, platform p
WHERE g.title = 'Stardew Valley'
  AND p.name IN ('PC', 'Nintendo Switch', 'Android', 'iOS');

INSERT INTO game_platform (game_id, platform_id)
SELECT g.id, p.id
FROM game g, platform p
WHERE g.title = 'Resident Evil 4 Remake'
  AND p.name IN ('PC', 'PlayStation 5', 'Xbox Series X');

INSERT INTO game_platform (game_id, platform_id)
SELECT g.id, p.id
FROM game g, platform p
WHERE g.title = 'Portal 2'
  AND p.name IN ('PC', 'Steam Deck', 'macOS');

INSERT INTO game_platform (game_id, platform_id)
SELECT g.id, p.id
FROM game g, platform p
WHERE g.title = 'Super Mario Odyssey'
  AND p.name IN ('Nintendo Switch');

INSERT INTO game_platform (game_id, platform_id)
SELECT g.id, p.id
FROM game g, platform p
WHERE g.title = 'Civilization VI'
  AND p.name IN ('PC', 'Nintendo Switch', 'macOS');