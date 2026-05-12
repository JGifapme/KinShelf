package com.kinshelf.services;

import com.kinshelf.dto.book.BookCreateDTO;
import com.kinshelf.dto.book.BookMapper;
import com.kinshelf.dto.book.BookResponseDTO;
import com.kinshelf.dto.book.BookWithUsersInputDTO;
import com.kinshelf.entities.*;
import com.kinshelf.exceptions.BadRequestException;
import com.kinshelf.exceptions.NotFoundException;
import com.kinshelf.repositories.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
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
    private final BookMapper bookMapper;

    @Transactional
    public BookResponseDTO create(BookCreateDTO dto) {
        String slug = Slugify.toSlug(dto.title());
        // vérifier que le slug et l'isbn sont uniques
        while (bookRepository.existsBySlug(slug)) {
            throw new BadRequestException("L'url associée existe déjà. Vérifiez qu'un livre avec un nom similaire n'existe pas déjà.");
        }
        String isbn = dto.isbn();
        while (bookRepository.existsByIsbn(isbn)){
            throw new BadRequestException("Cet isbn existe déjà, veuillez vérifier que le livre n'existe pas déjà sur KinShelf.");
        }
        Book book = new Book();
        book.setSlug(slug);
        bookMapper.updateEntityFromDTO(book, dto);

        return bookMapper.toDTO(bookRepository.save(book));
    }

    public List<BookResponseDTO> findAll() {
        return bookRepository.findAllByOrderByTitleAsc()
                .stream()
                .map(bookMapper::toDTO)
                .toList();
    }
    public List<BookResponseDTO> findAll(String search, String genreSlug, String userSlug) {
        // On vérifie que les paramètre ne soit pas vide "" ce qui pourrait provoquer des erreurs, on préfère renvoyer null
        String searchN = search;
        if (search == null || search.trim().isEmpty()) {
            searchN = null;
        }
        String genreSlugN = genreSlug;
        if (genreSlug == null || genreSlug.trim().isEmpty()) {
            genreSlugN = null;
        }
        String userSlugN = userSlug;
        if (userSlug == null || userSlug.trim().isEmpty()) {
            userSlugN = null;
        }
        return bookRepository.findBookSearch(searchN, genreSlugN, userSlugN)
                .stream()
                .map(bookMapper::toDTO)
                .toList();
    }

    public BookWithUsersInputDTO findById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Livre introuvable pour l'id : " + id));

        return bookMapper.toDTOWithUsersInput(book);
    }
    public BookWithUsersInputDTO findBySlug(String slug) {
        Book book = bookRepository.findBySlug(slug)
                .orElseThrow(() -> new NotFoundException("Livre introuvable pour cette url."));

        return bookMapper.toDTOWithUsersInput(book);
    }

    @Transactional
    public void delete(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new NotFoundException("Livre introuvable pour l'id : " + id);
        }

        bookRepository.deleteById(id);
    }

    @Transactional
    public BookResponseDTO update(Long id, BookCreateDTO dto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Livre introuvable pour l'id : " + id));

        //Slug et isbn vérifier !!
        bookMapper.updateEntityFromDTO(book, dto);
        return bookMapper.toDTO(bookRepository.save(book));
    }


}
