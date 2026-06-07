package dev.java10x.gamesearch.domain.exception;

public class PlatformNotFoundException extends RuntimeException {

    public PlatformNotFoundException(Long id) {
        super("Platform not found with id: " + id);
    }
}
