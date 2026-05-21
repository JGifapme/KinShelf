package com.kinshelf.services;

import com.kinshelf.dto.book.*;
import com.kinshelf.entities.*;
import com.kinshelf.exceptions.BadRequestException;
import com.kinshelf.exceptions.NotFoundException;
import com.kinshelf.repositories.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
        // vérifier que l'isbn est unique
        String isbn = dto.isbn();
        while (bookRepository.existsByIsbn(isbn)){
            throw new BadRequestException("Cet isbn existe déjà, veuillez vérifier que le livre n'existe pas déjà sur KinShelf.");
        }
        // Créer un slug unique :
        String slug = Slugify.toSlug(dto.title());
        Integer number = 1;
        slug = createSlug(slug,number);
        //théoriquement on a créé un slug unique, mais au cas ou, pour renvoyer une erreur propre :
        while (bookRepository.existsBySlug(slug)) {
            throw new BadRequestException("L'url associée existe déjà. Vérifiez qu'un livre avec un nom similaire n'existe pas déjà.");
        }
        Book book = new Book();
        book.setSlug(slug);
        bookMapper.updateEntityFromDTO(book, dto);

        return bookMapper.toDTO(bookRepository.save(book));
    }

    public Page<BookTitleAndImgDTO> findAll(Pageable pageable) {
        return bookRepository.findAllByOrderByTitleAsc(pageable).map(bookMapper::toDTOTitleAndImg);
    }
    public Page<BookTitleAndImgDTO> findAll(String search, String genreSlug, String userSlug, String categorySlug, String publisherSlug, Pageable pageable) {
        // On vérifie que les paramètre ne soit pas vide "" ce qui pourrait provoquer des erreurs, on préfère renvoyer null
        String searchN = search;
        if (search == null || search.trim().isEmpty()) {
            searchN = null;
        }
        String genreSlugN = genreSlug;
        if (genreSlug == null || genreSlug.trim().isEmpty()) {
            genreSlugN = null;
        }
        String allUsers = null;
        if (Objects.equals(userSlug, "all-users")) {
            userSlug = null;
            allUsers = "1";

        }
        String userSlugN = userSlug;
        if (userSlug == null || userSlug.trim().isEmpty()) {
            userSlugN = null;
        }

        String categorySlugN = categorySlug;
        if (categorySlug == null || categorySlug.trim().isEmpty()) {
            categorySlugN = null;
        }
        String publisherSlugN = publisherSlug;
        if (publisherSlug == null || publisherSlug.trim().isEmpty()) {
            publisherSlugN = null;
        }
        return bookRepository.findBookSearch(searchN, genreSlugN, userSlugN, categorySlugN, allUsers, publisherSlugN, pageable)
                .map(bookMapper::toDTOTitleAndImg);
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
    public boolean isIsbnInDb(String isbn) {
        return bookRepository.existsByIsbn(isbn);
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

    private String createSlug(String slug, Integer number){
        String newSlug;
        if (number == 1){
            newSlug = slug;
        }
        else{
            newSlug = slug+"-"+number;
        }
        if (bookRepository.existsBySlug(newSlug)){
            return createSlug(slug,(number+1));
        }
        return newSlug;
    }
}
