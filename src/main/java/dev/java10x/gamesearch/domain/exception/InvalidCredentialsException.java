package dev.java10x.gamesearch.domain.exception;

public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException() {
        super("Email or password invalid.");
    }
}
