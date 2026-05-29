package com.kinshelf.dto.author;

import com.kinshelf.dto.book.BookTitleAndImgDTO;

import java.util.List;
/// DTO qui retourne un auteur avec la liste de ses livres
public record AuthorWithBooksDTO(
        Long id,
        String name,
        List<BookTitleAndImgDTO> books
) {
}
