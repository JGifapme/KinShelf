package com.kinshelf.dto.category;

import com.kinshelf.dto.book.BookTitleAndImgDTO;
import com.kinshelf.dto.book.BookTitleDTO;

import java.util.List;

public record CategoryWithBooksDTO(
        Long id,
        String name,
        List<BookTitleAndImgDTO> books
) {
}
