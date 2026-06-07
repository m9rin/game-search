package dev.java10x.gamesearch.application.gateway;

import dev.java10x.gamesearch.domain.model.User;

public interface TokenGateway {

    String generateToken(User user);
}
