package com.kinshelf.dto.bookUser;

import com.kinshelf.entities.BookUser;

public class BookUserMapper {

    public static BookUserResponseDTO toDTO(BookUser bu) {
        if (bu == null) return null;

        return new BookUserResponseDTO(
                bu.getId(),
                bu.getBook().getId(),
                bu.getBook().getTitle(),
                bu.getUser().getId(),
                bu.getIsOwn(),
                bu.getIsRead(),
                bu.getIsInterested(),
                bu.getRating(),
                bu.getComment()
        );
    }

    public static void updateEntity(BookUser bu, BookUserCreateDTO dto) {
        if (bu == null || dto == null) return;

        bu.setIsOwn(dto.isOwn());
        bu.setIsRead(dto.isRead());
        bu.setIsRead(dto.isInterested());
        bu.setRating(dto.rating());
        bu.setComment(dto.comment());
    }
}
