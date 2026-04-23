package com.kinshelf.dto.bookUser;

import com.kinshelf.entities.BookUserId;

public record BookUserResponseDTO(

        BookUserId id,
        Long bookId,
        String bookTitle,
        Long userId,
        Boolean isOwn,
        Boolean isRead,
        Boolean isInterested,
        Integer rating,
        String comment

) {}
