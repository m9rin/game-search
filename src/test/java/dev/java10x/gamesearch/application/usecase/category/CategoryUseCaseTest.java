package dev.java10x.gamesearch.application.usecase.category;

import dev.java10x.gamesearch.application.gateway.CategoryGateway;
import dev.java10x.gamesearch.domain.model.Category;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryUseCaseTest {

    public static class InMemoryCategoryGateway implements CategoryGateway {

        private Long sequence = 0L;
        private final List<Category> categories = new ArrayList<>();

        @Override
        public Category save(Category category) {

            if (category.id() == null) {
                sequence++;
                Category created = new Category(sequence, category.name());
                categories.add(created);
                return created;
            }

            categories.removeIf(existing -> existing.id().equals(category.id()));
            categories.add(category);
            return category;

        }

        @Override
        public List<Category> findAll() {
            return categories;
        }

        @Override
        public Optional<Category> findById(Long id) {
            return categories.stream()
                    .filter(category -> category.id().equals(id))
                    .findFirst();
        }

        @Override
        public void deleteById(Long id) {
            categories.removeIf(category -> category.id().equals(id));
        }
    }

    @Test
    void shouldCreateCategory() {

        CategoryGateway gateway = new InMemoryCategoryGateway();
        CategoryUseCase useCase = new CategoryUseCase(gateway);

        Category created = useCase.create("Action");

        assertThat(created.id()).isNotNull();
        assertThat(created.name()).isEqualTo("Action");
    }

    @Test
    void shouldListCategories() {

        CategoryGateway gateway = new InMemoryCategoryGateway();
        CategoryUseCase useCase = new CategoryUseCase(gateway);

        useCase.create("Action");
        useCase.create("Adventure");

        List<Category> categories = useCase.findAll();

        assertThat(categories).hasSize(2);
        assertThat(categories)
                .extracting(Category::name)
                .containsExactly("Action", "Adventure");
    }

    @Test
    void shouldFindCategoryById() {

        CategoryGateway gateway = new InMemoryCategoryGateway();
        CategoryUseCase useCase = new CategoryUseCase(gateway);

        Category created = useCase.create("Action");

        Optional<Category> found = useCase.findById(created.id());

        assertThat(found).isPresent();
        assertThat(found.get().id()).isEqualTo(created.id());
        assertThat(found.get().name()).isEqualTo("Action");
    }

    @Test
    void shouldUpdatedExistingCategory() {

        CategoryGateway gateway = new InMemoryCategoryGateway();
        CategoryUseCase useCase = new CategoryUseCase(gateway);

        Category created = useCase.create("Action");

        Optional<Category> updated = useCase.update(created.id(), "Adventure");

        assertThat(updated).isPresent();
        assertThat(updated.get().id()).isEqualTo(created.id());
        assertThat(updated.get().name()).isEqualTo("Adventure");
    }

    @Test
    void shouldReturnEmptyWhenUpdatingMissingCategory() {

        CategoryGateway gateway = new InMemoryCategoryGateway();
        CategoryUseCase useCase = new CategoryUseCase(gateway);


        Optional<Category> updated = useCase.update(100L, "Adventure");

        assertThat(updated).isEmpty();
    }

    @Test
    void shouldDeleteExistingCategory() {

        CategoryGateway gateway = new InMemoryCategoryGateway();
        CategoryUseCase useCase = new CategoryUseCase(gateway);

        Category created = useCase.create("Action");

        boolean deleted = useCase.delete(created.id());

        assertThat(deleted).isTrue();
        assertThat(useCase.findById(created.id())).isEmpty();
    }

    @Test
    void shouldReturnFalseWhenDeleteMissingCategory() {

        CategoryGateway gateway = new InMemoryCategoryGateway();
        CategoryUseCase useCase = new CategoryUseCase(gateway);

        boolean deleted = useCase.delete(100L);

        assertThat(deleted).isFalse();
    }
}
