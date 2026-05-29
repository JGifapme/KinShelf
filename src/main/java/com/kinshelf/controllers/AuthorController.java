package com.kinshelf.controllers;

import com.kinshelf.dto.author.AuthorCreateDTO;
import com.kinshelf.dto.author.AuthorResponseDTO;
import com.kinshelf.dto.author.AuthorWithBooksDTO;
import com.kinshelf.services.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/// Controleur qui permet de créer, récupérer, mettre à jour et supprimer les informations sur un auteur
@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")// seul les utilisateurs authentifié/connecté peuvent utiliser cet endpoints
public class AuthorController {

    private final AuthorService authorService;

    /// Endpoint qui permet de créer un auteur
    @PostMapping
    public ResponseEntity<AuthorResponseDTO> create(@Valid @RequestBody AuthorCreateDTO dto) {
        return ResponseEntity.ok(authorService.create(dto));
    }

    /// Endpoint qui permet de récupérer la liste de tous les auteurs dans la base de donnée
    @GetMapping
    public ResponseEntity<List<AuthorResponseDTO>> getAll() {
        return ResponseEntity.ok(authorService.findAll());
    }

    /// Endpoint qui permet de récupérer les informations d'un auteur et la liste de ses livres grâce à son id
    @GetMapping("/id/{id}")
    public ResponseEntity<AuthorWithBooksDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(authorService.findById(id));
    }

    /// Endpoint qui permet de récupérer les informations d'un auteur et la liste de ses livres grâce à son slug
    @GetMapping("/{slug}")
    public ResponseEntity<AuthorWithBooksDTO> getBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(authorService.findBySlug(slug));
    }

    /// Endpoint qui permet de mettre à jour les informations d'un auteur (admin seulement)
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") // seulement les admins sont autorisé à utiliser cet endpoint
    public ResponseEntity<AuthorResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody AuthorCreateDTO dto
    ) {
        return ResponseEntity.ok(authorService.update(id, dto));
    }

    /// Endpoint qui permet de supprimer un auteur de la base de données (admin seulement)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") // seulement les admins sont autorisé à utiliser cet endpoint
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        authorService.delete(id);
        return ResponseEntity.noContent().build();
        //vérifier que ça supprime la table de jointure associée -> oui
    }
}
