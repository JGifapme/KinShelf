package com.kinshelf.controllers;

import com.kinshelf.dto.series.SeriesCreateDTO;
import com.kinshelf.dto.series.SeriesResponseDTO;
import com.kinshelf.dto.series.SeriesWithBooksDTO;
import com.kinshelf.services.SeriesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/// Controleur qui permet de créer, récupérer, mettre à jour et supprimer les informations sur une série
@RestController
@RequestMapping("/api/series")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")// seul les utilisateurs authentifié/connecté peuvent utiliser cet endpoints
public class SeriesController {

    private final SeriesService seriesService;

    /// Endpoint qui permet de créer une série
    @PostMapping
    public ResponseEntity<SeriesResponseDTO> create(@Valid @RequestBody SeriesCreateDTO dto) {
        return ResponseEntity.ok(seriesService.create(dto));
    }

    /// Endpoint qui permet de récupérer la liste de toutes les séries dans la base de donnée
    @GetMapping
    public ResponseEntity<List<SeriesResponseDTO>> getAll() {
        return ResponseEntity.ok(seriesService.findAll());
    }

    /// Endpoint qui permet de récupérer une série et la liste des livres associés grâce à son id
    @GetMapping("/id/{id}")
    public ResponseEntity<SeriesWithBooksDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(seriesService.findById(id));
    }

    /// Endpoint qui permet de récupérer une série et la liste des livres associés grâce à son slug
    @GetMapping("/{slug}")
    public ResponseEntity<SeriesWithBooksDTO> getBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(seriesService.findBySlug(slug));
    }

    /// Endpoint qui permet de mettre à jour les informations d'une série (nom, status (finie, en cours))
    @PatchMapping("/{id}")
    public ResponseEntity<SeriesResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody SeriesCreateDTO dto
    ) {
        return ResponseEntity.ok(seriesService.update(id, dto));
    }

    /// Endpoint qui permet de supprimer une série (admin seulement)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") // seulement les admins sont autorisé à utiliser cet endpoint
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        seriesService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
