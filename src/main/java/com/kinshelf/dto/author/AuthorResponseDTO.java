package com.kinshelf.dto.author;

public record AuthorResponseDTO(
        Long id,
        String name,
        String slug
) {}