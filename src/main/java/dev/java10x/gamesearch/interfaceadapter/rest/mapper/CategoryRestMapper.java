package dev.java10x.gamesearch.interfaceadapter.rest.mapper;

import dev.java10x.gamesearch.domain.model.Category;
import dev.java10x.gamesearch.interfaceadapter.rest.dto.CategoryRequest;
import dev.java10x.gamesearch.interfaceadapter.rest.dto.CategoryResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CategoryRestMapper {

    public static Category toDomain(CategoryRequest request) {
        return new Category(null, request.name());
    }

    public static CategoryResponse toResponse(Category category) {
        return CategoryResponse
                .builder()
                .id(category.id())
                .name(category.name())
                .build();
    }
}
