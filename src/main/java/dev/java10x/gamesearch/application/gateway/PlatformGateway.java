package dev.java10x.gamesearch.application.gateway;

import dev.java10x.gamesearch.domain.model.Platform;

import java.util.List;
import java.util.Optional;

public interface PlatformGateway {

    Platform save(Platform platform);

    List<Platform> findAll();

    Optional<Platform> findById(Long id);

    void deleteById(Long id);
}
