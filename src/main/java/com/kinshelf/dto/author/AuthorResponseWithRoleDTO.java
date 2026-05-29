package com.kinshelf.dto.author;
///  DTO de réponse pour un auteur avec son role associé à un livre
public record AuthorResponseWithRoleDTO(
        Long id,
        String name,
        String slug,
        String role
) {}