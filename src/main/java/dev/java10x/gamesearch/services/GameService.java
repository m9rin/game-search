package dev.java10x.gamesearch.services;

import dev.java10x.gamesearch.entities.Category;
import dev.java10x.gamesearch.entities.Game;
import dev.java10x.gamesearch.entities.Platform;
import dev.java10x.gamesearch.repositories.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository repository;
    private final CategoryService categoryService;
    private final PlatformService platformService;

    public Game save(Game game) {
        game.setCategories(this.findCategories(game.getCategories()));
        game.setPlatforms(this.findPlatforms(game.getPlatforms()));
        return repository.save(game);
    }

    public List<Game> getAll() {
        return repository.findAll();
    }

    public Optional<Game> getById(Long id) {
        return repository.findById(id);
    }

    public List<Game> getByCategory(Long categoryId) {
        return repository.findGameByCategories(List.of(Category.builder().id(categoryId).build()));
    }

    public List<Game> getByPlatforms(Long platformId) {
        return repository.findGameByPlatforms(List.of(Platform.builder().id(platformId).build()));
    }

    public Optional<Game> update(Long gameId, Game updateGame) {
        Optional<Game> optGame = repository.findById(gameId);

        List<Category> categories = this.findCategories(updateGame.getCategories());
        List<Platform> platforms = this.findPlatforms(updateGame.getPlatforms());

        if(optGame.isPresent()) {
            Game game = optGame.get();
            game.setTitle(updateGame.getTitle());
            game.setDescription(updateGame.getDescription());
            game.setReleaseDate(updateGame.getReleaseDate());
            game.setRating(updateGame.getRating());

            game.getCategories().clear();
            game.getCategories().addAll(categories);
            game.getPlatforms().clear();
            game.getPlatforms().addAll(platforms);

            repository.save(game);
            return Optional.of(game);
        }
        return Optional.empty();
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    private List<Category> findCategories(List<Category> categories) {
        List<Category> categoriesFound = new ArrayList<>();
        categories.forEach(category -> categoryService.getById(category.getId()).ifPresent(categoriesFound::add));
        return categoriesFound;
    }

    private List<Platform> findPlatforms(List<Platform> platforms) {
        List<Platform> platformsFound = new ArrayList<>();
        platforms.forEach(platform -> platformService.getById(platform.getId()).ifPresent(platformsFound::add));
        return platformsFound;
    }
}
