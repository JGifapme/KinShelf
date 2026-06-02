package com.kinshelf.dto.book;
/// DTO léger qui renvoie le titre, le slug et l'id d'un livre
public record BookTitleDTO(
        Long id,
        String title,
        String slug
) {}
