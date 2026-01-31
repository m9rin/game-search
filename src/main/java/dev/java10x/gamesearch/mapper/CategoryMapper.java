package dev.java10x.gamesearch.mapper;

import dev.java10x.gamesearch.controllers.request.CategoryRequest;
import dev.java10x.gamesearch.controllers.response.CategoryResponse;
import dev.java10x.gamesearch.entities.Category;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CategoryMapper {

    public static Category toCategory(CategoryRequest request) {
        return Category.builder()
                .name(request.name())
                .build();
    }

    public static CategoryResponse toCategoryResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
