package com.kinshelf.controllers;

import com.kinshelf.dto.series.SeriesCreateDTO;
import com.kinshelf.dto.series.SeriesResponseDTO;
import com.kinshelf.services.SeriesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/series")
@RequiredArgsConstructor
public class SeriesController {

    private final SeriesService seriesService;

    //n'importe quel user identifié
    @PostMapping
    public ResponseEntity<SeriesResponseDTO> create(@Valid @RequestBody SeriesCreateDTO dto) {
        return ResponseEntity.ok(seriesService.create(dto));
    }

    //n'importe quel user identifié
    @GetMapping
    public ResponseEntity<List<SeriesResponseDTO>> getAll() {
        return ResponseEntity.ok(seriesService.findAll());
    }

    //n'importe quel user identifié
    @GetMapping("/{id}")
    public ResponseEntity<SeriesResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(seriesService.findById(id));
        //voir les livres de la série
    }

    //admin suelement
    @PutMapping("/{id}")
    public ResponseEntity<SeriesResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody SeriesCreateDTO dto
    ) {
        return ResponseEntity.ok(seriesService.update(id, dto));
    }

    //admin seulement
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        seriesService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
