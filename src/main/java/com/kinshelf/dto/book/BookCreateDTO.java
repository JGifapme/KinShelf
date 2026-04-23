package com.kinshelf.dto.book;

import com.kinshelf.dto.bookAuthor.BookAuthorCreateDTO;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

public record BookCreateDTO(

        @NotBlank
        String title,

        String description,

        @Positive
        Integer numberOfPages,

        String coverUrl,

        LocalDate publicationDate,

        Long publisherId,
        Long categoryId,
        Long seriesId,

        @NotEmpty
        List<BookAuthorCreateDTO> authors,

        List<Long> genreIds

) {}
