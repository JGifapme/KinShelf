package com.kinshelf.services;

import com.kinshelf.dto.book.BookCreateDTO;
import com.kinshelf.dto.book.BookMapper;
import com.kinshelf.dto.book.BookResponseDTO;
import com.kinshelf.dto.book.BookWithUsersInputDTO;
import com.kinshelf.entities.*;
import com.kinshelf.exceptions.NotFoundException;
import com.kinshelf.repositories.*;
import jakarta.transaction.Transactional;
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
    private final BookMapper bookMapper;

    @Transactional
    public BookResponseDTO create(BookCreateDTO dto) {
        Book book = new Book();
        bookMapper.updateEntityFromDTO(book, dto);

        return bookMapper.toDTO(bookRepository.save(book));
    }

    public List<BookResponseDTO> findAll() {
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::toDTO)
                .toList();
    }

    public BookWithUsersInputDTO findById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Livre introuvable pour l'id : " + id));

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

        bookMapper.updateEntityFromDTO(book, dto);
        return bookMapper.toDTO(bookRepository.save(book));
    }
}
