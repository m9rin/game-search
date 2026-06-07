package dev.java10x.gamesearch.interfaceadapter.rest.controller;

import dev.java10x.gamesearch.application.usecase.game.GameUseCase;
import dev.java10x.gamesearch.interfaceadapter.rest.docs.GameControllerDocs;
import dev.java10x.gamesearch.interfaceadapter.rest.dto.GameRequest;
import dev.java10x.gamesearch.interfaceadapter.rest.dto.GameResponse;
import dev.java10x.gamesearch.interfaceadapter.rest.mapper.GameRestMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gamesearch/game")
@RequiredArgsConstructor
public class GameController implements GameControllerDocs {

    private final GameUseCase gameUseCase;

    @Override
    @PostMapping
    public ResponseEntity<GameResponse> save(@Valid @RequestBody GameRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GameRestMapper.toResponse(
                        gameUseCase.create(GameRestMapper.toCreateCommand(request))
                ));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<GameResponse>> getAll() {
        return ResponseEntity.ok(
                gameUseCase.findAll()
                        .stream()
                        .map(GameRestMapper::toResponse)
                        .toList()
        );
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<GameResponse> getById(@PathVariable Long id) {
        return gameUseCase.findById(id)
                .map(game -> ResponseEntity.ok(GameRestMapper.toResponse(game)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @GetMapping("/search/category")
    public ResponseEntity<List<GameResponse>> getByCategory(@RequestParam Long category) {
        return ResponseEntity.ok(
                gameUseCase.findByCategory(category)
                        .stream()
                        .map(GameRestMapper::toResponse)
                        .toList()
        );
    }

    @Override
    @GetMapping("/search/platform")
    public ResponseEntity<List<GameResponse>> getByPlatform(@RequestParam Long platform) {
        return ResponseEntity.ok(
                gameUseCase.findByPlatform(platform)
                        .stream()
                        .map(GameRestMapper::toResponse)
                        .toList()
        );
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<GameResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody GameRequest request
    ) {
        return gameUseCase.update(id, GameRestMapper.toUpdateCommand(request))
                .map(game -> ResponseEntity.ok(GameRestMapper.toResponse(game)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (gameUseCase.delete(id)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.notFound().build();
    }
}
