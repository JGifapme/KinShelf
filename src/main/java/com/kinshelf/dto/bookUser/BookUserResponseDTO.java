package com.kinshelf.dto.bookUser;

public record BookUserResponseDTO(

        Integer id,
        Integer bookId,
        String bookTitle,
        Integer userId,
        Boolean isOwn,
        Boolean isRead,
        Integer rating,
        String comment

) {}
