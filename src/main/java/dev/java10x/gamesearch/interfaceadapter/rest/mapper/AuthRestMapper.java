package dev.java10x.gamesearch.interfaceadapter.rest.mapper;

import dev.java10x.gamesearch.application.usecase.auth.LoginCommand;
import dev.java10x.gamesearch.application.usecase.auth.RegisterUserCommand;
import dev.java10x.gamesearch.domain.model.User;
import dev.java10x.gamesearch.interfaceadapter.rest.dto.LoginRequest;
import dev.java10x.gamesearch.interfaceadapter.rest.dto.UserRequest;
import dev.java10x.gamesearch.interfaceadapter.rest.dto.UserResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AuthRestMapper {

    public static RegisterUserCommand toRegisterCommand(UserRequest request) {
        return new RegisterUserCommand(
                request.name(),
                request.email(),
                request.password()
        );
    }

    public static LoginCommand toLoginCommand(LoginRequest request) {
        return new LoginCommand(
                request.email(),
                request.password()
        );
    }

    public static UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.id())
                .name(user.name())
                .email(user.email())
                .build();
    }
}
