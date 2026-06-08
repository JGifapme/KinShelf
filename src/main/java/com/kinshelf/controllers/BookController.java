package com.kinshelf.controllers;

import com.kinshelf.dto.book.BookCreateDTO;
import com.kinshelf.dto.book.BookResponseDTO;
import com.kinshelf.dto.book.BookTitleAndImgDTO;
import com.kinshelf.dto.book.BookWithUsersInputDTO;
import com.kinshelf.dto.bookUser.BookUserCreateDTO;
import com.kinshelf.entities.BookUserId;
import com.kinshelf.entities.UserDetailsImplementation;
import com.kinshelf.services.BookService;
import com.kinshelf.services.BookUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/// Controleur qui permet de créer, récupérer, mettre à jour et supprimer les informations sur les livres
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")// seul les utilisateurs authentifié/connecté peuvent utiliser ces endpoints
public class BookController {

    private final BookService bookService;
    private final BookUserService bookUserService;

    /// Endpoint qui permet de créer un livre/ouvrage
    //n'importe quel user identifié
    @PostMapping
    public ResponseEntity<BookResponseDTO> create(@Valid @RequestBody BookCreateDTO dto) {
        return ResponseEntity.ok(bookService.create(dto));
    }

    /// Endpoint qui permet récupérer la liste de tous les livres qui respèctent certains critères
    //n'importe quel user identifié
    @GetMapping
    public ResponseEntity<Page<BookTitleAndImgDTO>> getAll(
            @RequestParam(required = false) String search, // Une recherche textuelle sur le nom du livre, l'auteur ou la série
            @RequestParam(required = false) String genreSlug, // Une recherche par genre (fantasy, fiction, ...)
            @RequestParam(required = false) String userSlug, // Une recherche par utilisateur qui le possède
            @RequestParam(required = false) String categorySlug, // Une recherche par catégorie (Roman, BD, manga)
            @RequestParam(required = false) String publisherSlug, // Une recherche par éditeur
            @RequestParam(required = false) String userStatus, // Une recherche par status de l'utilisateur connecté (Possédé, Lu, Wishlist)
            @RequestParam(defaultValue = "0") int page, // Affichage par Page
            @RequestParam(defaultValue = "50") int size, // par défaut de 50 livres
            @RequestParam(defaultValue = "a-z") String sortBy, // Trier par défaut de A à Z
            @AuthenticationPrincipal UserDetailsImplementation userDetails // Récupère les informations de l'utilisateur dans le jwt token
            ) {
        Long userId = userDetails.getUserEntity().getId(); // on récupère l'id de l'utilisateur connecté pour faire une recherche dessus
            return ResponseEntity.ok(bookService.findAll(search, genreSlug, userSlug, categorySlug, publisherSlug, userStatus, page, size, sortBy, userId));
    }

    /// Endpoint qui permet de récupérer les informations d'un livres et la liste des
    /// interactions utilisateurs grâce à son id
    //n'importe quel user identifié
    @GetMapping("/id/{id}")
    public ResponseEntity<BookWithUsersInputDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.findById(id));
        //retourner la liste des utilisateurs qui l'ont, l'ont lu -> Fait!
    }
    /// Endpoint qui permet de récupérer les informations d'un livres et la liste des
    /// interactions utilisateurs grâce à son slug
    @GetMapping("/{slug}")
    public ResponseEntity<BookWithUsersInputDTO> getBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(bookService.findBySlug(slug));
    }

    /// Endpoint qui permet de mettre à jour les informations d'un livre
    //juste les admins ?
    @PatchMapping("/{id}")
    public ResponseEntity<BookResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody BookCreateDTO dto
    ) {
        return ResponseEntity.ok(bookService.update(id, dto));
    }

    /// Endpoint qui permet de supprimer un livre (admin seulement)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") // seulement les admins sont autorisé à utiliser cet endpoint
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
        //vérifier que ça supprime bien les tables intermédiaires : bookAuthor, bookUser, Loan, bookGenres
    }

    /// Endpoint qui permet de mettre à jour les informations de l'utilisateur connecté vis a vis d'un livre.
    /// Si il l'a lu, le possède, whislist, la note sur 5 et son commentaire.
    /// Gestion des relations Book <-> User.
    /// Upsert : Update ou créer l'entrée : 1 seul endpoint pour les 2.
    @PatchMapping("/{bookId}/status") //permet de mettre si on a lu un livre, le possède, sa note, ...,
    public ResponseEntity<BookWithUsersInputDTO> upCreateStatus(
            @PathVariable Long bookId,
            // seulement le permettre pour l'utilisateur identifié, récupérer son id via spring security :
            @AuthenticationPrincipal UserDetailsImplementation userDetails,
            @Valid @RequestBody BookUserCreateDTO bookUserCreateDTO) {
        bookUserService.upCreate(new BookUserId(bookId, userDetails.getUserEntity().getId()), bookUserCreateDTO);

        return ResponseEntity.ok(bookService.findById(bookId));
    }

}
