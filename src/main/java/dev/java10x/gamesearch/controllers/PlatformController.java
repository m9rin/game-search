package dev.java10x.gamesearch.controllers;

import dev.java10x.gamesearch.controllers.request.PlatformRequest;
import dev.java10x.gamesearch.controllers.response.PlatformResponse;
import dev.java10x.gamesearch.entities.Platform;
import dev.java10x.gamesearch.mapper.PlatformMapper;
import dev.java10x.gamesearch.services.PlatformService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping("/gamesearch/platform")
@RequiredArgsConstructor
@Tag(name = "Platform", description = "Endpoints for managing game platforms")
public class PlatformController {

    private final PlatformService platformService;

    @PostMapping
    @Operation(
            summary = "Create a new platform",
            description = "Creates a new game platform and returns the created resource.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Platform created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PlatformResponse.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid request body (validation error)", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    public ResponseEntity<PlatformResponse> save(@Valid @RequestBody PlatformRequest request) {
        Platform platform = PlatformMapper.toPlatform(request);
        Platform saved = platformService.save(platform);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(PlatformMapper.toPlatformResponse(saved));
    }

    @GetMapping
    @Operation(
            summary = "List all platforms",
            description = "Returns a list of all available game platforms.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Platforms retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PlatformResponse.class)
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    public ResponseEntity<List<PlatformResponse>> getAll() {
        return ResponseEntity.ok(
                platformService.getAll()
                        .stream()
                        .map(PlatformMapper::toPlatformResponse)
                        .toList()
        );
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get platform by id",
            description = "Retrieves a platform by its unique identifier.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Platform found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PlatformResponse.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Platform not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    public ResponseEntity<PlatformResponse> getById(
            @Parameter(description = "Platform id", required = true, example = "1")
            @PathVariable Long id
    ) {
        return platformService.getById(id)
                .map(platform -> ResponseEntity.ok(PlatformMapper.toPlatformResponse(platform)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update a platform",
            description = "Updates a platform identified by id with the provided data.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Platform updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PlatformResponse.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid request body (validation error)", content = @Content),
            @ApiResponse(responseCode = "404", description = "Platform not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    public ResponseEntity<PlatformResponse> update(
            @Parameter(description = "Platform id", required = true, example = "1")
            @PathVariable Long id,
            @Valid @RequestBody PlatformRequest request
    ) {
        return platformService.update(id, PlatformMapper.toPlatform(request))
                .map(platform -> ResponseEntity.ok(PlatformMapper.toPlatformResponse(platform)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a platform",
            description = "Deletes a platform identified by id.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Platform deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Platform not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "Platform id", required = true, example = "1")
            @PathVariable Long id
    ) {
        Optional<Platform> optPlatform = platformService.getById(id);
        if (optPlatform.isPresent()) {
            platformService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.notFound().build();
    }
}