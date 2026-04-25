package com.kinshelf.controllers;

import com.kinshelf.dto.category.CategoryCreateDTO;
import com.kinshelf.dto.category.CategoryResponseDTO;
import com.kinshelf.dto.category.CategoryWithBooksDTO;
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

    //n'importe quel user identifié
    @PostMapping
    public ResponseEntity<CategoryResponseDTO> create(@Valid @RequestBody CategoryCreateDTO dto) {
        return ResponseEntity.ok(categoryService.create(dto));
    }

    //n'importe quel user identifié
    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAll() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    //n'importe quel user identifié
    @GetMapping("/{id}")
    public ResponseEntity<CategoryWithBooksDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }

    //juste les admins
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody CategoryCreateDTO dto
    ) {
        return ResponseEntity.ok(categoryService.update(id, dto));
    }

    //juste les admins
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
        //vérifier que ça supprime les livres associés
    }
}
