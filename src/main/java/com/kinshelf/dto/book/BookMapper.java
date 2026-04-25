package com.kinshelf.dto.book;

import com.kinshelf.dto.author.AuthorResponseDTO;
import com.kinshelf.dto.author.AuthorResponseWithRoleDTO;
import com.kinshelf.dto.category.CategoryMapper;
import com.kinshelf.dto.category.CategoryResponseDTO;
import com.kinshelf.dto.genre.GenreResponseDTO;
import com.kinshelf.dto.publisher.PublisherMapper;
import com.kinshelf.dto.publisher.PublisherResponseDTO;
import com.kinshelf.dto.series.SeriesMapper;
import com.kinshelf.dto.series.SeriesResponseDTO;
import com.kinshelf.entities.*;
import java.util.List;
import java.util.stream.Collectors;

public class BookMapper {

    public static BookResponseDTO toDTO(Book book) {
        if (book == null) {
            return null;
        }

        PublisherResponseDTO publisherDTO = null;
        if (book.getPublisher() != null) {
            publisherDTO = PublisherMapper.toDTO(book.getPublisher());
        }

        CategoryResponseDTO categoryDTO = null;
        if (book.getCategory() != null) {
            categoryDTO = CategoryMapper.toDTO(book.getCategory());
        }

        SeriesResponseDTO seriesDTO = null;
        if (book.getSeries() != null) {
            seriesDTO = SeriesMapper.toDTO(book.getSeries());
        }
        return new BookResponseDTO(
                book.getId(),
                book.getTitle(),
                book.getDescription(),
                book.getNumberOfPages(),
                book.getCoverUrl(),
                book.getPublicationDate(),

                // relations simples
                publisherDTO,
                categoryDTO,
                seriesDTO,

                // relations many to many
                mapAuthors(book),
                mapGenres(book)
        );
    }

    private static List<AuthorResponseWithRoleDTO> mapAuthors(Book book) {
        if (book.getBookAuthors() == null) {
            return List.of();
        }

        return book.getBookAuthors()
                .stream()
                .map(ba -> {
                    Author author = ba.getAuthor();
                    // On récupère le rôle depuis books_authors
                    String roleName = ba.getRole() != null ? ba.getRole().name() : "AUTEUR";

                    return new AuthorResponseWithRoleDTO(
                            author.getId(),
                            author.getFirstName(),
                            author.getLastName(),
                            author.getFirstName() + " " + author.getLastName(),
                            roleName
                    );
                })
                .collect(Collectors.toList());
    }

    private static List<GenreResponseDTO> mapGenres(Book book) {
        if (book.getGenres() == null) {
            return List.of();
        }

        return book.getGenres()
                .stream()
                .map(genre -> new GenreResponseDTO(genre.getId(), genre.getName()))
                .collect(Collectors.toList());
    }
}