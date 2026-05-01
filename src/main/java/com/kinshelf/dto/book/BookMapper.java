package com.kinshelf.dto.book;

import com.kinshelf.dto.author.AuthorResponseDTO;
import com.kinshelf.dto.author.AuthorResponseWithRoleDTO;
import com.kinshelf.dto.bookAuthor.BookAuthorCreateDTO;
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
import java.util.Map;
import java.util.Optional;
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
        if (dto.authors() == null) {
            book.getBookAuthors().clear();
            return;
        }
        // on créer une Map avec les nouveaux auteurs
        Map<Long, BookAuthorCreateDTO> nouveauxAuteurs = dto.authors().stream()
                .collect(Collectors.toMap(BookAuthorCreateDTO::authorId, a -> a));
        // on regarde avec les anciens auteurs et on supprime ceux qui ne sont pas dans la Map
        book.getBookAuthors().removeIf(ba -> !nouveauxAuteurs.containsKey(ba.getAuthor().getId()));

        // on ajoute les nouveaux
        for (BookAuthorCreateDTO authorDto : dto.authors()) {
            Optional<BookAuthor> existing = book.getBookAuthors().stream()
                    .filter(ba -> ba.getAuthor().getId().equals(authorDto.authorId()))
                    .findFirst();

            if (existing.isPresent()) {
                // pour les anciens on met le role à jour
                existing.get().setRole(authorDto.role());
            } else {
                // pour les nouveaux on les créer
                Author author = authorRepository.findById(authorDto.authorId()).orElseThrow();
                BookAuthor ba = new BookAuthor();
                BookAuthorId id = new BookAuthorId(book.getId(), author.getId());
                ba.setId(id);
                ba.setBook(book);
                ba.setAuthor(author);
                ba.setRole(authorDto.role());
                book.getBookAuthors().add(ba);
            }
        }
    }
}