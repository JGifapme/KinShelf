package com.kinshelf.dto.author;

import jakarta.validation.constraints.NotBlank;

public record AuthorCreateDTO(

        @NotBlank
        String firstName,

        @NotBlank
        String lastName

) {}
