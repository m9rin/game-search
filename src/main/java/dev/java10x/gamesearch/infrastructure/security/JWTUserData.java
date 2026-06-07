package dev.java10x.gamesearch.infrastructure.security;

import lombok.Builder;

@Builder
public record JWTUserData(Long id, String name, String email) {
}
