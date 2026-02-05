package dev.java10x.gamesearch.controllers;

import dev.java10x.gamesearch.controllers.request.CategoryRequest;
import dev.java10x.gamesearch.controllers.response.CategoryResponse;
import dev.java10x.gamesearch.entities.Category;
import dev.java10x.gamesearch.entities.Platform;
import dev.java10x.gamesearch.mapper.CategoryMapper;
import dev.java10x.gamesearch.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/gamesearch/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponse> save(@RequestBody CategoryRequest request) {
        Category category = CategoryMapper.toCategory(request);
        Category saved = categoryService.save(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(CategoryMapper.toCategoryResponse(saved));
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAll() {
        return ResponseEntity.ok(categoryService.getAll()
                .stream()
                .map(CategoryMapper::toCategoryResponse)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getById(@PathVariable Long id) {
        return categoryService.getById(id)
                .map(category -> ResponseEntity.ok(CategoryMapper.toCategoryResponse(category)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> update(@PathVariable Long id, @RequestBody CategoryRequest request) {
        return categoryService.update(id, CategoryMapper.toCategory(request))
                .map(category -> ResponseEntity.ok(CategoryMapper.toCategoryResponse(category)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<Category> optCategory = categoryService.getById(id);
        if (optCategory.isPresent()) {
            categoryService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.notFound().build();
    }

}
