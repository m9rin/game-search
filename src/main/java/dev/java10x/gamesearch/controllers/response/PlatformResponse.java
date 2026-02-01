package dev.java10x.gamesearch.controllers.response;

import lombok.Builder;

@Builder
public record PlatformResponse(Long id, String name) {
}
