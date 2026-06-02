package com.kinshelf.dto.genre;
/// DTO qui retourne l'id, le slug et le nom d'un genre
public record GenreResponseDTO(
        Long id,
        String name,
        String slug
) {}
