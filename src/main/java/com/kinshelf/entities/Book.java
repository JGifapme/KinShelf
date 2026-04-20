package com.kinshelf.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "books")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_book")
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    private String description;

    @Column(name = "nb_pages")
    private Integer numberOfPages;

    @Column(name = "cover_url")
    private String coverUrl;

    @Column(name = "publication_date")
    private LocalDate publicationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "series_id")
    private Series series;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookAuthor> bookAuthors;

    @ManyToMany
    @JoinTable(
            name = "books_genres",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres;

    @JsonIgnore
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookUser> bookUsers;

    @JsonIgnore
    @OneToMany(mappedBy = "book")
    private List<Loan> loans;
}