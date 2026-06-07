package dev.java10x.gamesearch.interfaceadapter.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response payload containing JWT token")
public record LoginResponse(
        @Schema(description = "JWT access token")
        String token
) {}
