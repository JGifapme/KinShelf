package com.kinshelf.controllers;

import com.kinshelf.dto.genre.GenreCreateDTO;
import com.kinshelf.dto.genre.GenreResponseDTO;
import com.kinshelf.services.GenreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
@RequiredArgsConstructor
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
    
    @GetMapping("/{id}")
    public ResponseEntity<GenreResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(genreService.findById(id));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<GenreResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody GenreCreateDTO dto
    ) {
        return ResponseEntity.ok(genreService.update(id, dto));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        genreService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
