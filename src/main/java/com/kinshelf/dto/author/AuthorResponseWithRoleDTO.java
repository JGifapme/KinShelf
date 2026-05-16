package com.kinshelf.dto.author;

public record AuthorResponseWithRoleDTO(
        Long id,
        String name,
        String slug,
        String role
) {}