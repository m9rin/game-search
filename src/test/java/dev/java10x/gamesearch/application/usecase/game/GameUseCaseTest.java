package dev.java10x.gamesearch.application.usecase.game;

import dev.java10x.gamesearch.application.gateway.CategoryGateway;
import dev.java10x.gamesearch.application.gateway.GameGateway;
import dev.java10x.gamesearch.application.gateway.PlatformGateway;
import dev.java10x.gamesearch.domain.exception.CategoryNotFoundException;
import dev.java10x.gamesearch.domain.exception.PlatformNotFoundException;
import dev.java10x.gamesearch.domain.model.Category;
import dev.java10x.gamesearch.domain.model.Game;
import dev.java10x.gamesearch.domain.model.Platform;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class GameUseCaseTest {

    private static class InMemoryGameGateway implements GameGateway {

        private Long sequence = 0L;
        private final List<Game> games = new ArrayList<>();

        @Override
        public Game save(Game game) {
            if (game.id() == null) {
                sequence++;

                Game created = new Game(
                        sequence,
                        game.title(),
                        game.genre(),
                        game.releaseDate(),
                        game.rating(),
                        game.description(),
                        game.developer(),
                        game.publisher(),
                        game.categories(),
                        game.platforms()
                );

                games.add(created);
                return created;
            }

            games.removeIf(existing -> existing.id().equals(game.id()));
            games.add(game);
            return game;
        }

        @Override
        public List<Game> findAll() {
            return List.copyOf(games);
        }

        @Override
        public Optional<Game> findById(Long id) {
            return games.stream()
                    .filter(game -> game.id().equals(id))
                    .findFirst();
        }

        @Override
        public void deleteById(Long id) {
            games.removeIf(game -> game.id().equals(id));
        }

        @Override
        public List<Game> findByCategoryId(Long categoryId) {
            return games.stream()
                    .filter(game -> game.categories()
                            .stream()
                            .anyMatch(category -> category.id().equals(categoryId)))
                    .toList();
        }

        @Override
        public List<Game> findByPlatformId(Long platformId) {
            return games.stream()
                    .filter(game -> game.platforms()
                            .stream()
                            .anyMatch(platform -> platform.id().equals(platformId)))
                    .toList();
        }
    }

    public static class InMemoryCategoryGateway implements CategoryGateway {

        private Long sequence = 0L;
        private final List<Category> categories = new ArrayList<>();

        @Override
        public Category save(Category category) {

            if (category.id() == null) {
                sequence++;
                Category created = new Category(sequence, category.name());
                categories.add(created);
                return created;
            }

            categories.removeIf(existing -> existing.id().equals(category.id()));
            categories.add(category);
            return category;

        }

        @Override
        public List<Category> findAll() {
            return List.copyOf(categories);
        }

        @Override
        public Optional<Category> findById(Long id) {
            return categories.stream()
                    .filter(category -> category.id().equals(id))
                    .findFirst();
        }

        @Override
        public void deleteById(Long id) {
            categories.removeIf(category -> category.id().equals(id));
        }
    }

    private static class InMemoryPlatformGateway implements PlatformGateway {

        private Long sequence = 0L;
        private final List<Platform> platforms = new ArrayList<>();

        @Override
        public Platform save(Platform platform) {
            if (platform.id() == null) {
                sequence++;
                Platform created = new Platform(sequence, platform.name());
                platforms.add(created);
                return created;
            }
            platforms.removeIf(existing -> existing.id().equals(platform.id()));
            platforms.add(platform);
            return platform;
        }

        @Override
        public List<Platform> findAll() {
            return List.copyOf(platforms);
        }

        @Override
        public Optional<Platform> findById(Long id) {
            return platforms.stream()
                    .filter(platform -> platform.id().equals(id))
                    .findFirst();
        }

        @Override
        public void deleteById(Long id) {
            platforms.removeIf(platform -> platform.id().equals(id));
        }
    }

    @Test
    void shouldCreateGameWithCategoriesAndPlatforms() {

        GameGateway gameGateway = new InMemoryGameGateway();
        CategoryGateway categoryGateway = new InMemoryCategoryGateway();
        PlatformGateway platformGateway = new InMemoryPlatformGateway();

        Category action = categoryGateway.save(new Category(null, "Action"));
        Platform windows = platformGateway.save(new Platform(null, "Windows"));

        GameUseCase useCase = new GameUseCase(gameGateway, categoryGateway, platformGateway);

        CreateGameCommand command = new CreateGameCommand(
                "The Witcher 3",
                "RPG",
                LocalDate.of(2015, 5, 19),
                10.0,
                "Open-world RPG",
                "CD Projekt Red",
                "CD Projekt",
                List.of(action.id()),
                List.of(windows.id())
        );

        Game created = useCase.create(command);

        assertThat(created.id()).isNotNull();
        assertThat(created.title()).isEqualTo("The Witcher 3");
        assertThat(created.categories()).extracting(Category::name).containsExactly("Action");
        assertThat(created.platforms()).extracting(Platform::name).containsExactly("Windows");
    }

    @Test
    void shouldThrowExceptionWhenCreatingGameWithMissingCategory() {
        GameGateway gameGateway = new InMemoryGameGateway();
        CategoryGateway categoryGateway = new InMemoryCategoryGateway();
        PlatformGateway platformGateway = new InMemoryPlatformGateway();

        Platform windows = platformGateway.save(new Platform(null, "Windows"));

        GameUseCase useCase = new GameUseCase(gameGateway, categoryGateway, platformGateway);

        CreateGameCommand command = new CreateGameCommand(
                "The Witcher 3",
                "RPG",
                LocalDate.of(2015, 5, 19),
                10.0,
                "Open-world RPG",
                "CD Projekt Red",
                "CD Projekt",
                List.of(100L),
                List.of(windows.id())
        );

        assertThatThrownBy(() -> useCase.create(command))
                .isInstanceOf(CategoryNotFoundException.class)
                .hasMessage("Category not found with id: 100");
    }

    @Test
    void shouldThrowExceptionWhenCreatingGameWithMissingPlatform() {
        GameGateway gameGateway = new InMemoryGameGateway();
        CategoryGateway categoryGateway = new InMemoryCategoryGateway();
        PlatformGateway platformGateway = new InMemoryPlatformGateway();

        Category action = categoryGateway.save(new Category(null, "Action"));

        GameUseCase useCase = new GameUseCase(gameGateway, categoryGateway, platformGateway);

        CreateGameCommand command = new CreateGameCommand(
                "The Witcher 3",
                "RPG",
                LocalDate.of(2015, 5, 19),
                10.0,
                "Open-world RPG",
                "CD Projekt Red",
                "CD Projekt",
                List.of(action.id()),
                List.of(100L)
        );

        assertThatThrownBy(() -> useCase.create(command))
                .isInstanceOf(PlatformNotFoundException.class)
                .hasMessage("Platform not found with id: 100");
    }

    @Test
    void shouldListGames() {
        GameGateway gameGateway = new InMemoryGameGateway();
        CategoryGateway categoryGateway = new InMemoryCategoryGateway();
        PlatformGateway platformGateway = new InMemoryPlatformGateway();

        Category action = categoryGateway.save(new Category(null, "Action"));
        Platform windows = platformGateway.save(new Platform(null, "Windows"));

        GameUseCase useCase = new GameUseCase(gameGateway, categoryGateway, platformGateway);

        useCase.create(new CreateGameCommand(
                "The Witcher 3",
                "RPG",
                LocalDate.of(2015, 5, 19),
                10.0,
                "Open-world RPG",
                "CD Projekt Red",
                "CD Projekt",
                List.of(action.id()),
                List.of(windows.id())
        ));

        useCase.create(new CreateGameCommand(
                "God of War",
                "Action-Adventure",
                LocalDate.of(2018, 4, 20),
                9.5,
                "Action adventure game",
                "Santa Monica Studio",
                "Sony Interactive Entertainment",
                List.of(action.id()),
                List.of(windows.id())
        ));

        List<Game> games = useCase.findAll();

        assertThat(games).hasSize(2);
        assertThat(games)
                .extracting(Game::title)
                .containsExactly("The Witcher 3", "God of War");
    }

    @Test
    void shouldFindGameById() {
        GameGateway gameGateway = new InMemoryGameGateway();
        CategoryGateway categoryGateway = new InMemoryCategoryGateway();
        PlatformGateway platformGateway = new InMemoryPlatformGateway();

        Category action = categoryGateway.save(new Category(null, "Action"));
        Platform windows = platformGateway.save(new Platform(null, "Windows"));

        GameUseCase useCase = new GameUseCase(gameGateway, categoryGateway, platformGateway);

        Game created = useCase.create(new CreateGameCommand(
                "The Witcher 3",
                "RPG",
                LocalDate.of(2015, 5, 19),
                10.0,
                "Open-world RPG",
                "CD Projekt Red",
                "CD Projekt",
                List.of(action.id()),
                List.of(windows.id())
        ));

        Optional<Game> found = useCase.findById(created.id());

        assertThat(found).isPresent();
        assertThat(found.get().id()).isEqualTo(created.id());
        assertThat(found.get().title()).isEqualTo("The Witcher 3");
    }

    @Test
    void shouldUpdateExistingGame() {
        GameGateway gameGateway = new InMemoryGameGateway();
        CategoryGateway categoryGateway = new InMemoryCategoryGateway();
        PlatformGateway platformGateway = new InMemoryPlatformGateway();

        Category action = categoryGateway.save(new Category(null, "Action"));
        Category rpg = categoryGateway.save(new Category(null, "RPG"));
        Platform windows = platformGateway.save(new Platform(null, "Windows"));
        Platform playstation = platformGateway.save(new Platform(null, "PlayStation 5"));

        GameUseCase useCase = new GameUseCase(gameGateway, categoryGateway, platformGateway);

        Game created = useCase.create(new CreateGameCommand(
                "The Witcher 3",
                "RPG",
                LocalDate.of(2015, 5, 19),
                10.0,
                "Open-world RPG",
                "CD Projekt Red",
                "CD Projekt",
                List.of(action.id()),
                List.of(windows.id())
        ));

        Optional<Game> updated = useCase.update(
                created.id(),
                new UpdateGameCommand(
                        "The Witcher 3: Wild Hunt",
                        "Action RPG",
                        LocalDate.of(2015, 5, 19),
                        9.8,
                        "Updated description",
                        "CD Projekt Red",
                        "CD Projekt",
                        List.of(rpg.id()),
                        List.of(playstation.id())
                )
        );

        assertThat(updated).isPresent();
        assertThat(updated.get().id()).isEqualTo(created.id());
        assertThat(updated.get().title()).isEqualTo("The Witcher 3: Wild Hunt");
        assertThat(updated.get().genre()).isEqualTo("Action RPG");
        assertThat(updated.get().rating()).isEqualTo(9.8);
        assertThat(updated.get().description()).isEqualTo("Updated description");
        assertThat(updated.get().developer()).isEqualTo("CD Projekt Red");
        assertThat(updated.get().publisher()).isEqualTo("CD Projekt");
        assertThat(updated.get().categories()).extracting(Category::name).containsExactly("RPG");
        assertThat(updated.get().platforms()).extracting(Platform::name).containsExactly("PlayStation 5");
    }

    @Test
    void shouldReturnEmptyWhenUpdatingMissingGame() {
        GameGateway gameGateway = new InMemoryGameGateway();
        CategoryGateway categoryGateway = new InMemoryCategoryGateway();
        PlatformGateway platformGateway = new InMemoryPlatformGateway();

        Category action = categoryGateway.save(new Category(null, "Action"));
        Platform windows = platformGateway.save(new Platform(null, "Windows"));

        GameUseCase useCase = new GameUseCase(gameGateway, categoryGateway, platformGateway);

        Optional<Game> updated = useCase.update(
                999L,
                new UpdateGameCommand(
                        "The Witcher 3",
                        "RPG",
                        LocalDate.of(2015, 5, 19),
                        10.0,
                        "Open-world RPG",
                        "CD Projekt Red",
                        "CD Projekt",
                        List.of(action.id()),
                        List.of(windows.id())
                )
        );

        assertThat(updated).isEmpty();
    }

    @Test
    void shouldThrowExceptionWhenUpdatingGameWithMissingCategory() {
        GameGateway gameGateway = new InMemoryGameGateway();
        CategoryGateway categoryGateway = new InMemoryCategoryGateway();
        PlatformGateway platformGateway = new InMemoryPlatformGateway();

        Category action = categoryGateway.save(new Category(null, "Action"));
        Platform windows = platformGateway.save(new Platform(null, "Windows"));

        GameUseCase useCase = new GameUseCase(gameGateway, categoryGateway, platformGateway);

        Game created = useCase.create(new CreateGameCommand(
                "The Witcher 3",
                "RPG",
                LocalDate.of(2015, 5, 19),
                10.0,
                "Open-world RPG",
                "CD Projekt Red",
                "CD Projekt",
                List.of(action.id()),
                List.of(windows.id())
        ));

        assertThatThrownBy(() -> useCase.update(
                created.id(),
                new UpdateGameCommand(
                        "The Witcher 3",
                        "RPG",
                        LocalDate.of(2015, 5, 19),
                        10.0,
                        "Open-world RPG",
                        "CD Projekt Red",
                        "CD Projekt",
                        List.of(999L),
                        List.of(windows.id())
                )
        ))
                .isInstanceOf(CategoryNotFoundException.class)
                .hasMessage("Category not found with id: 999");
    }

    @Test
    void shouldThrowExceptionWhenUpdatingGameWithMissingPlatform() {
        GameGateway gameGateway = new InMemoryGameGateway();
        CategoryGateway categoryGateway = new InMemoryCategoryGateway();
        PlatformGateway platformGateway = new InMemoryPlatformGateway();

        Category action = categoryGateway.save(new Category(null, "Action"));
        Platform windows = platformGateway.save(new Platform(null, "Windows"));

        GameUseCase useCase = new GameUseCase(gameGateway, categoryGateway, platformGateway);

        Game created = useCase.create(new CreateGameCommand(
                "The Witcher 3",
                "RPG",
                LocalDate.of(2015, 5, 19),
                10.0,
                "Open-world RPG",
                "CD Projekt Red",
                "CD Projekt",
                List.of(action.id()),
                List.of(windows.id())
        ));

        assertThatThrownBy(() -> useCase.update(
                created.id(),
                new UpdateGameCommand(
                        "The Witcher 3",
                        "RPG",
                        LocalDate.of(2015, 5, 19),
                        10.0,
                        "Open-world RPG",
                        "CD Projekt Red",
                        "CD Projekt",
                        List.of(action.id()),
                        List.of(999L)
                )
        ))
                .isInstanceOf(PlatformNotFoundException.class)
                .hasMessage("Platform not found with id: 999");
    }

    @Test
    void shouldDeleteExistingGame() {
        GameGateway gameGateway = new InMemoryGameGateway();
        CategoryGateway categoryGateway = new InMemoryCategoryGateway();
        PlatformGateway platformGateway = new InMemoryPlatformGateway();

        Category action = categoryGateway.save(new Category(null, "Action"));
        Platform windows = platformGateway.save(new Platform(null, "Windows"));

        GameUseCase useCase = new GameUseCase(gameGateway, categoryGateway, platformGateway);

        Game created = useCase.create(new CreateGameCommand(
                "The Witcher 3",
                "RPG",
                LocalDate.of(2015, 5, 19),
                10.0,
                "Open-world RPG",
                "CD Projekt Red",
                "CD Projekt",
                List.of(action.id()),
                List.of(windows.id())
        ));

        boolean deleted = useCase.delete(created.id());

        assertThat(deleted).isTrue();
        assertThat(useCase.findById(created.id())).isEmpty();
    }

    @Test
    void shouldReturnFalseWhenDeletingMissingGame() {
        GameGateway gameGateway = new InMemoryGameGateway();
        CategoryGateway categoryGateway = new InMemoryCategoryGateway();
        PlatformGateway platformGateway = new InMemoryPlatformGateway();

        GameUseCase useCase = new GameUseCase(gameGateway, categoryGateway, platformGateway);

        boolean deleted = useCase.delete(999L);

        assertThat(deleted).isFalse();
    }

    @Test
    void shouldFindGamesByCategory() {
        GameGateway gameGateway = new InMemoryGameGateway();
        CategoryGateway categoryGateway = new InMemoryCategoryGateway();
        PlatformGateway platformGateway = new InMemoryPlatformGateway();

        Category action = categoryGateway.save(new Category(null, "Action"));
        Category rpg = categoryGateway.save(new Category(null, "RPG"));
        Platform windows = platformGateway.save(new Platform(null, "Windows"));

        GameUseCase useCase = new GameUseCase(gameGateway, categoryGateway, platformGateway);

        useCase.create(new CreateGameCommand(
                "The Witcher 3",
                "RPG",
                LocalDate.of(2015, 5, 19),
                10.0,
                "Open-world RPG",
                "CD Projekt Red",
                "CD Projekt",
                List.of(rpg.id()),
                List.of(windows.id())
        ));

        useCase.create(new CreateGameCommand(
                "God of War",
                "Action-Adventure",
                LocalDate.of(2018, 4, 20),
                9.5,
                "Action adventure game",
                "Santa Monica Studio",
                "Sony Interactive Entertainment",
                List.of(action.id()),
                List.of(windows.id())
        ));

        List<Game> games = useCase.findByCategory(action.id());

        assertThat(games).hasSize(1);
        assertThat(games.get(0).title()).isEqualTo("God of War");
    }

    @Test
    void shouldFindGamesByPlatform() {
        GameGateway gameGateway = new InMemoryGameGateway();
        CategoryGateway categoryGateway = new InMemoryCategoryGateway();
        PlatformGateway platformGateway = new InMemoryPlatformGateway();

        Category action = categoryGateway.save(new Category(null, "Action"));
        Platform windows = platformGateway.save(new Platform(null, "Windows"));
        Platform playstation = platformGateway.save(new Platform(null, "PlayStation 5"));

        GameUseCase useCase = new GameUseCase(gameGateway, categoryGateway, platformGateway);

        useCase.create(new CreateGameCommand(
                "The Witcher 3",
                "RPG",
                LocalDate.of(2015, 5, 19),
                10.0,
                "Open-world RPG",
                "CD Projekt Red",
                "CD Projekt",
                List.of(action.id()),
                List.of(windows.id())
        ));

        useCase.create(new CreateGameCommand(
                "God of War",
                "Action-Adventure",
                LocalDate.of(2018, 4, 20),
                9.5,
                "Action adventure game",
                "Santa Monica Studio",
                "Sony Interactive Entertainment",
                List.of(action.id()),
                List.of(playstation.id())
        ));

        List<Game> games = useCase.findByPlatform(playstation.id());

        assertThat(games).hasSize(1);
        assertThat(games.get(0).title()).isEqualTo("God of War");
    }
}
