package dev.java10x.gamesearch.interfaceadapter.rest.controller;

import dev.java10x.gamesearch.application.usecase.platform.PlatformUseCase;
import dev.java10x.gamesearch.interfaceadapter.rest.docs.PlatformControllerDocs;
import dev.java10x.gamesearch.interfaceadapter.rest.dto.PlatformRequest;
import dev.java10x.gamesearch.interfaceadapter.rest.dto.PlatformResponse;
import dev.java10x.gamesearch.interfaceadapter.rest.mapper.PlatformRestMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gamesearch/platform")
@RequiredArgsConstructor
public class PlatformController implements PlatformControllerDocs {

    private final PlatformUseCase platformUseCase;


    @Override
    @PostMapping
    public ResponseEntity<PlatformResponse> save(@Valid @RequestBody  PlatformRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(PlatformRestMapper.toResponse(platformUseCase.create(request.name())));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<PlatformResponse>> getAll() {
        return ResponseEntity.ok(platformUseCase.findAll()
                .stream()
                .map(PlatformRestMapper::toResponse)
                .toList());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<PlatformResponse> getById(@PathVariable Long id) {
        return platformUseCase.findById(id)
                .map(platform -> ResponseEntity.ok(PlatformRestMapper.toResponse(platform)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<PlatformResponse> update(@PathVariable Long id, @Valid @RequestBody PlatformRequest request) {
        return platformUseCase.update(id, request.name())
                .map(platform -> ResponseEntity.ok(PlatformRestMapper.toResponse(platform)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (platformUseCase.delete(id)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.notFound().build();
    }
}
