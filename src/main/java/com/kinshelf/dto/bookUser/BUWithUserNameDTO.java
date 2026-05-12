package com.kinshelf.dto.bookUser;

import com.kinshelf.entities.BookUserId;

public record BUWithUserNameDTO(
        String bookSlug,
        String userSlug,
        String userName,
        Boolean isOwn,
        Boolean isRead,
        Boolean isInterested,
        Integer rating,
        String comment
) {
}
