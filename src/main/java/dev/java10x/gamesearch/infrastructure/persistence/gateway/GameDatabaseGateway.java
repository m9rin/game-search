package dev.java10x.gamesearch.infrastructure.persistence.gateway;

import dev.java10x.gamesearch.application.gateway.GameGateway;
import dev.java10x.gamesearch.domain.model.Game;
import dev.java10x.gamesearch.infrastructure.persistence.mapper.GamePersistenceMapper;
import dev.java10x.gamesearch.infrastructure.persistence.repository.GameJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GameDatabaseGateway implements GameGateway {

    private final GameJpaRepository gameJpaRepository;

    @Override
    public Game save(Game game) {
        return GamePersistenceMapper.toDomain(
                gameJpaRepository.save(GamePersistenceMapper.toEntity(game))
        );
    }

    @Override
    public List<Game> findAll() {
        return gameJpaRepository.findAll()
                .stream()
                .map(GamePersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Game> findById(Long id) {
        return gameJpaRepository.findById(id)
                .map(GamePersistenceMapper::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        gameJpaRepository.deleteById(id);
    }

    @Override
    public List<Game> findByCategoryId(Long categoryId) {
        return gameJpaRepository.findByCategoriesId(categoryId)
                .stream()
                .map(GamePersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public List<Game> findByPlatformId(Long platformId) {
        return gameJpaRepository.findByPlatformsId(platformId)
                .stream()
                .map(GamePersistenceMapper::toDomain)
                .toList();
    }
}
