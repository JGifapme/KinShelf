package com.kinshelf.dto.bookUser;

import com.kinshelf.entities.BookUserId;

public record BUWithUserNameDTO(
        BookUserId id,
        Long bookId,
        Long userId,
        String userName,
        Boolean isOwn,
        Boolean isRead,
        Boolean isInterested,
        Integer rating,
        String comment
) {
}
