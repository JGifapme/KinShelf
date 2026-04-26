package com.kinshelf.dto.bookUser;

import jakarta.validation.constraints.*;

public record BookUserCreateDTO(
        Boolean isOwn,
        Boolean isRead,
        Boolean isInterested,

        @Min(0)
        @Max(5)
        Integer rating,
        String comment

) {}
