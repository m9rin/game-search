package dev.java10x.gamesearch.interfaceadapter.rest.docs;

import dev.java10x.gamesearch.interfaceadapter.rest.dto.CategoryRequest;
import dev.java10x.gamesearch.interfaceadapter.rest.dto.CategoryResponse;
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

public interface CategoryControllerDocs {

    @Operation(
            summary = "Create a new category",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Category created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<CategoryResponse> save(@Valid @RequestBody CategoryRequest request);

    @Operation(
            summary = "List all categories",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categories retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<List<CategoryResponse>> getAll();

    @Operation(
            summary = "Get category by id",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Category found"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<CategoryResponse> getById(
            @Parameter(description = "Category id", required = true, example = "1")
            @PathVariable Long id
    );

    @Operation(
            summary = "Update a category",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Category updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<CategoryResponse> update(
            @Parameter(description = "Category id", required = true, example = "1")
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequest request
    );

    @Operation(
            summary = "Delete a category",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Category deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<Void> delete(
            @Parameter(description = "Category id", required = true, example = "1")
            @PathVariable Long id
    );
}
