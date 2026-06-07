package dev.java10x.gamesearch.interfaceadapter.rest.docs;

import dev.java10x.gamesearch.interfaceadapter.rest.dto.LoginRequest;
import dev.java10x.gamesearch.interfaceadapter.rest.dto.LoginResponse;
import dev.java10x.gamesearch.interfaceadapter.rest.dto.UserRequest;
import dev.java10x.gamesearch.interfaceadapter.rest.dto.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthControllerDocs {

    @Operation(summary = "Register a new user")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "409", description = "User already exists"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<UserResponse> register(@Valid @RequestBody UserRequest request);

    @Operation(summary = "Login")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request);
}
