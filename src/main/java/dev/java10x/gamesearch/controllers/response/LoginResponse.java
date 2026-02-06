package dev.java10x.gamesearch.controllers.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response payload containing JWT authentication token")
public record LoginResponse(
        @Schema(description = "JWT authentication token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        String token
) {
}