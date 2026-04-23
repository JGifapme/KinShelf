package com.kinshelf.dto.bookUser;

import jakarta.validation.constraints.*;

public record BookUserCreateDTO(

        @NotNull
        Long bookId,

        @NotNull
        Long userId,

        Boolean isOwn,

        Boolean isRead,

        Boolean isInterested,

        @Min(0)
        @Max(5)
        Integer rating,

        String comment

) {}
