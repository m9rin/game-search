package dev.java10x.gamesearch.controllers;

import dev.java10x.gamesearch.controllers.request.GameRequest;
import dev.java10x.gamesearch.controllers.response.GameResponse;
import dev.java10x.gamesearch.entities.Game;
import dev.java10x.gamesearch.mapper.GameMapper;
import dev.java10x.gamesearch.services.GameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/gamesearch/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @PostMapping
    public ResponseEntity<GameResponse> save(@Valid @RequestBody GameRequest request) {
        Game game = GameMapper.toGame(request);
        Game saved = gameService.save(game);
        return ResponseEntity.status(HttpStatus.CREATED).body(GameMapper.toGameResponse(saved));
    }

    @GetMapping
    public ResponseEntity<List<GameResponse>> getAll() {
        return ResponseEntity.ok().body(gameService.getAll()
                .stream()
                .map(GameMapper::toGameResponse)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameResponse> getById(@PathVariable Long id) {
        return gameService.getById(id)
                .map(game -> ResponseEntity.ok(GameMapper.toGameResponse(game)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search/category")
    public ResponseEntity<List<GameResponse>> getByCategory(@RequestParam Long category) {
        return ResponseEntity.ok(gameService.getByCategory(category).stream()
                .map(GameMapper::toGameResponse)
                .toList());
    }

    @GetMapping("/search/platform")
    public ResponseEntity<List<GameResponse>> getByPlatform(@RequestParam Long platform) {
        return ResponseEntity.ok(gameService.getByPlatforms(platform).stream()
                .map(GameMapper::toGameResponse)
                .toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<GameResponse> update(@PathVariable Long id, @Valid @RequestBody GameRequest request) {
        return gameService.update(id, GameMapper.toGame(request))
                .map(game -> ResponseEntity.ok(GameMapper.toGameResponse(game)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<Game> optGame = gameService.getById(id);
        if (optGame.isPresent()) {
            gameService.delete(id);
            ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.notFound().build();
    }
}
