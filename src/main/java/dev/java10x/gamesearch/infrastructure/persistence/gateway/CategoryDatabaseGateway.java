package dev.java10x.gamesearch.infrastructure.persistence.gateway;

import dev.java10x.gamesearch.application.gateway.CategoryGateway;
import dev.java10x.gamesearch.domain.model.Category;
import dev.java10x.gamesearch.infrastructure.persistence.mapper.CategoryPersistenceMapper;
import dev.java10x.gamesearch.infrastructure.persistence.repository.CategoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CategoryDatabaseGateway implements CategoryGateway {

    private final CategoryJpaRepository categoryJpaRepository;

    @Override
    public Category save(Category category) {
        return CategoryPersistenceMapper.toDomain(
                categoryJpaRepository.save(CategoryPersistenceMapper.toEntity(category)));
    }

    @Override
    public List<Category> findAll() {
        return categoryJpaRepository.findAll()
                .stream()
                .map(CategoryPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryJpaRepository.findById(id)
                .map(CategoryPersistenceMapper::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        categoryJpaRepository.deleteById(id);
    }
}
