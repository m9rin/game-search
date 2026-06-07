package dev.java10x.gamesearch.application.gateway;

import dev.java10x.gamesearch.domain.model.User;

import java.util.Optional;

public interface UserGateway {

    User save(User user);

    Optional<User> findByEmail(String email);
}
