package com.kinshelf.dto.bookUser;

import com.kinshelf.entities.BookUserId;
/// DTO qui renvoie la relation Book <-> User (Lu, possédés, Wish) avec le titre du livre
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
