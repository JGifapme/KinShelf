package com.kinshelf.dto.bookUser;

import com.kinshelf.dto.category.CategoryCreateDTO;
import com.kinshelf.entities.Book;
import com.kinshelf.entities.BookUser;
import com.kinshelf.entities.Category;
import com.kinshelf.entities.User;
import org.springframework.stereotype.Component;
/// Classe utilitaire pour convertir les objets BookUser (les intéractions entre les utilisateurs et les livres) en DTO et inversément
@Component
public class BookUserMapper {
/// Convertit un objet BookUser en DTO BookUserResponseDTO
    public static BookUserResponseDTO toDTO(BookUser bu) {
        if (bu == null) {
            return null;
        }

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
    /// Convertit un objet BookUser en DTO BUWithUserNameDTO
    public static BUWithUserNameDTO toDTOWithUserName(BookUser bu) {
        if (bu == null) {
            return null;
        }

        return new BUWithUserNameDTO(
                bu.getBook().getSlug(),
                bu.getUser().getSlug(),
                bu.getUser().getUsername(),
                bu.getIsOwn(),
                bu.getIsRead(),
                bu.getIsInterested(),
                bu.getRating(),
                bu.getComment()
        );
    }
    /// Convertit un DTO BookUserResponseDTO en objet BookUser
    public BookUser toEntity(BookUserCreateDTO dto, Book book, User user) {
        if (dto == null) {
            return null;
        }

        return BookUser.builder()
                .book(book)
                .user(user)
                .isOwn(dto.isOwn())
                .isRead(dto.isRead())
                .isInterested(dto.isInterested())
                .rating(dto.rating())
                .comment(dto.comment())
                .build();
    }
    /// Méthode qui met à jour l'objet BookUser depuis le DTO BookUserCreateDTO
    public static void updateEntity(BookUser bu, BookUserCreateDTO dto) {
        if (bu == null || dto == null) {
            return;
        }
        if (dto.isOwn() != null){
            bu.setIsOwn(dto.isOwn());
        }
        if (dto.isRead() != null){
            bu.setIsRead(dto.isRead());
        }
        if (dto.isInterested() != null){
            bu.setIsInterested(dto.isInterested());
        }
        if (dto.rating() != null){
            bu.setRating(dto.rating());
        }
        if (dto.comment() != null){
            bu.setComment(dto.comment());
        }
    }
}
