package dev.java10x.gamesearch.application.usecase.category;

import dev.java10x.gamesearch.application.gateway.CategoryGateway;
import dev.java10x.gamesearch.domain.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryUseCase {

    private final CategoryGateway categoryGateway;

    public Category create(String name) {
        return categoryGateway.save(new Category(null, name));
    }

    public List<Category> findAll() {
        return categoryGateway.findAll();
    }

    public Optional<Category> findById(Long id) {
        return categoryGateway.findById(id);
    }

    public Optional<Category> update(Long id, String name) {
        return categoryGateway.findById(id)
                .map(category -> categoryGateway.save(new Category(category.id(), name)));
    }

    public boolean delete(Long id) {
        if (categoryGateway.findById(id).isEmpty()) {
            return false;
        }

        categoryGateway.deleteById(id);
        return true;
    }
}
