package com.kinshelf.dto.bookUser;

import com.kinshelf.entities.BookUserId;
/// DTO qui renvoie la relation Book <-> User (Lu, possédés, Wish, note, commentaire)
/// avec le titre du livre et le nom de l'utilisateur
public record BUWithUserNameDTO(
        String bookSlug,
        String userSlug,
        String username,
        Boolean isOwn,
        Boolean isRead,
        Boolean isInterested,
        Integer rating,
        String comment
) {
}
