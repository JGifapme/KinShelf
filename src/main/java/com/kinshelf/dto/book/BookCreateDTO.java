package com.kinshelf.dto.book;

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

        Integer publisherId,
        Integer categoryId,
        Integer seriesId,

        @NotEmpty
        List<BookAuthorCreateDTO> authors,

        List<Integer> genreIds

) {}
