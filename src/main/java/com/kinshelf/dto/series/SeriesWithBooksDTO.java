package com.kinshelf.dto.series;

import com.kinshelf.dto.book.BookTitleAndImgDTO;
import com.kinshelf.entities.SeriesStatus;

import java.util.List;

public record SeriesWithBooksDTO(
        Long id,
        String name,
        SeriesStatus status,
        List<BookTitleAndImgDTO> books
) {
}
