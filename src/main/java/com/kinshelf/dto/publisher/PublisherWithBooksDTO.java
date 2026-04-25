package com.kinshelf.dto.publisher;

import com.kinshelf.dto.book.BookTitleAndImgDTO;

import java.util.List;

public record PublisherWithBooksDTO(
        Long id,
        String name,
        List<BookTitleAndImgDTO> books
) {
}
