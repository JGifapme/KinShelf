package com.kinshelf.controllers;

import com.kinshelf.dto.book.BookFromApiDTO;
import com.kinshelf.exceptions.NotFoundException;
import com.kinshelf.services.DataFromOtherApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/// Controleur qui sert à récupérer les infos à partir d'un numéro isbn soit sur l'api d'OpenLibrary soit sur Google books
/// pour pré-remplir l'ajout d'un livre
@RestController
@RequestMapping("/api/isbn")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")// seul les utilisateurs authentifié/connecté peuvent utiliser cet endpoints
public class IsbnController{

    private final DataFromOtherApiService dataApiService;

    /// Endpoint qui sert à récupérer les infos à partir d'un numéro isbn soit sur l'api d'OpenLibrary soit sur Google books
    /// pour pré-remplir l'ajout d'un livre.
    @GetMapping("/{isbn}")
    public ResponseEntity<BookFromApiDTO> getBookInfo(@PathVariable String isbn) {
        BookFromApiDTO book = dataApiService.bookByIsbn(isbn);
        if (book != null) {
            return ResponseEntity.ok(book);
        }
        else {
            throw new NotFoundException("Livre introuvable pour cet isbn");
        }
    }
}
