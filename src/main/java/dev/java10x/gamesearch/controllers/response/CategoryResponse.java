package dev.java10x.gamesearch.controllers.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "Response payload containing category information")
public record CategoryResponse(
        @Schema(description = "Category unique identifier", example = "1")
        Long id,

        @Schema(description = "Category name", example = "Action")
        String name
) {
}