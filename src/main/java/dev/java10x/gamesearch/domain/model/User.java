package dev.java10x.gamesearch.domain.model;

public record User(
        Long id,
        String name,
        String email,
        String password
) {}
