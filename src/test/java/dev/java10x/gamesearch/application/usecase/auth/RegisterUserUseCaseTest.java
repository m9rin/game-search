package dev.java10x.gamesearch.application.usecase.auth;

import dev.java10x.gamesearch.application.gateway.PasswordEncoderGateway;
import dev.java10x.gamesearch.application.gateway.PasswordMatcherGateway;
import dev.java10x.gamesearch.application.gateway.TokenGateway;
import dev.java10x.gamesearch.application.gateway.UserGateway;
import dev.java10x.gamesearch.domain.exception.InvalidCredentialsException;
import dev.java10x.gamesearch.domain.exception.UserAlreadyExistsException;
import dev.java10x.gamesearch.domain.model.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class RegisterUserUseCaseTest {

    @Test
    void shouldRegisterUserWithEncodedPassword() {
        UserGateway userGateway = new InMemoryUserGateway();
        PasswordEncoderGateway passwordEncoderGateway = new FakePasswordEncoderGateway();

        RegisterUserUseCase useCase = new RegisterUserUseCase(userGateway, passwordEncoderGateway);

        User registered = useCase.register(new RegisterUserCommand(
                "Marin",
                "marin@email.com",
                "123456"
        ));

        assertThat(registered.id()).isNotNull();
        assertThat(registered.name()).isEqualTo("Marin");
        assertThat(registered.email()).isEqualTo("marin@email.com");
        assertThat(registered.password()).isEqualTo("encoded-123456");
    }

    @Test
    void shouldThrowExceptionWhenRegisteringDuplicatedEmail() {
        UserGateway userGateway = new InMemoryUserGateway();
        PasswordEncoderGateway passwordEncoderGateway = new FakePasswordEncoderGateway();

        RegisterUserUseCase useCase = new RegisterUserUseCase(userGateway, passwordEncoderGateway);

        useCase.register(new RegisterUserCommand(
                "Marin",
                "marin@email.com",
                "123456"
        ));

        assertThatThrownBy(() -> useCase.register(new RegisterUserCommand(
                "Outro Marin",
                "marin@email.com",
                "abcdef"
        )))
                .isInstanceOf(UserAlreadyExistsException.class)
                .hasMessage("User already exists with email: marin@email.com");
    }

    private static class InMemoryUserGateway implements UserGateway {

        private Long sequence = 0L;
        private final List<User> users = new ArrayList<>();

        @Override
        public User save(User user) {
            sequence++;

            User created = new User(
                    sequence,
                    user.name(),
                    user.email(),
                    user.password()
            );

            users.add(created);
            return created;
        }

        @Override
        public Optional<User> findByEmail(String email) {
            return users.stream()
                    .filter(user -> user.email().equals(email))
                    .findFirst();
        }
    }

    private static class FakePasswordEncoderGateway implements PasswordEncoderGateway {

        @Override
        public String encode(String rawPassword) {
            return "encoded-" + rawPassword;
        }
    }
}
