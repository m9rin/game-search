package dev.java10x.gamesearch.interfaceadapter.rest.mapper;

import dev.java10x.gamesearch.application.usecase.game.CreateGameCommand;
import dev.java10x.gamesearch.application.usecase.game.UpdateGameCommand;
import dev.java10x.gamesearch.domain.model.Category;
import dev.java10x.gamesearch.domain.model.Game;
import dev.java10x.gamesearch.domain.model.Platform;
import dev.java10x.gamesearch.interfaceadapter.rest.dto.CategoryResponse;
import dev.java10x.gamesearch.interfaceadapter.rest.dto.GameRequest;
import dev.java10x.gamesearch.interfaceadapter.rest.dto.GameResponse;
import dev.java10x.gamesearch.interfaceadapter.rest.dto.PlatformResponse;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class GameRestMapper {

    public static CreateGameCommand toCreateCommand(GameRequest request) {
        return new CreateGameCommand(
                request.title(),
                request.genre(),
                request.releaseDate(),
                request.rating(),
                request.description(),
                request.developer(),
                request.publisher(),
                request.categories(),
                request.platforms()
        );
    }

    public static UpdateGameCommand toUpdateCommand(GameRequest request) {
        return new UpdateGameCommand(
                request.title(),
                request.genre(),
                request.releaseDate(),
                request.rating(),
                request.description(),
                request.developer(),
                request.publisher(),
                request.categories(),
                request.platforms()
        );
    }

    public static GameResponse toResponse(Game game) {
        return GameResponse.builder()
                .id(game.id())
                .title(game.title())
                .genre(game.genre())
                .releaseDate(game.releaseDate())
                .rating(game.rating())
                .description(game.description())
                .developer(game.developer())
                .publisher(game.publisher())
                .categories(toCategoryResponses(game.categories()))
                .platforms(toPlatformResponses(game.platforms()))
                .build();
    }

    private static List<CategoryResponse> toCategoryResponses(List<Category> categories) {
        return categories.stream()
                .map(category -> CategoryResponse.builder()
                        .id(category.id())
                        .name(category.name())
                        .build())
                .toList();
    }

    private static List<PlatformResponse> toPlatformResponses(List<Platform> platforms) {
        return platforms.stream()
                .map(platform -> PlatformResponse.builder()
                        .id(platform.id())
                        .name(platform.name())
                        .build())
                .toList();
    }
}