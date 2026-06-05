package dev.java10x.gamesearch.interfaceadapter.rest.controller;

import dev.java10x.gamesearch.application.usecase.category.CategoryUseCase;
import dev.java10x.gamesearch.interfaceadapter.rest.docs.CategoryControllerDocs;
import dev.java10x.gamesearch.interfaceadapter.rest.dto.CategoryRequest;
import dev.java10x.gamesearch.interfaceadapter.rest.dto.CategoryResponse;
import dev.java10x.gamesearch.interfaceadapter.rest.mapper.CategoryRestMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gamesearch/category")
@RequiredArgsConstructor
public class CategoryController implements CategoryControllerDocs {

    private final CategoryUseCase useCase;

    @Override
    @PostMapping
    public ResponseEntity<CategoryResponse> save(@Valid @RequestBody CategoryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CategoryRestMapper.toResponse(useCase.create(request.name())));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAll() {
        return ResponseEntity.ok(useCase.findAll()
                .stream()
                .map(CategoryRestMapper::toResponse)
                .toList());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getById(@PathVariable Long id) {
        return useCase.findById(id)
                .map(category -> ResponseEntity.ok(CategoryRestMapper.toResponse(category)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> update(@PathVariable Long id, @Valid @RequestBody CategoryRequest request) {
        return useCase.update(id, request.name())
                .map(category -> ResponseEntity.ok(CategoryRestMapper.toResponse(category)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (useCase.delete(id)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.notFound().build();
    }
}
