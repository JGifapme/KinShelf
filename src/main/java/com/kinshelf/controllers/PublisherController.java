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

@RestController
@RequestMapping("/api/publishers")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")// seul les utilisateurs authentifié/connecté peuvent utiliser cet endpoints
public class PublisherController {

    private final PublisherService publisherService;

    @PostMapping
    public ResponseEntity<PublisherResponseDTO> create(@Valid @RequestBody PublisherCreateDTO dto) {
        return ResponseEntity.ok(publisherService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<PublisherResponseDTO>> getAll() {
        return ResponseEntity.ok(publisherService.findAll());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<PublisherWithBooksDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(publisherService.findById(id));
    }

    @GetMapping("/{slug}")
    public ResponseEntity<PublisherWithBooksDTO> getBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(publisherService.findBySlug(slug));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") // seulement les admins sont autorisé à utiliser cet endpoint
    public ResponseEntity<PublisherResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody PublisherCreateDTO dto
    ) {
        return ResponseEntity.ok(publisherService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") // seulement les admins sont autorisé à utiliser cet endpoint
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        publisherService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
