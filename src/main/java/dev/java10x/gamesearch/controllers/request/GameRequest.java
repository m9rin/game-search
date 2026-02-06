package dev.java10x.gamesearch.controllers.request;

import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record GameRequest(
        @NotEmpty(message = "title is required") String title,
        @NotEmpty(message = "genre is required")String genre,
        @NotEmpty(message = "release date is required")LocalDate releaseDate,
        @NotEmpty(message = "rating is required")double rating,
        @NotEmpty(message = "description is required")String description,
        @NotEmpty(message = "developer is required")String developer,
        @NotEmpty(message = "publisher is required")String publisher,
        @NotEmpty(message = "category is required")List<Long> categories,
        @NotEmpty(message = "platform is required")List<Long> platforms
) {
}
