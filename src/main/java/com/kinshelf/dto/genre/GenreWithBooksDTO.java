package com.kinshelf.dto.genre;

import com.kinshelf.dto.book.BookTitleAndImgDTO;
import java.util.List;

public record GenreWithBooksDTO(
        Long id,
        String name,
        List<BookTitleAndImgDTO> books
) {
}
