package com.kinshelf.dto.category;

import com.kinshelf.dto.book.BookTitleAndImgDTO;
import com.kinshelf.dto.book.BookTitleDTO;

import java.util.List;
/// DTO qui retourne l'id, le nom et la liste des livres d'une catégorie
public record CategoryWithBooksDTO(
        Long id,
        String name,
        List<BookTitleAndImgDTO> books
) {
}
