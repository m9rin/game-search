package dev.java10x.gamesearch.application.usecase.auth;

import dev.java10x.gamesearch.application.gateway.PasswordMatcherGateway;
import dev.java10x.gamesearch.application.gateway.TokenGateway;
import dev.java10x.gamesearch.application.gateway.UserGateway;
import dev.java10x.gamesearch.domain.exception.InvalidCredentialsException;
import dev.java10x.gamesearch.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUseCase {

    private final UserGateway userGateway;
    private final PasswordMatcherGateway passwordMatcherGateway;
    private final TokenGateway tokenGateway;

    public String login(LoginCommand command) {
        User user = userGateway.findByEmail(command.email())
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordMatcherGateway.matches(command.password(), user.password())) {
            throw new InvalidCredentialsException();
        }

        return tokenGateway.generateToken(user);
    }
}
