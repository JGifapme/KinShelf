package com.kinshelf.services;

import com.kinshelf.dto.book.BookCreateDTO;
import com.kinshelf.entities.*;
import com.kinshelf.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final SeriesRepository seriesRepository;
    private final GenreRepository genreRepository;
    private final PublisherRepository publisherRepository;
    private final AuthorRepository authorRepository;

    public Book create(BookCreateDTO dto) {

        Book book = Book.builder()
                .title(dto.title())
                .description(dto.description())
                .numberOfPages(dto.numberOfPages())
                .coverUrl(dto.coverUrl())
                .publicationDate(dto.publicationDate())
                .build();

        // relations simples
        if (dto.publisherId() != null) {
            book.setPublisher(publisherRepository.findById(dto.publisherId()).orElseThrow());
        }

        if (dto.categoryId() != null) {
            book.setCategory(categoryRepository.findById(dto.categoryId()).orElseThrow());
        }

        if (dto.seriesId() != null) {
            book.setSeries(seriesRepository.findById(dto.seriesId()).orElseThrow());
        }

        List<BookAuthor> bookAuthors = dto.authors().stream()
                .map(a -> {
                    Author author = authorRepository.findById(a.authorId()).orElseThrow();

                    BookAuthor ba = new BookAuthor();
                    ba.setBook(book);
                    ba.setAuthor(author);
                    ba.setRole(a.role());

                    return ba;
                })
                .toList();

        book.setBookAuthors(bookAuthors);

        if (dto.genreIds() != null) {
            List<Genre> genres = genreRepository.findAllById(dto.genreIds());
            book.setGenres(genres);
        }

        return bookRepository.save(book);
    }
}
