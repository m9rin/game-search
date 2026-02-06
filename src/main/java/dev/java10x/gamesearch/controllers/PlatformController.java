package dev.java10x.gamesearch.controllers;

import dev.java10x.gamesearch.controllers.request.PlatformRequest;
import dev.java10x.gamesearch.controllers.response.PlatformResponse;
import dev.java10x.gamesearch.entities.Platform;
import dev.java10x.gamesearch.mapper.PlatformMapper;
import dev.java10x.gamesearch.services.PlatformService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/gamesearch/platform")
@RequiredArgsConstructor
public class PlatformController {

    private final PlatformService platformService;

    @PostMapping
    public ResponseEntity<PlatformResponse> save(@Valid @RequestBody PlatformRequest request) {
        Platform platform = PlatformMapper.toPlatform(request);
        Platform saved = platformService.save(platform);
        return ResponseEntity.status(HttpStatus.CREATED).body(PlatformMapper.toPlatformResponse(saved));
    }

    @GetMapping
    public ResponseEntity<List<PlatformResponse>> getAll() {
        return ResponseEntity.ok(platformService.getAll()
                .stream()
                .map(PlatformMapper::toPlatformResponse)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlatformResponse> getById(@PathVariable Long id) {
        return platformService.getById(id)
                .map(platform -> ResponseEntity.ok(PlatformMapper.toPlatformResponse(platform)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlatformResponse> update(@PathVariable Long id, @Valid @RequestBody PlatformRequest request) {
        return platformService.update(id, PlatformMapper.toPlatform(request))
                .map(platform -> ResponseEntity.ok(PlatformMapper.toPlatformResponse(platform)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<Platform> optPlatform = platformService.getById(id);
        if (optPlatform.isPresent()) {
            platformService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.notFound().build();
    }
}
