package com.kinshelf.dto.genre;

import com.kinshelf.dto.book.BookTitleAndImgDTO;
import com.kinshelf.entities.Book;
import com.kinshelf.entities.Genre;

import java.util.ArrayList;
import java.util.List;
/// Classe utilitaire pour convertir les objets Genre en DTO et inversément
public class GenreMapper {

    /// Convertit un objet Genre en DTO GenreResponseDTO
    public static GenreResponseDTO toDTO(Genre genre) {
        if (genre == null) {
            return null;
        }

        return new GenreResponseDTO(
                genre.getId(),
                genre.getName(),
                genre.getSlug()
        );
    }

    /// Convertit un DTO GenreCreateDTO en objet Genre
    public static Genre toEntity(GenreCreateDTO dto) {
        if (dto == null) {
            return null;
        }

        return Genre.builder()
                .name(dto.name())
                .build();
    }
    /// Met à jour un objet Genre avec les données du DTO
    public static void updateEntity(Genre genre, GenreCreateDTO dto) {
        if (genre == null || dto == null) {
            return;
        }
        if (dto.name() != null) {
            genre.setName(dto.name());
        }
    }
    /// Convertit un Genre en DTO avec la liste des livres qui ont ce genre
    public static GenreWithBooksDTO toDTOGenreWithBooks(Genre genre) {
        if (genre == null) {
            return null;
        }
        List<BookTitleAndImgDTO> bookList = mapBooks(genre);
        return new GenreWithBooksDTO(
                genre.getId(),
                genre.getName(),
                bookList
        );
    }
    /// Récupère la liste des livres associés au genre en DTO simplifié juste le titre, le slug et l'image
    private static List<BookTitleAndImgDTO> mapBooks(Genre genre) {
        List<BookTitleAndImgDTO> bookTitles = new ArrayList<>();

        if (genre.getBooks() == null) {
            return bookTitles;
        }
        for (Book book : genre.getBooks()) {
            BookTitleAndImgDTO dto = new BookTitleAndImgDTO(
                    book.getId(),
                    book.getTitle(),
                    book.getSlug(),
                    book.getCoverUrl()
            );
            bookTitles.add(dto);
        }
        return bookTitles;
    }
}
