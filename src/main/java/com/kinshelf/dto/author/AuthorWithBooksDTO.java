package com.kinshelf.dto.author;

import com.kinshelf.dto.book.BookTitleAndImgDTO;

import java.util.List;

public record AuthorWithBooksDTO(
        Long id,
        String name,
        List<BookTitleAndImgDTO> books
) {
}
