package com.kinshelf.dto.author;

public record AuthorResponseWithRoleDTO(
        Long id,
        String firstName,
        String lastName,
        String fullName,
        String role
) {}