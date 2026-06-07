package dev.java10x.gamesearch.interfaceadapter.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Schema(description = "Request payload for user registration")
public record UserRequest(
        @Schema(description = "User full name", example = "John Doe", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotEmpty(message = "Name is required")
        String name,

        @Schema(description = "User email address", example = "john.doe@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
        @Email(message = "Invalid email format")
        @NotEmpty(message = "Email is required")
        String email,

        @Schema(description = "User password", example = "securePassword123", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotEmpty(message = "Password is required")
        @Size(min = 6, message = "Password must be at least 6 characters")
        String password
) {}
