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
/// Controleur qui permet de créer, récupérer, mettre à jour et supprimer les informations sur un genre
/// (fantasy, fiction, ..). Dans les faits seuls les endpoints GET sont utilisés, les genres étant déjà définis.
/// POST/PATCH/DELETE seront peut-être implémenter dans la page admin
@RestController
@RequestMapping("/api/genres")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")// seul les utilisateurs authentifié/connecté peuvent utiliser cet endpoints
public class GenreController {

    private final GenreService genreService;

    /// Endpoint qui permet de créer un genre, non utilisée dans le front
    @PostMapping
    public ResponseEntity<GenreResponseDTO> create(@Valid @RequestBody GenreCreateDTO dto) {
        return ResponseEntity.ok(genreService.create(dto));
    }

    /// Endpoint qui permet de récupérer la liste de tous les genres dans la base de donnée
    @GetMapping
    public ResponseEntity<List<GenreResponseDTO>> getAll() {
        return ResponseEntity.ok(genreService.findAll());
    }

    /// Endpoint qui permet de récupérer les informations d'un genre et la liste des livres associés grâce à son id
    @GetMapping("/id/{id}")
    public ResponseEntity<GenreWithBooksDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(genreService.findById(id));
    }

    /// Endpoint qui permet de récupérer les informations d'un genre et la liste des livres associés grâce à son slug
    @GetMapping("/{slug}")
    public ResponseEntity<GenreResponseDTO> getBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(genreService.findBySlug(slug));
    }

    /// Endpoint qui permet de mettre à jour les informations d'un genre (admin seulement), non utilisée dans le front
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") // seulement les admins sont autorisé à utiliser cet endpoint
    public ResponseEntity<GenreResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody GenreCreateDTO dto
    ) {
        return ResponseEntity.ok(genreService.update(id, dto));
    }

    /// Endpoint qui permet de supprimer un genre (admin seulement), non utilisée dans le front
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") // seulement les admins sont autorisé à utiliser cet endpoint
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        genreService.delete(id);
        return ResponseEntity.noContent().build();
        //vérifier que ça supprime les livres qui ont uniquement ce genre associé ? Ou que ca ne supprime pas
    }
}
