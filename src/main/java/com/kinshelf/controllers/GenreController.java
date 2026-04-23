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

    //n'importe quel user identifié
    @PostMapping
    public ResponseEntity<GenreResponseDTO> create(@Valid @RequestBody GenreCreateDTO dto) {
        return ResponseEntity.ok(genreService.create(dto));
    }

    //n'importe quel user identifié
    @GetMapping
    public ResponseEntity<List<GenreResponseDTO>> getAll() {
        return ResponseEntity.ok(genreService.findAll());
    }

    //n'importe quel user identifié
    @GetMapping("/{id}")
    public ResponseEntity<GenreResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(genreService.findById(id));
        //ajouter les listes des ouvrages
    }

    //juste admins
    @PutMapping("/{id}")
    public ResponseEntity<GenreResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody GenreCreateDTO dto
    ) {
        return ResponseEntity.ok(genreService.update(id, dto));
    }

    //juste admins
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        genreService.delete(id);
        return ResponseEntity.noContent().build();
        //vérifier que ça supprime les livres qui ont uniquement ce genre associé ? Ou que ca ne supprime pas
    }
}
