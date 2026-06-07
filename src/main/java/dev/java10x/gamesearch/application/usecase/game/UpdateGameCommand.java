package dev.java10x.gamesearch.application.usecase.game;

import java.time.LocalDate;
import java.util.List;

public record UpdateGameCommand(
        String title,
        String genre,
        LocalDate releaseDate,
        double rating,
        String description,
        String developer,
        String publisher,
        List<Long> categoryIds,
        List<Long> platformIds
){}
