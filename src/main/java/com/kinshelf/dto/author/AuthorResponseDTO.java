package com.kinshelf.dto.author;

public record AuthorResponseDTO(
        Integer id,
        String firstName,
        String lastName,
        String fullName
) {}