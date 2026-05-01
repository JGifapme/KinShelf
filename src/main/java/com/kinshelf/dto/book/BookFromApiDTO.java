package com.kinshelf.dto.book;

import java.time.LocalDate;
import java.util.List;

public record BookFromApiDTO(
        String title,
        String publisher,
        List<String> authors,
        String description,
        String isbn,
        Integer pageCount,
        String imageUrl,
        String publicationDate
) {}
