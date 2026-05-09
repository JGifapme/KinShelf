package com.kinshelf.dto.genre;

import jakarta.validation.constraints.NotBlank;

public record GenreCreateDTO(
        @NotBlank(message = "Le nom est obligatoire")
        String name
) {}
