package dev.java10x.gamesearch.application.usecase.game;

import dev.java10x.gamesearch.application.gateway.CategoryGateway;
import dev.java10x.gamesearch.application.gateway.GameGateway;
import dev.java10x.gamesearch.application.gateway.PlatformGateway;
import dev.java10x.gamesearch.domain.exception.CategoryNotFoundException;
import dev.java10x.gamesearch.domain.exception.PlatformNotFoundException;
import dev.java10x.gamesearch.domain.model.Category;
import dev.java10x.gamesearch.domain.model.Game;
import dev.java10x.gamesearch.domain.model.Platform;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameUseCase {

    private final GameGateway gameGateway;
    private final CategoryGateway categoryGateway;
    private final PlatformGateway platformGateway;

    public Game create(CreateGameCommand command) {

        Game game = new Game(
                null,
                command.title(),
                command.genre(),
                command.releaseDate(),
                command.rating(),
                command.description(),
                command.developer(),
                command.publisher(),
                findCategories(command.categoryIds()),
                findPlatforms(command.platformIds())
        );

        return gameGateway.save(game);
    }

    public List<Game> findAll() {
        return gameGateway.findAll();
    }

    public Optional<Game> findById(Long id) {
        return gameGateway.findById(id);
    }

    public Optional<Game> update(Long id, UpdateGameCommand command) {
        return gameGateway.findById(id)
                .map(game -> gameGateway.save(new Game(
                        game.id(),
                        command.title(),
                        command.genre(),
                        command.releaseDate(),
                        command.rating(),
                        command.description(),
                        command.developer(),
                        command.publisher(),
                        findCategories(command.categoryIds()),
                        findPlatforms(command.platformIds())
                )));
    }

    public boolean delete(Long id) {
        if (gameGateway.findById(id).isEmpty()) {
            return false;
        }

        gameGateway.deleteById(id);
        return true;
    }

    public List<Game> findByCategory(Long categoryId) {
        return gameGateway.findByCategoryId(categoryId);
    }

    public List<Game> findByPlatform(Long platformId) {
        return gameGateway.findByPlatformId(platformId);
    }

    private List<Category> findCategories(List<Long> categoryIds) {
        return categoryIds.stream()
                .map(categoryId -> categoryGateway.findById(categoryId)
                        .orElseThrow(() -> new CategoryNotFoundException(categoryId)))
                .toList();
    }

    private List<Platform> findPlatforms(List<Long> platformIds) {
        return platformIds.stream()
                .map(platformId -> platformGateway.findById(platformId)
                        .orElseThrow(() -> new PlatformNotFoundException(platformId)))
                .toList();
    }
}
