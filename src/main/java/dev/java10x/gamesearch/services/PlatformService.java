package dev.java10x.gamesearch.services;

import dev.java10x.gamesearch.entities.Platform;
import dev.java10x.gamesearch.repositories.PlatformRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlatformService {

    private final PlatformRepository repository;

    public Platform save(Platform platform) {
        return repository.save(platform);
    }

    public List<Platform> getAll() {
        return repository.findAll();
    }

    public Optional<Platform> getById(Long id) {
        return repository.findById(id);
    }

    public Optional<Platform> update(Long id, Platform updatePlatform) {
        Optional<Platform> optPlatform = repository.findById(id);

        if(optPlatform.isPresent()) {
            Platform platform = optPlatform.get();
            platform.setName(updatePlatform.getName());
            repository.save(platform);
            return Optional.of(platform);
        }
        return Optional.empty();
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
