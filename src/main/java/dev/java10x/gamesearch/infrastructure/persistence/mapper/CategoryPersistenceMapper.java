package dev.java10x.gamesearch.infrastructure.persistence.mapper;

import dev.java10x.gamesearch.domain.model.Category;
import dev.java10x.gamesearch.infrastructure.persistence.entity.CategoryEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CategoryPersistenceMapper {

    public static CategoryEntity toEntity(Category category) {
        return CategoryEntity
                .builder()
                .id(category.id())
                .name(category.name())
                .build();
    }

    public static Category toDomain(CategoryEntity entity) {
        return new Category(
                entity.getId(),
                entity.getName()
        );
    }
}
