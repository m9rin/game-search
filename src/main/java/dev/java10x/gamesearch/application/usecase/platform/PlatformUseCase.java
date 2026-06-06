package dev.java10x.gamesearch.application.usecase.platform;

import dev.java10x.gamesearch.application.gateway.PlatformGateway;
import dev.java10x.gamesearch.domain.model.Platform;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlatformUseCase {

    private final PlatformGateway platformGateway;

    public Platform create(String name) {
        return platformGateway.save(new Platform(null, name));
    }

    public List<Platform> findAll() {
        return platformGateway.findAll();
    }

    public Optional<Platform> findById(Long id) {
        return platformGateway.findById(id);
    }

    public Optional<Platform> update(Long id, String name) {
        return platformGateway.findById(id)
                .map(platform -> platformGateway.save(new Platform(platform.id(), name)));
    }

    public boolean delete(Long id) {
        if (platformGateway.findById(id).isEmpty()) {
            return false;
        }
        platformGateway.deleteById(id);
        return true;
    }
}
