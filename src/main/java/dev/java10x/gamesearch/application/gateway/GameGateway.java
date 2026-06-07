package dev.java10x.gamesearch.application.gateway;

import dev.java10x.gamesearch.domain.model.Game;

import java.util.List;
import java.util.Optional;

public interface GameGateway {

    Game save(Game game);

    List<Game> findAll();

    Optional<Game> findById(Long id);

    void deleteById(Long id);

    List<Game> findByCategoryId(Long categoryId);

    List<Game> findByPlatformId(Long platformId);
}
