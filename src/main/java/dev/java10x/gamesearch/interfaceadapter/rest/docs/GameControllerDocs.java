package dev.java10x.gamesearch.interfaceadapter.rest.docs;

import dev.java10x.gamesearch.interfaceadapter.rest.dto.GameRequest;
import dev.java10x.gamesearch.interfaceadapter.rest.dto.GameResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface GameControllerDocs {
    @Operation(summary = "Create a new game")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Game created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<GameResponse> save(@Valid @RequestBody GameRequest request);

    @Operation(summary = "List all games")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Games retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<List<GameResponse>> getAll();

    @Operation(summary = "Get game by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Game found"),
            @ApiResponse(responseCode = "404", description = "Game not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<GameResponse> getById(
            @Parameter(description = "Game id", required = true, example = "1")
            @PathVariable Long id
    );

    @Operation(summary = "Search games by category")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Games retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid query parameter"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<List<GameResponse>> getByCategory(
            @Parameter(description = "Category id to filter by", required = true, example = "1")
            @RequestParam Long category
    );

    @Operation(summary = "Search games by platform")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Games retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid query parameter"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<List<GameResponse>> getByPlatform(
            @Parameter(description = "Platform id to filter by", required = true, example = "1")
            @RequestParam Long platform
    );

    @Operation(summary = "Update a game")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Game updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "404", description = "Game not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<GameResponse> update(
            @Parameter(description = "Game id", required = true, example = "1")
            @PathVariable Long id,
            @Valid @RequestBody GameRequest request
    );

    @Operation(summary = "Delete a game")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Game deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Game not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<Void> delete(
            @Parameter(description = "Game id", required = true, example = "1")
            @PathVariable Long id
    );
}
