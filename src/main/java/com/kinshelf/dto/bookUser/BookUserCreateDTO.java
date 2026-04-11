package com.kinshelf.dto.bookUser;

import jakarta.validation.constraints.*;

public record BookUserCreateDTO(

        @NotNull
        Integer bookId,

        @NotNull
        Integer userId,

        Boolean isOwn,

        Boolean isRead,

        @Min(0)
        @Max(5)
        Integer rating,

        String comment

) {}
