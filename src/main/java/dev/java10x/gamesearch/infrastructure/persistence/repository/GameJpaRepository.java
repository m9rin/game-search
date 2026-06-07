package dev.java10x.gamesearch.infrastructure.persistence.repository;

import dev.java10x.gamesearch.infrastructure.persistence.entity.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameJpaRepository extends JpaRepository<GameEntity, Long> {

    List<GameEntity> findByCategoriesId(Long categoryId);

    List<GameEntity> findByPlatformsId(Long platformId);
}
