package dev.java10x.gamesearch.controllers.request;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record GameRequest(
        String title,
        String genre,
        LocalDate releaseDate,
        double rating,
        String description,
        String developer,
        String publisher,
        List<Long> categories,
        List<Long> platforms
) {
}
