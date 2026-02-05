package dev.java10x.gamesearch.mapper;

import dev.java10x.gamesearch.controllers.request.GameRequest;
import dev.java10x.gamesearch.controllers.response.CategoryResponse;
import dev.java10x.gamesearch.controllers.response.GameResponse;
import dev.java10x.gamesearch.controllers.response.PlatformResponse;
import dev.java10x.gamesearch.entities.Category;
import dev.java10x.gamesearch.entities.Game;
import dev.java10x.gamesearch.entities.Platform;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class GameMapper {

    public static Game toGame(GameRequest request) {
        List<Category> categories = request.categories().stream()
                .map(categoryId -> Category.builder().id(categoryId).build())
                .toList();

        List<Platform> platforms = request.platforms().stream()
                .map(platformId -> Platform.builder().id(platformId).build())
                .toList();

        return Game.builder()
                .title(request.title())
                .genre(request.genre())
                .releaseDate(request.releaseDate())
                .rating(request.rating())
                .description(request.description())
                .developer(request.developer())
                .publisher(request.publisher())
                .categories(categories)
                .platforms(platforms)
                .build();
    }

    public static GameResponse toGameResponse(Game game) {

        List<CategoryResponse> categories = game.getCategories()
                .stream()
                .map(CategoryMapper::toCategoryResponse)
                .toList();

        List<PlatformResponse> platforms = game.getPlatforms()
                .stream()
                .map(PlatformMapper::toPlatformResponse)
                .toList();

        return GameResponse.builder()
                .id(game.getId())
                .title(game.getTitle())
                .genre(game.getGenre())
                .releaseDate(game.getReleaseDate())
                .rating(game.getRating())
                .description(game.getDescription())
                .developer(game.getDeveloper())
                .publisher(game.getPublisher())
                .categories(categories)
                .platforms(platforms)
                .build();
    }
}
