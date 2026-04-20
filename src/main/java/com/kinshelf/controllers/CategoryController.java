package com.kinshelf.controllers;

import com.kinshelf.dto.category.CategoryCreateDTO;
import com.kinshelf.dto.category.CategoryResponseDTO;
import com.kinshelf.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> create(@Valid @RequestBody CategoryCreateDTO dto) {
        return ResponseEntity.ok(categoryService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAll() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody CategoryCreateDTO dto
    ) {
        return ResponseEntity.ok(categoryService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
