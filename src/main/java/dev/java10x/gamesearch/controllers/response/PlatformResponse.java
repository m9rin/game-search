package dev.java10x.gamesearch.controllers.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "Response payload containing platform information")
public record PlatformResponse(
        @Schema(description = "Platform unique identifier", example = "1")
        Long id,

        @Schema(description = "Platform name", example = "PlayStation 5")
        String name
) {
}