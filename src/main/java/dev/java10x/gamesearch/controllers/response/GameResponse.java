package dev.java10x.gamesearch.controllers.response;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record GameResponse(
        Long id,
        String title,
        String genre,
        LocalDate releaseDate,
        double rating,
        String description,
        String developer,
        String publisher,
        List<CategoryResponse> categories,
        List<PlatformResponse> platforms
) {
}
