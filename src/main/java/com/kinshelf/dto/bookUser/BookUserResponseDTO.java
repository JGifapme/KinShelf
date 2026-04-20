package com.kinshelf.dto.bookUser;

public record BookUserResponseDTO(

        Long id,
        Long bookId,
        String bookTitle,
        Long userId,
        Boolean isOwn,
        Boolean isRead,
        Integer rating,
        String comment

) {}
