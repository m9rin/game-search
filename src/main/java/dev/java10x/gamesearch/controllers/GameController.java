package dev.java10x.gamesearch.controllers;

import dev.java10x.gamesearch.controllers.request.GameRequest;
import dev.java10x.gamesearch.controllers.response.GameResponse;
import dev.java10x.gamesearch.entities.Game;
import dev.java10x.gamesearch.mapper.GameMapper;
import dev.java10x.gamesearch.services.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/gamesearch/game")
@RequiredArgsConstructor
@Tag(name = "Game", description = "Endpoints for managing games and searching by filters")
public class GameController {

    private final GameService gameService;

    @PostMapping
    @Operation(
            summary = "Create a new game",
            description = "Creates a new game and returns the created resource.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Game created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GameResponse.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid request body (validation error)", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    public ResponseEntity<GameResponse> save(@Valid @RequestBody GameRequest request) {
        Game game = GameMapper.toGame(request);
        Game saved = gameService.save(game);
        return ResponseEntity.status(HttpStatus.CREATED).body(GameMapper.toGameResponse(saved));
    }

    @GetMapping
    @Operation(
            summary = "List all games",
            description = "Returns a list of all games.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Games retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GameResponse.class))
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    public ResponseEntity<List<GameResponse>> getAll() {
        return ResponseEntity.ok().body(
                gameService.getAll()
                        .stream()
                        .map(GameMapper::toGameResponse)
                        .toList()
        );
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get game by id",
            description = "Retrieves a game by its unique identifier.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Game found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GameResponse.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Game not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    public ResponseEntity<GameResponse> getById(
            @Parameter(description = "Game id", required = true, example = "1")
            @PathVariable Long id
    ) {
        return gameService.getById(id)
                .map(game -> ResponseEntity.ok(GameMapper.toGameResponse(game)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search/category")
    @Operation(
            summary = "Search games by category",
            description = "Returns a list of games that belong to the given category id.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Games retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GameResponse.class))
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid query parameter", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    public ResponseEntity<List<GameResponse>> getByCategory(
            @Parameter(description = "Category id to filter by", required = true, example = "1")
            @RequestParam Long category
    ) {
        return ResponseEntity.ok(
                gameService.getByCategory(category).stream()
                        .map(GameMapper::toGameResponse)
                        .toList()
        );
    }

    @GetMapping("/search/platform")
    @Operation(
            summary = "Search games by platform",
            description = "Returns a list of games that are available on the given platform id.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Games retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GameResponse.class))
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid query parameter", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    public ResponseEntity<List<GameResponse>> getByPlatform(
            @Parameter(description = "Platform id to filter by", required = true, example = "1")
            @RequestParam Long platform
    ) {
        return ResponseEntity.ok(
                gameService.getByPlatforms(platform).stream()
                        .map(GameMapper::toGameResponse)
                        .toList()
        );
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update a game",
            description = "Updates a game identified by id with the provided data.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Game updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GameResponse.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid request body (validation error)", content = @Content),
            @ApiResponse(responseCode = "404", description = "Game not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    public ResponseEntity<GameResponse> update(
            @Parameter(description = "Game id", required = true, example = "1")
            @PathVariable Long id,
            @Valid @RequestBody GameRequest request
    ) {
        return gameService.update(id, GameMapper.toGame(request))
                .map(game -> ResponseEntity.ok(GameMapper.toGameResponse(game)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a game",
            description = "Deletes a game identified by id.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Game deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Game not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "Game id", required = true, example = "1")
            @PathVariable Long id
    ) {
        Optional<Game> optGame = gameService.getById(id);
        if (optGame.isPresent()) {
            gameService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // <- important: return it
        }
        return ResponseEntity.notFound().build();
    }
}