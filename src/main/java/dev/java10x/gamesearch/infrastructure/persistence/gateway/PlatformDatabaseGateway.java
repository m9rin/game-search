package dev.java10x.gamesearch.infrastructure.persistence.gateway;

import dev.java10x.gamesearch.application.gateway.PlatformGateway;
import dev.java10x.gamesearch.domain.model.Platform;
import dev.java10x.gamesearch.infrastructure.persistence.mapper.PlatformPersistenceMapper;
import dev.java10x.gamesearch.infrastructure.persistence.repository.PlatformJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PlatformDatabaseGateway implements PlatformGateway {

    private final PlatformJpaRepository platformJpaRepository;

    @Override
    public Platform save(Platform platform) {
        return PlatformPersistenceMapper.toDomain(
                platformJpaRepository.save(PlatformPersistenceMapper.toEntity(platform))
        );
    }

    @Override
    public List<Platform> findAll() {
        return platformJpaRepository.findAll()
                .stream()
                .map(PlatformPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Platform> findById(Long id) {
        return platformJpaRepository.findById(id)
                .map(PlatformPersistenceMapper::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        platformJpaRepository.deleteById(id);
    }
}
