package com.kinshelf.controllers;

import com.kinshelf.dto.genre.GenreCreateDTO;
import com.kinshelf.dto.genre.GenreResponseDTO;
import com.kinshelf.dto.genre.GenreWithBooksDTO;
import com.kinshelf.services.GenreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")// seul les utilisateurs authentifié/connecté peuvent utiliser cet endpoints
public class GenreController {

    private final GenreService genreService;

    @PostMapping
    public ResponseEntity<GenreResponseDTO> create(@Valid @RequestBody GenreCreateDTO dto) {
        return ResponseEntity.ok(genreService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<GenreResponseDTO>> getAll() {
        return ResponseEntity.ok(genreService.findAll());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<GenreWithBooksDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(genreService.findById(id));
    }

    @GetMapping("/{slug}")
    public ResponseEntity<GenreWithBooksDTO> getBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(genreService.findBySlug(slug));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") // seulement les admins sont autorisé à utiliser cet endpoint
    public ResponseEntity<GenreResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody GenreCreateDTO dto
    ) {
        return ResponseEntity.ok(genreService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") // seulement les admins sont autorisé à utiliser cet endpoint
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        genreService.delete(id);
        return ResponseEntity.noContent().build();
        //vérifier que ça supprime les livres qui ont uniquement ce genre associé ? Ou que ca ne supprime pas
    }
}
