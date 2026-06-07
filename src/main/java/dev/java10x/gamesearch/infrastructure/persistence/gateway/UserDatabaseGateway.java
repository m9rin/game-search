package dev.java10x.gamesearch.infrastructure.persistence.gateway;

import dev.java10x.gamesearch.application.gateway.UserGateway;
import dev.java10x.gamesearch.domain.model.User;
import dev.java10x.gamesearch.infrastructure.persistence.mapper.UserPersistenceMapper;
import dev.java10x.gamesearch.infrastructure.persistence.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserDatabaseGateway implements UserGateway {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User save(User user) {
        return UserPersistenceMapper.toDomain(
                userJpaRepository.save(UserPersistenceMapper.toEntity(user))
        );
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email)
                .map(UserPersistenceMapper::toDomain);
    }
}
