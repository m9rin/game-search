package dev.java10x.gamesearch.interfaceadapter.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
@Schema(description = "Response payload containing game information")
public record GameResponse(
        @Schema(description = "Game unique identifier", example = "1")
        Long id,

        @Schema(description = "Game title", example = "The Last of Us Part II")
        String title,

        @Schema(description = "Game genre", example = "Action-Adventure")
        String genre,

        @Schema(description = "Game release date", example = "2020-06-19")
        LocalDate releaseDate,

        @Schema(description = "Game rating (0-10)", example = "9.5")
        double rating,

        @Schema(description = "Game description", example = "An action-adventure game set in a post-apocalyptic world")
        String description,

        @Schema(description = "Game developer", example = "Naughty Dog")
        String developer,

        @Schema(description = "Game publisher", example = "Sony Interactive Entertainment")
        String publisher,

        @Schema(description = "Game categories")
        List<CategoryResponse> categories,

        @Schema(description = "Game platforms")
        List<PlatformResponse> platforms
) {}
