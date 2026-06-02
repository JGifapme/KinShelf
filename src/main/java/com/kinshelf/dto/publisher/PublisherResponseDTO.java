package com.kinshelf.dto.publisher;
/// DTO qui retourne l'id, le slug et le nom d'un éditeur
public record PublisherResponseDTO(
        Long id,
        String name,
        String slug
) {}
