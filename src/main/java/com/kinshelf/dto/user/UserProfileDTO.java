package com.kinshelf.dto.user;

import com.kinshelf.dto.book.BookTitleAndImgDTO;

import java.time.LocalDate;
import java.util.List;

public record UserProfileDTO(
        Long id,
        String username,
        String slug,
        LocalDate dateOfBirth,
        Integer age,
        Integer nbrOfBooks
) {
}
