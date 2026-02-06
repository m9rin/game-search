package dev.java10x.gamesearch.controllers.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

@Schema(description = "Request payload for creating or updating a platform")
public record PlatformRequest(
        @Schema(description = "Platform name", example = "PlayStation 5", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotEmpty(message = "name is required")
        String name
) {
}