CREATE TABLE game_category (
    game_id INTEGER,
    category_id INTEGER,
    CONSTRAINT fk_game_category_game FOREIGN KEY(game_id) REFERENCES game(id),
    CONSTRAINT fk_game_category_category FOREIGN KEY (category_id) REFERENCES category(id)
)