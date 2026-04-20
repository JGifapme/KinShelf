package com.kinshelf.services;

import com.kinshelf.dto.book.BookCreateDTO;
import com.kinshelf.dto.book.BookMapper;
import com.kinshelf.dto.book.BookResponseDTO;
import com.kinshelf.entities.*;
import com.kinshelf.exceptions.NotFoundException;
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

    public BookResponseDTO create(BookCreateDTO dto) {

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

        return BookMapper.toDTO(bookRepository.save(book));
    }
    public List<BookResponseDTO> findAll() {
        return bookRepository.findAll()
                .stream()
                .map(BookMapper::toDTO)
                .toList();
    }
    public BookResponseDTO findById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Livre introuvable pour l'id : " + id));

        return BookMapper.toDTO(book);
    }
    public void delete(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new NotFoundException("Livre introuvable pour l'id : " + id);
        }

        bookRepository.deleteById(id);
    }
    public BookResponseDTO update(Long id, BookCreateDTO dto) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Livre introuvable pour l'id : " + id));

        book.setTitle(dto.title());
        book.setDescription(dto.description());
        book.setNumberOfPages(dto.numberOfPages());
        book.setCoverUrl(dto.coverUrl());
        book.setPublicationDate(dto.publicationDate());

        // many to one
        if (dto.publisherId() != null) {
            book.setPublisher(
                    publisherRepository.findById(dto.publisherId())
                            .orElseThrow(() -> new NotFoundException("Éditeur introuvable"))
            );
        } else {
            book.setPublisher(null);
        }

        if (dto.categoryId() != null) {
            book.setCategory(
                    categoryRepository.findById(dto.categoryId())
                            .orElseThrow(() -> new NotFoundException("Catégorie introuvable"))
            );
        } else {
            book.setCategory(null);
        }

        if (dto.seriesId() != null) {
            book.setSeries(
                    seriesRepository.findById(dto.seriesId())
                            .orElseThrow(() -> new NotFoundException("Série introuvable"))
            );
        } else {
            book.setSeries(null);
        }

        // auteurs avec role
        book.getBookAuthors().clear();

        List<BookAuthor> bookAuthors = dto.authors().stream()
                .map(a -> {
                    Author author = authorRepository.findById(a.authorId())
                            .orElseThrow(() -> new NotFoundException("Auteur introuvable"));

                    BookAuthor ba = new BookAuthor();
                    ba.setBook(book);
                    ba.setAuthor(author);
                    ba.setRole(a.role());

                    return ba;
                })
                .toList();

        book.getBookAuthors().addAll(bookAuthors);

        // genre many to many
        if (dto.genreIds() != null) {
            List<Genre> genres = genreRepository.findAllById(dto.genreIds());
            book.setGenres(genres);
        } else {
            book.getGenres().clear();
        }

        return BookMapper.toDTO(bookRepository.save(book));
    }
}
