package dev.java10x.gamesearch.infrastructure.persistence.mapper;

import dev.java10x.gamesearch.domain.model.Category;
import dev.java10x.gamesearch.domain.model.Game;
import dev.java10x.gamesearch.domain.model.Platform;
import dev.java10x.gamesearch.infrastructure.persistence.entity.CategoryEntity;
import dev.java10x.gamesearch.infrastructure.persistence.entity.GameEntity;
import dev.java10x.gamesearch.infrastructure.persistence.entity.PlatformEntity;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class GamePersistenceMapper {

    public static GameEntity toEntity(Game game) {
        return GameEntity.builder()
                .id(game.id())
                .title(game.title())
                .genre(game.genre())
                .releaseDate(game.releaseDate())
                .rating(game.rating())
                .description(game.description())
                .developer(game.developer())
                .publisher(game.publisher())
                .categories(toCategoryEntities(game.categories()))
                .platforms(toPlatformEntities(game.platforms()))
                .build();
    }

    public static Game toDomain(GameEntity entity) {
        return new Game(
                entity.getId(),
                entity.getTitle(),
                entity.getGenre(),
                entity.getReleaseDate(),
                entity.getRating(),
                entity.getDescription(),
                entity.getDeveloper(),
                entity.getPublisher(),
                toCategories(entity.getCategories()),
                toPlatforms(entity.getPlatforms())
        );
    }

    private static List<CategoryEntity> toCategoryEntities(List<Category> categories) {
        return categories.stream()
                .map(category -> CategoryEntity.builder()
                        .id(category.id())
                        .name(category.name())
                        .build())
                .toList();
    }

    private static List<PlatformEntity> toPlatformEntities(List<Platform> platforms) {
        return platforms.stream()
                .map(platform -> PlatformEntity.builder()
                        .id(platform.id())
                        .name(platform.name())
                        .build())
                .toList();
    }

    private static List<Category> toCategories(List<CategoryEntity> categories) {
        return categories.stream()
                .map(category -> new Category(category.getId(), category.getName()))
                .toList();
    }

    private static List<Platform> toPlatforms(List<PlatformEntity> platforms) {
        return platforms.stream()
                .map(platform -> new Platform(platform.getId(), platform.getName()))
                .toList();
    }
}
