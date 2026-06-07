package dev.java10x.gamesearch.application.usecase.auth;

public record RegisterUserCommand(
        String name,
        String email,
        String password
) {
}
