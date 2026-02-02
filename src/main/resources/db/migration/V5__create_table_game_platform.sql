CREATE TABLE game_platform (
    game_id INTEGER,
    platform_id INTEGER,
    CONSTRAINT fk_game_platform_game FOREIGN KEY (game_id) REFERENCES game(id),
    CONSTRAINT fk_game_platform_platform FOREIGN KEY (platform_id) REFERENCES  platform(id)
)