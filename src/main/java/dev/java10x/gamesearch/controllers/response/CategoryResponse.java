package dev.java10x.gamesearch.controllers.response;

import lombok.Builder;

@Builder
public record CategoryResponse(Long id, String name) {
}
