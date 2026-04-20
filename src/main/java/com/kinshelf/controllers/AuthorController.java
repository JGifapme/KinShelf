package com.kinshelf.controllers;

import com.kinshelf.dto.author.AuthorCreateDTO;
import com.kinshelf.dto.author.AuthorResponseDTO;
import com.kinshelf.services.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping
    public ResponseEntity<AuthorResponseDTO> create(@Valid @RequestBody AuthorCreateDTO dto) {
        return ResponseEntity.ok(authorService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<AuthorResponseDTO>> getAll() {
        return ResponseEntity.ok(authorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(authorService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody AuthorCreateDTO dto
    ) {
        return ResponseEntity.ok(authorService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        authorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
