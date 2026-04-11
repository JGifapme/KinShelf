package com.kinshelf.dto.category;

import jakarta.validation.constraints.NotBlank;

public record CategoryCreateDTO(
        @NotBlank
        String name

) {}
