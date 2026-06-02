package com.kinshelf.dto.genre;

import com.kinshelf.dto.book.BookTitleAndImgDTO;
import java.util.List;
/// DTO qui retourne l'id, le nom et la liste des livres d'un genre
public record GenreWithBooksDTO(
        Long id,
        String name,
        List<BookTitleAndImgDTO> books
) {
}
