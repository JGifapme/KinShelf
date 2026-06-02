package com.kinshelf.dto.book;
/// DTO léger qui renvoie le titre, le slug, l'id et l'url de l'image de couverture d'un livre
public record BookTitleAndImgDTO(
        Long id,
        String title,
        String slug,
        String coverUrl
) {}
