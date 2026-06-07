package dev.java10x.gamesearch.interfaceadapter.rest.docs;

import dev.java10x.gamesearch.interfaceadapter.rest.dto.PlatformRequest;
import dev.java10x.gamesearch.interfaceadapter.rest.dto.PlatformResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface PlatformControllerDocs {

    @Operation(
            summary = "Create a new platform",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Platform created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<PlatformResponse> save(@Valid @RequestBody PlatformRequest request);

    @Operation(
            summary = "List all platforms",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Platforms retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<List<PlatformResponse>> getAll();

    @Operation(
            summary = "Get platform by id",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Platform found"),
            @ApiResponse(responseCode = "404", description = "Platform not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<PlatformResponse> getById(
            @Parameter(description = "Platform id", required = true, example = "1")
            @PathVariable Long id
    );

    @Operation(
            summary = "Update a platform",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Platform updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "404", description = "Platform not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<PlatformResponse> update(
            @Parameter(description = "Platform id", required = true, example = "1")
            @PathVariable Long id,
            @Valid @RequestBody PlatformRequest request
    );

    @Operation(
            summary = "Delete a platform",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Platform deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Platform not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<Void> delete(
            @Parameter(description = "Platform id", required = true, example = "1")
            @PathVariable Long id
    );
}