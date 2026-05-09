package com.kinshelf.dto.book;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kinshelf.dto.bookAuthor.BookAuthorCreateDTO;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

public record BookCreateDTO(

        @NotBlank(message = "Le titre est obligatoire")
        String title,

        String isbn,

        String description,

        @Positive(message = "Le nombre de page doit être supérieur à 0.")
        Integer numberOfPages,

        String coverUrl,

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate publicationDate,

        Long publisherId,
        Long categoryId,
        Long seriesId,

        @NotEmpty(message = "Le livre doit avoir au moins 1 auteur.")
        List<BookAuthorCreateDTO> authors,

        List<Long> genreIds

) {}
