package com.kinshelf.controllers;

import com.kinshelf.dto.book.BookCreateDTO;
import com.kinshelf.dto.book.BookResponseDTO;
import com.kinshelf.services.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    //n'importe quel user identifié
    @PostMapping
    public ResponseEntity<BookResponseDTO> create(@Valid @RequestBody BookCreateDTO dto) {
        return ResponseEntity.ok(bookService.create(dto));
    }

    //n'importe quel user identifié
    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> getAll() {
        //Ajouter la possibilité de mettre des filtres : lu, possédé, en fonction de la note, de l'auteur, du titre,
        return ResponseEntity.ok(bookService.findAll());
    }

    //n'importe quel user identifié
    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getById(@PathVariable Long id) {
        //retourner les infos de bookUser pour tous les membres pour ce livre :
        //qui le possède, la note moyenne, les commentaires...
        return ResponseEntity.ok(bookService.findById(id));
    }

    //juste les admins
    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody BookCreateDTO dto
    ) {
        return ResponseEntity.ok(bookService.update(id, dto));
    }

    //juste les admins
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }


    //Gestion des relations Book <-> User

    //@PatchMapping("/{bookId}/status") permet de mettre si on a lu un livre,
    // si on le possède, la note, le commentaire,
    // seulement le permettre pour l'utilisateur identifié, récupérer son id via spring security

    //

}
