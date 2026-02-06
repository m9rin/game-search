package dev.java10x.gamesearch.controllers.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

@Schema(description = "Request payload for user authentication")
public record LoginRequest(
        @Schema(description = "User email address", example = "user@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
        @Email(message = "Invalid email format")
        @NotEmpty(message = "Email is required")
        String email,

        @Schema(description = "User password", example = "password123", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotEmpty(message = "Password is required")
        String password
) {
}