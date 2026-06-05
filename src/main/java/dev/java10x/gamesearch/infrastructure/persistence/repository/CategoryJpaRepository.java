package dev.java10x.gamesearch.infrastructure.persistence.repository;

import dev.java10x.gamesearch.infrastructure.persistence.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryJpaRepository extends JpaRepository <CategoryEntity, Long> {
}
