package com.kinshelf.dto.genre;

import jakarta.validation.constraints.NotBlank;

public record GenreCreateDTO(
        @NotBlank
        String name
) {}
