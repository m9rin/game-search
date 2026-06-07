package dev.java10x.gamesearch.interfaceadapter.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

@Schema(description = "Request payload for creating or updating a game")
public record GameRequest(
        @Schema(description = "Game title", example = "The Last of Us Part II", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotEmpty(message = "title is required")
        String title,

        @Schema(description = "Game genre", example = "Action-Adventure", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotEmpty(message = "genre is required")
        String genre,

        @Schema(description = "Game release date", example = "2020-06-19", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "release date is required")
        LocalDate releaseDate,

        @Schema(description = "Game rating (0-10)", example = "9.5", requiredMode = Schema.RequiredMode.REQUIRED)
        @DecimalMin(value = "0.0", message = "rating must be greater than or equal to 0")
        @DecimalMax(value = "10.0", message = "rating must be less than or equal to 10")
        double rating,

        @Schema(description = "Game description", example = "An action-adventure game set in a post-apocalyptic world", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotEmpty(message = "description is required")
        String description,

        @Schema(description = "Game developer", example = "Naughty Dog", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotEmpty(message = "developer is required")
        String developer,

        @Schema(description = "Game publisher", example = "Sony Interactive Entertainment", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotEmpty(message = "publisher is required")
        String publisher,

        @Schema(description = "List of category IDs", example = "[1, 2, 3]", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotEmpty(message = "category is required")
        List<Long> categories,

        @Schema(description = "List of platform IDs", example = "[1, 2]", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotEmpty(message = "platform is required")
        List<Long> platforms
) {}
