package com.kinshelf.dto.book;

import com.kinshelf.dto.author.AuthorResponseDTO;
import com.kinshelf.dto.author.AuthorResponseWithRoleDTO;
import com.kinshelf.dto.bookUser.BUWithUserNameDTO;
import com.kinshelf.dto.category.CategoryMapper;
import com.kinshelf.dto.category.CategoryResponseDTO;
import com.kinshelf.dto.genre.GenreResponseDTO;
import com.kinshelf.dto.publisher.PublisherMapper;
import com.kinshelf.dto.publisher.PublisherResponseDTO;
import com.kinshelf.dto.series.SeriesMapper;
import com.kinshelf.dto.series.SeriesResponseDTO;
import com.kinshelf.entities.*;
import com.kinshelf.exceptions.NotFoundException;
import com.kinshelf.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BookMapper {

    private final PublisherRepository publisherRepository;
    private final CategoryRepository categoryRepository;
    private final SeriesRepository seriesRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    public BookResponseDTO toDTO(Book book) {
        if (book == null) {
            return null;
        }
        return new BookResponseDTO(
                book.getId(),
                book.getTitle(),
                book.getDescription(),
                book.getNumberOfPages(),
                book.getCoverUrl(),
                book.getPublicationDate(),

                // relations simples
                mapPublisher(book),
                mapCategory(book),
                mapSeries(book),

                // relations many to many
                mapAuthors(book),
                mapGenres(book)
        );
    }


    public BookWithUsersInputDTO toDTOWithUsersInput(Book book) {
        if (book == null) {
            return null;
        }
        return new BookWithUsersInputDTO(
                book.getId(),
                book.getTitle(),
                book.getDescription(),
                book.getNumberOfPages(),
                book.getCoverUrl(),
                book.getPublicationDate(),

                // relations simples
                mapPublisher(book),
                mapCategory(book),
                mapSeries(book),

                // relations many to many
                mapAuthors(book),
                mapGenres(book),
                mapBookUser(book)
        );
    }

    private PublisherResponseDTO mapPublisher(Book book) {
        if (book.getPublisher() != null) {
            return PublisherMapper.toDTO(book.getPublisher());
        }
        return null;
    }

    private CategoryResponseDTO mapCategory(Book book) {
        if (book.getCategory() != null) {
            return CategoryMapper.toDTO(book.getCategory());
        }
        return null;
    }

    private SeriesResponseDTO mapSeries(Book book) {
        if (book.getSeries() != null) {
            return SeriesMapper.toDTO(book.getSeries());
        }
        return null;
    }

    private List<AuthorResponseWithRoleDTO> mapAuthors(Book book) {
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

    private List<GenreResponseDTO> mapGenres(Book book) {
        if (book.getGenres() == null) {
            return List.of();
        }

        return book.getGenres()
                .stream()
                .map(genre -> new GenreResponseDTO(genre.getId(), genre.getName()))
                .collect(Collectors.toList());
    }
    private List<BUWithUserNameDTO> mapBookUser(Book book) {
        if (book.getBookUsers() == null) {
            return List.of();
        }

        return book.getBookUsers()
                .stream()
                .map(bu -> new BUWithUserNameDTO(
                        bu.getId(),
                        bu.getBook().getId(),
                        bu.getUser().getId(),
                        bu.getUser().getFirstName()+" "+bu.getUser().getLastName(),
                        bu.getIsOwn(),
                        bu.getIsRead(),
                        bu.getIsInterested(),
                        bu.getRating(),
                        bu.getComment()
                ))
                .collect(Collectors.toList());
    }

    public void updateEntityFromDTO(Book book, BookCreateDTO dto) {
        book.setTitle(dto.title());
        book.setDescription(dto.description());
        book.setNumberOfPages(dto.numberOfPages());
        book.setCoverUrl(dto.coverUrl());
        book.setPublicationDate(dto.publicationDate());

        if (dto.publisherId() != null) {
            Publisher publisher = publisherRepository.findById(dto.publisherId())
                    .orElseThrow(() -> new NotFoundException("Éditeur introuvable"));
            book.setPublisher(publisher);
        } else {
            book.setPublisher(null);
        }

        if (dto.categoryId() != null) {
            Category category = categoryRepository.findById(dto.categoryId())
                    .orElseThrow(() -> new NotFoundException("Catégorie introuvable"));
            book.setCategory(category);
        } else {
            book.setCategory(null);
        }
        if (dto.seriesId() != null) {
            Series series = seriesRepository.findById(dto.seriesId())
                    .orElseThrow(() -> new NotFoundException("Série introuvable"));
            book.setSeries(series);
        } else {
            book.setSeries(null);
        }

        // Gestion des auteurs
        updateAuthors(book, dto);

        // gestion des genres
        if (dto.genreIds() != null) {
            book.setGenres(genreRepository.findAllById(dto.genreIds()));
        } else {
            book.getGenres().clear();
        }
    }
    private void updateAuthors(Book book, BookCreateDTO dto) {
        book.getBookAuthors().clear();
        if (dto.authors() != null) {
            List<BookAuthor> newAuthors = dto.authors().stream().map(a -> {
                Author author = authorRepository.findById(a.authorId()).orElseThrow();
                BookAuthor ba = new BookAuthor();
                ba.setBook(book);
                ba.setAuthor(author);
                ba.setRole(a.role());
                return ba;
            }).toList();
            book.getBookAuthors().addAll(newAuthors);
        }
    }
}