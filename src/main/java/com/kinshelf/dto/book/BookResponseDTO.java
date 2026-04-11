package com.kinshelf.dto.book;

import java.time.LocalDate;
import java.util.List;

public record BookResponseDTO(

        String title,
        String description,
        Integer numberOfPages,
        String coverUrl,
        LocalDate publicationDate,

        String publisherName,
        String categoryName,
        String seriesName,

        List<String> authors,
        List<String> genres

) {}
