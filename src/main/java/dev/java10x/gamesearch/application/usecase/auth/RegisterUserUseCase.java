package dev.java10x.gamesearch.application.usecase.auth;

import dev.java10x.gamesearch.application.gateway.PasswordEncoderGateway;
import dev.java10x.gamesearch.application.gateway.UserGateway;
import dev.java10x.gamesearch.domain.exception.UserAlreadyExistsException;
import dev.java10x.gamesearch.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterUserUseCase {

    private final UserGateway userGateway;
    private final PasswordEncoderGateway passwordEncoderGateway;

    public User register(RegisterUserCommand command) {
        userGateway.findByEmail(command.email())
                .ifPresent(user -> {
                    throw new UserAlreadyExistsException(command.email());
                });

        User user = new User(
                null,
                command.name(),
                command.email(),
                passwordEncoderGateway.encode(command.password())
        );

        return userGateway.save(user);
    }
}
