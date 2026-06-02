package com.kinshelf.dto.publisher;

import com.kinshelf.dto.book.BookTitleAndImgDTO;

import java.util.List;
/// DTO qui retourne l'id, le nom et la liste des livres d'un éditeur
public record PublisherWithBooksDTO(
        Long id,
        String name,
        List<BookTitleAndImgDTO> books
) {
}
