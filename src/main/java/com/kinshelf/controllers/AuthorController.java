package com.kinshelf.controllers;

import com.kinshelf.dto.author.AuthorCreateDTO;
import com.kinshelf.dto.author.AuthorResponseDTO;
import com.kinshelf.dto.author.AuthorWithBooksDTO;
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

    //n'importe quel user identifié
    @PostMapping
    public ResponseEntity<AuthorResponseDTO> create(@Valid @RequestBody AuthorCreateDTO dto) {
        return ResponseEntity.ok(authorService.create(dto));
    }

    //n'importe quel user identifié
    @GetMapping
    public ResponseEntity<List<AuthorResponseDTO>> getAll() {
        return ResponseEntity.ok(authorService.findAll());
    }

    //n'importe quel user identifié
    @GetMapping("/{id}")
    public ResponseEntity<AuthorWithBooksDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(authorService.findById(id));
    }

    //juste admin
    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody AuthorCreateDTO dto
    ) {
        return ResponseEntity.ok(authorService.update(id, dto));
    }

    //juste admin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        authorService.delete(id);
        return ResponseEntity.noContent().build();
        //vérifier que ça supprime les livres associés
    }
}
