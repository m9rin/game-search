package dev.java10x.gamesearch.services;

import dev.java10x.gamesearch.entities.Category;
import dev.java10x.gamesearch.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;

    public Category save(Category category) {
        return repository.save(category);
    }

    public List<Category> getAll() {
        return repository.findAll();
    }

    public Optional<Category> findById(Long id) {
        return repository.findById(id);
    }

    public Optional<Category> update(Long id, Category updateCategory) {
        Optional<Category> optCategory = repository.findById(id);

        if(optCategory.isPresent()) {
            Category category = optCategory.get();
                category.setName(updateCategory.getName());
                repository.save(category);
                return Optional.of(category);
        }
        return Optional.empty();
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
