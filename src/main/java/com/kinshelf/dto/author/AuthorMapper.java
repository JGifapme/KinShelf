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

public class AuthorMapper {

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

    public static Author toEntity(AuthorCreateDTO dto) {
        if (dto == null) {
            return null;
        }
        Author author = new Author();
        author.setName(dto.name());
        return author;
    }

    public static void updateEntity(Author author, AuthorCreateDTO dto) {
        if (author == null || dto == null) {
            return;
        }
        if (dto.name() != null) {
            author.setName(dto.name());
        }
    }

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