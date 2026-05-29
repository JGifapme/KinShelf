package com.kinshelf.dto.book;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kinshelf.dto.bookAuthor.BookAuthorCreateDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;
/// DTO qui donne les infos nécessaires pour créer un livre
/// Les messages après les validations permettent de lancer une MethodArgumentNotValidException contenant le message
public record BookCreateDTO(

        @NotBlank(message = "Le titre est obligatoire")
        @Size(max = 255, message = "Le titre ne peut dépasser 255 caractères.")
        String title,

        @Size(max = 13, message = "L'isbn ne peut dépasser 13 caractères.")
        String isbn,

        String description, // Description ou synopsis du livre

        @Positive(message = "Le nombre de page doit être supérieur à 0.")
        Integer numberOfPages,

        String coverUrl, // URL de l'image de couverture pour l'afficher dans le front

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate publicationDate,

        Long publisherId,

        @NotNull(message = "La catégorie est obligatoire")
        Long categoryId,
        Long seriesId,

        @NotEmpty(message = "Le livre doit avoir au moins 1 auteur.")
        List<BookAuthorCreateDTO> authors, // Liste du/des auteurs du livre

        List<Long> genreIds // Liste des id des genres du livre

) {}
