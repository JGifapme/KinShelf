package com.kinshelf.dto.book;

import java.time.LocalDate;
import java.util.List;
/// DTO qui renvoie les informations récupérées sur Open Library et Google Books grâce au code ISBN
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
