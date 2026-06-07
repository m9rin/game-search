package dev.java10x.gamesearch.infrastructure.persistence.repository;

import dev.java10x.gamesearch.infrastructure.persistence.entity.PlatformEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlatformJpaRepository extends JpaRepository<PlatformEntity, Long> {
}
