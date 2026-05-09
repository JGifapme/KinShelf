package com.kinshelf.dto.category;

import jakarta.validation.constraints.NotBlank;

public record CategoryCreateDTO(
        @NotBlank(message = "Le nom est obligatoire")
        String name

) {}
