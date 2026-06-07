package dev.java10x.gamesearch.application.usecase.auth;

public record LoginCommand(
        String email,
        String password
) {
}
