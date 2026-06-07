package dev.java10x.gamesearch.interfaceadapter.rest.controller;

import dev.java10x.gamesearch.application.usecase.auth.LoginUseCase;
import dev.java10x.gamesearch.application.usecase.auth.RegisterUserUseCase;
import dev.java10x.gamesearch.interfaceadapter.rest.docs.AuthControllerDocs;
import dev.java10x.gamesearch.interfaceadapter.rest.dto.LoginRequest;
import dev.java10x.gamesearch.interfaceadapter.rest.dto.LoginResponse;
import dev.java10x.gamesearch.interfaceadapter.rest.dto.UserRequest;
import dev.java10x.gamesearch.interfaceadapter.rest.dto.UserResponse;
import dev.java10x.gamesearch.interfaceadapter.rest.mapper.AuthRestMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController implements AuthControllerDocs {

    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUseCase loginUseCase;

    @Override
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(AuthRestMapper.toResponse(
                        registerUserUseCase.register(AuthRestMapper.toRegisterCommand(request))
                ));
    }

    @Override
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        String token = loginUseCase.login(AuthRestMapper.toLoginCommand(request));

        return ResponseEntity.ok(new LoginResponse(token));
    }
}
