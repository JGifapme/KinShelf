package com.kinshelf.dto.user;

import com.kinshelf.dto.book.BookTitleAndImgDTO;

import java.time.LocalDate;
import java.util.List;
/// DTO qui retourne toutes les infos de l'utilisateur connecté pour sa page profil
public record UserProfileDTO(
        Long id,
        String username,
        String slug,
        String email,
        LocalDate dateOfBirth,
        Integer age,
        Integer nbrOfBooks
) {
}
