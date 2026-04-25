package com.kinshelf.controllers;

import com.kinshelf.dto.book.BookFromApiDTO;
import com.kinshelf.services.DataFromOtherApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/isbn")
@RequiredArgsConstructor
public class IsbnController {

    private final DataFromOtherApiService dataApiService;

    //controlleur qui sert à récupérer les infos à partir d'un numéro isbn soit sur l'api d'OpenLibrary soit sur Google books
    //Pour pré remplir l'ajout d'un livre
    @GetMapping("/{isbn}")
    public ResponseEntity<BookFromApiDTO> getBookInfo(@PathVariable String isbn) {
        BookFromApiDTO book = dataApiService.bookByIsbn(isbn);
        if (book != null) {
            return ResponseEntity.ok(book);
        }
        return ResponseEntity.notFound().build();
    }
}
