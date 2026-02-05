package dev.java10x.gamesearch.repositories;

import dev.java10x.gamesearch.entities.Category;
import dev.java10x.gamesearch.entities.Game;
import dev.java10x.gamesearch.entities.Platform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    List<Game> findGameByCategories(List<Category> categories);
    List<Game> findGameByPlatforms(List<Platform> platforms);
}
