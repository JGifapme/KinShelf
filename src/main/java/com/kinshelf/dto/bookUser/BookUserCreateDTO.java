package com.kinshelf.dto.bookUser;

import jakarta.validation.constraints.*;

public record BookUserCreateDTO(
        Boolean isOwn,
        Boolean isRead,
        Boolean isInterested,

        @Min(value = 0, message = "La note doit être comprise entre 0 et 5.")
        @Max(5)
        Integer rating,
        String comment

) {}
