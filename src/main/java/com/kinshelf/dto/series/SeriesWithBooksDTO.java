package com.kinshelf.dto.series;

import com.kinshelf.dto.book.BookTitleAndImgDTO;
import com.kinshelf.entities.SeriesStatus;

import java.util.List;
/// DTO qui retourne l'id, le nom et la liste des livres d'une série
public record SeriesWithBooksDTO(
        Long id,
        String name,
        SeriesStatus status,
        List<BookTitleAndImgDTO> books
) {
}
