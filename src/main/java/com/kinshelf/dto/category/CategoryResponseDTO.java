package com.kinshelf.dto.category;
/// DTO qui retourne l'id, le slug et le nom d'une catégorie
public record CategoryResponseDTO(
        Long id,
        String name,
        String slug
) {}
