package dev.java10x.gamesearch.controllers.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "Response payload containing user profile information")
public record UserResponse(
        @Schema(description = "User unique identifier", example = "1")
        Long id,

        @Schema(description = "User full name", example = "John Doe")
        String name,

        @Schema(description = "User email address", example = "john.doe@example.com")
        String email
) {
}