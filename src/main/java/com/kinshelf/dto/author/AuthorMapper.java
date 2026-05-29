package com.kinshelf.dto.author;

import com.kinshelf.dto.book.BookTitleAndImgDTO;
import com.kinshelf.dto.book.BookTitleDTO;
import com.kinshelf.dto.genre.GenreResponseDTO;
import com.kinshelf.entities.Author;
import com.kinshelf.entities.Book;
import com.kinshelf.entities.BookAuthor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
/// Classe utilitaire pour convertir les objets Author
public class AuthorMapper {

    /// Convertit un objet Author en DTO AuthorResponseDTO
    public static AuthorResponseDTO toDTO(Author author) {
        if (author == null) {
            return null;
        }

        return new AuthorResponseDTO(
                author.getId(),
                author.getName(),
                author.getSlug()
        );
    }
    /// Convertit un DTO AuthorCreateDTO en objet Author
    public static Author toEntity(AuthorCreateDTO dto) {
        if (dto == null) {
            return null;
        }
        Author author = new Author();
        author.setName(dto.name());
        return author;
    }

    /// Met à jour un objet Author avec les données du DTO
    public static void updateEntity(Author author, AuthorCreateDTO dto) {
        if (author == null || dto == null) {
            return;
        }
        if (dto.name() != null) {
            author.setName(dto.name());
        }
    }

    /// Convertit un Author en DTO avec sa liste de livres
    public static AuthorWithBooksDTO toDTOWithBooks(Author author) {
        if (author == null) {
            return null;
        }

        return new AuthorWithBooksDTO(
                author.getId(),
                author.getName(),
                mapBooks(author)
        );
    }

    /// Récupère la liste des livres de l’auteur en DTO simplifié juste le titre, le slug et l'image
    private static List<BookTitleAndImgDTO> mapBooks(Author author) {
        List<BookTitleAndImgDTO> bookTitles = new ArrayList<>();

        for (BookAuthor bookAuthorLink : author.getBookAuthors()) {
            Book book = bookAuthorLink.getBook();
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