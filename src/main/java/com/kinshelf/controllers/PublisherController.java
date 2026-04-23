package com.kinshelf.controllers;

import com.kinshelf.dto.publisher.PublisherCreateDTO;
import com.kinshelf.dto.publisher.PublisherResponseDTO;
import com.kinshelf.services.PublisherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publishers")
@RequiredArgsConstructor
public class PublisherController {

    private final PublisherService publisherService;

    //n'importe quel user identifié
    @PostMapping
    public ResponseEntity<PublisherResponseDTO> create(@Valid @RequestBody PublisherCreateDTO dto) {
        return ResponseEntity.ok(publisherService.create(dto));
    }

    //n'importe quel user identifié
    @GetMapping
    public ResponseEntity<List<PublisherResponseDTO>> getAll() {
        return ResponseEntity.ok(publisherService.findAll());
    }

    //n'importe quel user identifié
    @GetMapping("/{id}")
    public ResponseEntity<PublisherResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(publisherService.findById(id));
        //rajouter la liste des livres
    }

    //admin seulement
    @PutMapping("/{id}")
    public ResponseEntity<PublisherResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody PublisherCreateDTO dto
    ) {
        return ResponseEntity.ok(publisherService.update(id, dto));
    }

    //admin seulement
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        publisherService.delete(id);
        return ResponseEntity.noContent().build();
        //supprimer les livres affiliés
    }
}
