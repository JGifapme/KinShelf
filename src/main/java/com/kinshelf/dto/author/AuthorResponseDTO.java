package com.kinshelf.dto.author;

public record AuthorResponseDTO(
        Long id,
        String firstName,
        String lastName,
        String fullName
) {}