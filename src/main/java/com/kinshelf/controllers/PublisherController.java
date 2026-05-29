package com.kinshelf.controllers;

import com.kinshelf.dto.publisher.PublisherCreateDTO;
import com.kinshelf.dto.publisher.PublisherResponseDTO;
import com.kinshelf.dto.publisher.PublisherWithBooksDTO;
import com.kinshelf.services.PublisherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/// Controleur qui permet de créer, récupérer, mettre à jour et supprimer les informations sur un éditeur
@RestController
@RequestMapping("/api/publishers")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")// seul les utilisateurs authentifié/connecté peuvent utiliser cet endpoints
public class PublisherController {

    private final PublisherService publisherService;

    /// Endpoint qui permet de créer un éditeur
    @PostMapping
    public ResponseEntity<PublisherResponseDTO> create(@Valid @RequestBody PublisherCreateDTO dto) {
        return ResponseEntity.ok(publisherService.create(dto));
    }

    /// Endpoint qui permet de récupérer la liste de tous les éditeurs dans la base de donnée
    @GetMapping
    public ResponseEntity<List<PublisherResponseDTO>> getAll() {
        return ResponseEntity.ok(publisherService.findAll());
    }

    /// Endpoint qui permet de récupérer les informations d'un éditeur et la liste de ses livres grâce à son id
    @GetMapping("/id/{id}")
    public ResponseEntity<PublisherWithBooksDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(publisherService.findById(id));
    }

    /// Endpoint qui permet de récupérer les informations d'un éditeur et la liste de ses livres grâce à son slug
    @GetMapping("/{slug}")
    public ResponseEntity<PublisherResponseDTO> getBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(publisherService.findBySlug(slug));
    }

    /// Endpoint qui permet de mettre à jour les informations d'un éditeur (admin seulement)
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") // seulement les admins sont autorisé à utiliser cet endpoint
    public ResponseEntity<PublisherResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody PublisherCreateDTO dto
    ) {
        return ResponseEntity.ok(publisherService.update(id, dto));
    }

    /// Endpoint qui permet de supprimer un éditeur de la base de données (admin seulement)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") // seulement les admins sont autorisé à utiliser cet endpoint
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        publisherService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
