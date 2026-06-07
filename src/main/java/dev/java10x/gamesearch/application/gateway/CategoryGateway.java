package dev.java10x.gamesearch.application.gateway;

import dev.java10x.gamesearch.domain.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryGateway {

    Category save(Category category);

    List<Category> findAll();

    Optional<Category> findById(Long id);

    void deleteById(Long id);
}
