package com.kinshelf.dto.genre;

import com.kinshelf.dto.book.BookTitleAndImgDTO;
import com.kinshelf.entities.Book;
import com.kinshelf.entities.Genre;

import java.util.ArrayList;
import java.util.List;

public class GenreMapper {

    public static GenreResponseDTO toDTO(Genre genre) {
        if (genre == null) {
            return null;
        }

        return new GenreResponseDTO(
                genre.getId(),
                genre.getName()
        );
    }

    public static Genre toEntity(GenreCreateDTO dto) {
        if (dto == null) {
            return null;
        }

        return Genre.builder()
                .name(dto.name())
                .build();
    }

    public static void updateEntity(Genre genre, GenreCreateDTO dto) {
        if (genre == null || dto == null) {
            return;
        }

        genre.setName(dto.name());
    }

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
    private static List<BookTitleAndImgDTO> mapBooks(Genre genre) {
        List<BookTitleAndImgDTO> bookTitles = new ArrayList<>();

        if (genre.getBooks() == null) {
            return bookTitles;
        }
        for (Book book : genre.getBooks()) {
            BookTitleAndImgDTO dto = new BookTitleAndImgDTO(
                    book.getId(),
                    book.getTitle(),
                    book.getCoverUrl()
            );
            bookTitles.add(dto);
        }
        return bookTitles;
    }
}
