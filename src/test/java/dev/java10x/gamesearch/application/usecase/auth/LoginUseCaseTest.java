package dev.java10x.gamesearch.application.usecase.auth;

import dev.java10x.gamesearch.application.gateway.PasswordMatcherGateway;
import dev.java10x.gamesearch.application.gateway.TokenGateway;
import dev.java10x.gamesearch.application.gateway.UserGateway;
import dev.java10x.gamesearch.domain.exception.InvalidCredentialsException;
import dev.java10x.gamesearch.domain.model.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class LoginUseCaseTest {

    @Test
    void shouldLoginAndReturnToken() {
        UserGateway userGateway = new InMemoryUserGateway();
        PasswordMatcherGateway passwordMatcherGateway = new FakePasswordMatcherGateway();
        TokenGateway tokenGateway = new FakeTokenGateway();

        userGateway.save(new User(null, "Marin", "marin@email.com", "encoded-123456"));

        LoginUseCase useCase = new LoginUseCase(userGateway, passwordMatcherGateway, tokenGateway);

        String token = useCase.login(new LoginCommand("marin@email.com", "123456"));

        assertThat(token).isEqualTo("token-for-marin@email.com");
    }

    @Test
    void shouldThrowExceptionWhenEmailDoesNotExist() {
        UserGateway userGateway = new InMemoryUserGateway();
        PasswordMatcherGateway passwordMatcherGateway = new FakePasswordMatcherGateway();
        TokenGateway tokenGateway = new FakeTokenGateway();

        LoginUseCase useCase = new LoginUseCase(userGateway, passwordMatcherGateway, tokenGateway);

        assertThatThrownBy(() -> useCase.login(new LoginCommand(
                "missing@email.com",
                "123456"
        )))
                .isInstanceOf(InvalidCredentialsException.class)
                .hasMessage("Email or password invalid.");
    }

    @Test
    void shouldThrowExceptionWhenPasswordIsInvalid() {
        UserGateway userGateway = new InMemoryUserGateway();
        PasswordMatcherGateway passwordMatcherGateway = new FakePasswordMatcherGateway();
        TokenGateway tokenGateway = new FakeTokenGateway();

        userGateway.save(new User(null, "Marin", "marin@email.com", "encoded-123456"));

        LoginUseCase useCase = new LoginUseCase(userGateway, passwordMatcherGateway, tokenGateway);

        assertThatThrownBy(() -> useCase.login(new LoginCommand(
                "marin@email.com",
                "wrong-password"
        )))
                .isInstanceOf(InvalidCredentialsException.class)
                .hasMessage("Email or password invalid.");
    }

    private static class InMemoryUserGateway implements UserGateway {

        private Long sequence = 0L;
        private final List<User> users = new ArrayList<>();

        @Override
        public User save(User user) {
            sequence++;

            User created = new User(sequence, user.name(), user.email(), user.password());
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

    private static class FakePasswordMatcherGateway implements PasswordMatcherGateway {

        @Override
        public boolean matches(String rawPassword, String encodedPassword) {
            return encodedPassword.equals("encoded-" + rawPassword);
        }
    }

    private static class FakeTokenGateway implements TokenGateway {

        @Override
        public String generateToken(User user) {
            return "token-for-" + user.email();
        }
    }
}
