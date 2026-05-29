package com.kinshelf.dto.author;
/// DTO de réponse pour un auteur avec son id, nom et slug
public record AuthorResponseDTO(
        Long id,
        String name,
        String slug
) {}