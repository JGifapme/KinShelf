package com.kinshelf.controllers;

import com.kinshelf.dto.author.AuthorWithBooksDTO;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:5173")
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
    public ResponseEntity<Page<BookTitleAndImgDTO>> getAll(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String genreSlug,
            @RequestParam(required = false) String userSlug,
            @RequestParam(required = false) String categorySlug,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size
            ) {
        Pageable pageable = PageRequest.of(page, size);
        //Ajouter la possibilité de mettre des filtres : lu, possédé, en fonction de la note, de l'auteur, du titre,
            return ResponseEntity.ok(bookService.findAll(search, genreSlug, userSlug, categorySlug, pageable));
    }

    //n'importe quel user identifié
    @GetMapping("/id/{id}")
    public ResponseEntity<BookWithUsersInputDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.findById(id));
        //retourner la liste des utilisateurs qui l'ont, l'ont lu -> Fait!
    }
    @GetMapping("/{slug}")
    public ResponseEntity<BookWithUsersInputDTO> getBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(bookService.findBySlug(slug));
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
        //vérifier que ça supprime bien les tables intermédiaires : bookAuthor, bookUser, Loan, bookGenres
    }


    ///Gestion des relations Book <-> User
    @PatchMapping("/{bookId}/status/{userId}") //permet de mettre si on a lu un livre, le possède, sa note, ..., le {userId} sera
    // remplacé lorsque spring security sera en place par la récupération de l'id de la personne connectée
    public ResponseEntity<BookWithUsersInputDTO> upCreateStatus(
            @PathVariable Long bookId,
            @PathVariable Long userId,
            @Valid @RequestBody BookUserCreateDTO bookUserCreateDTO) {
        bookUserService.upCreate(new BookUserId(bookId, userId), bookUserCreateDTO);
        //Update ou créer l'entrée : 1 seul endpoint pour les 2
        return ResponseEntity.ok(bookService.findById(bookId));
    }
    // si on le possède, la note, le commentaire,
    // seulement le permettre pour l'utilisateur identifié, récupérer son id via spring security

}
