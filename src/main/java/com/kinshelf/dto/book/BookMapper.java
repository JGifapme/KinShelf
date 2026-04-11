package com.kinshelf.dto.book;

import com.kinshelf.entities.*;
import java.util.List;
import java.util.stream.Collectors;

public class BookMapper {

    public static BookResponseDTO toDTO(Book book) {
        if (book == null) {
            return null;
        }

        return new BookResponseDTO(
                book.getTitle(),
                book.getDescription(),
                book.getNumberOfPages(),
                book.getCoverUrl(),
                book.getPublicationDate(),

                // relations simples
                book.getPublisher() != null ? book.getPublisher().getName() : null,
                book.getCategory() != null ? book.getCategory().getName() : null,
                book.getSeries() != null ? book.getSeries().getName() : null,

                // authors
                mapAuthors(book),

                // genres
                mapGenres(book)
        );
    }

    private static List<String> mapAuthors(Book book) {
        if (book.getBookAuthors() == null) {
            return List.of();
        }

        return book.getBookAuthors()
                .stream()
                .map(ba -> {
                    Author author = ba.getAuthor();
                    return author.getFirstName() + " " + author.getLastName();
                })
                .collect(Collectors.toList());
    }

    private static List<String> mapGenres(Book book) {
        if (book.getGenres() == null) {
            return List.of();
        }

        return book.getGenres()
                .stream()
                .map(Genre::getName)
                .collect(Collectors.toList());
    }
}