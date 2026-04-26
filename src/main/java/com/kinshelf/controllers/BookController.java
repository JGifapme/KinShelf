package com.kinshelf.controllers;

import com.kinshelf.dto.book.BookCreateDTO;
import com.kinshelf.dto.book.BookResponseDTO;
import com.kinshelf.dto.book.BookTitleAndImgDTO;
import com.kinshelf.dto.book.BookWithUsersInputDTO;
import com.kinshelf.dto.bookUser.BookUserCreateDTO;
import com.kinshelf.dto.bookUser.BookUserResponseDTO;
import com.kinshelf.entities.BookUserId;
import com.kinshelf.services.BookService;
import com.kinshelf.services.BookUserService;
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
    private final BookUserService bookUserService;

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
    public ResponseEntity<BookWithUsersInputDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.findById(id));
    }

    //juste les admins
    @PatchMapping("/{id}")
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

    @PatchMapping("/{bookId}/status/{userId}") //permet de mettre si on a lu un livre, le {userId} sera
    // remplacé lorsque spring security sera en place par la récupération de l'id de la personne connectée
    public ResponseEntity<BookWithUsersInputDTO> updateStatus(
            @PathVariable Long bookId,
            @PathVariable Long userId,
            @Valid @RequestBody BookUserCreateDTO bookUserCreateDTO) {
        bookUserService.upCreate(new BookUserId(bookId, userId), bookUserCreateDTO);
        return ResponseEntity.ok(bookService.findById(bookId));
    }
    // si on le possède, la note, le commentaire,
    // seulement le permettre pour l'utilisateur identifié, récupérer son id via spring security

    //

}
