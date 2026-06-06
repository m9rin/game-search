package dev.java10x.gamesearch.application.usecase.platform;

import dev.java10x.gamesearch.application.gateway.PlatformGateway;
import dev.java10x.gamesearch.domain.model.Platform;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class PlatformUseCaseTest {

    private static class InMemoryPlatformGateway implements PlatformGateway {

        private Long sequence = 0L;
        private final List<Platform> platforms = new ArrayList<>();

        @Override
        public Platform save(Platform platform) {
            if (platform.id() == null) {
                sequence++;
                Platform created = new Platform(sequence, platform.name());
                platforms.add(created);
                return created;
            }
            platforms.removeIf(existing -> existing.id().equals(platform.id()));
            platforms.add(platform);
            return platform;
        }

        @Override
        public List<Platform> findAll() {
            return List.copyOf(platforms);
        }

        @Override
        public Optional<Platform> findById(Long id) {
            return platforms.stream()
                    .filter(platform -> platform.id().equals(id))
                    .findFirst();
        }

        @Override
        public void deleteById(Long id) {
            platforms.removeIf(platform -> platform.id().equals(id));
        }
    }

    @Test
    void shouldCreatePlatform() {

        PlatformGateway gateway = new InMemoryPlatformGateway();
        PlatformUseCase useCase = new PlatformUseCase(gateway);

        Platform created = useCase.create("Windows");

        assertThat(created.id()).isNotNull();
        assertThat(created.name()).isEqualTo("Windows");
    }

    @Test
    void shouldListPlatforms() {

        PlatformGateway gateway = new InMemoryPlatformGateway();
        PlatformUseCase useCase = new PlatformUseCase(gateway);

        useCase.create("Windows");
        useCase.create("Playstation 5");

        List<Platform> platforms = useCase.findAll();

        assertThat(platforms).hasSize(2);
        assertThat(platforms)
                .extracting(Platform::name)
                .containsExactly("Windows", "Playstation 5");
    }

    @Test
    void shouldFindPlatformById() {

        PlatformGateway gateway = new InMemoryPlatformGateway();
        PlatformUseCase useCase = new PlatformUseCase(gateway);

        Platform created = useCase.create("Windows");

        Optional<Platform> found = useCase.findById(created.id());

        assertThat(found).isPresent();
        assertThat(found.get().id()).isEqualTo(created.id());
        assertThat(found.get().name()).isEqualTo("Windows");
    }

    @Test
    void shouldUpdateExistingPlatform() {

        PlatformGateway gateway = new InMemoryPlatformGateway();
        PlatformUseCase useCase = new PlatformUseCase(gateway);

        Platform created = useCase.create("Windows");

        Optional<Platform> updated = useCase.update(created.id(), "Playstation 5");

        assertThat(updated).isPresent();
        assertThat(updated.get().id()).isEqualTo(created.id());
        assertThat(updated.get().name()).isEqualTo("Playstation 5");
    }

    @Test
    void shouldReturnEmptyWhenUpdatingMissingPlatform() {

        PlatformGateway gateway = new InMemoryPlatformGateway();
        PlatformUseCase useCase = new PlatformUseCase(gateway);

        Optional<Platform> updated = useCase.update(100L, "Playstation 5");

        assertThat(updated).isEmpty();
    }

    @Test
    void shouldDeleteExistingPlatform() {

        PlatformGateway gateway = new InMemoryPlatformGateway();
        PlatformUseCase useCase = new PlatformUseCase(gateway);

        Platform created = useCase.create("X-Box");

        boolean deleted = useCase.delete(created.id());

        assertThat(deleted).isTrue();
        assertThat(useCase.findById(created.id())).isEmpty();
    }

    @Test
    void shouldReturnFalseWhenDeletingMissingPlatform() {

        PlatformGateway gateway = new InMemoryPlatformGateway();
        PlatformUseCase useCase = new PlatformUseCase(gateway);

        boolean deleted = useCase.delete(100L);

        assertThat(deleted).isFalse();
    }
}
