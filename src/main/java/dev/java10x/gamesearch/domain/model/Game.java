package dev.java10x.gamesearch.domain.model;

import java.time.LocalDate;
import java.util.List;

public record Game(
        Long id,
        String title,
        String genre,
        LocalDate releaseDate,
        double rating,
        String description,
        String developer,
        String publisher,
        List<Category> categories,
        List<Platform> platforms
) {
}
