package dev.java10x.gamesearch.controllers.request;

import jakarta.validation.constraints.NotEmpty;

public record PlatformRequest(@NotEmpty(message = "name is required") String name) {
}
