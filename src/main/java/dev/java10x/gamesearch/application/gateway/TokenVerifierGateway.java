package dev.java10x.gamesearch.application.gateway;

import dev.java10x.gamesearch.infrastructure.security.JWTUserData;

import java.util.Optional;

public interface TokenVerifierGateway {

    Optional<JWTUserData> verifyToken(String token);
}
