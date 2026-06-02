package com.kinshelf.dto.book;

import com.kinshelf.dto.author.AuthorResponseWithRoleDTO;
import com.kinshelf.dto.bookUser.BUWithUserNameDTO;
import com.kinshelf.dto.bookUser.BookUserResponseDTO;
import com.kinshelf.dto.category.CategoryResponseDTO;
import com.kinshelf.dto.genre.GenreResponseDTO;
import com.kinshelf.dto.publisher.PublisherResponseDTO;
import com.kinshelf.dto.series.SeriesResponseDTO;

import java.time.LocalDate;
import java.util.List;
///  DTO complet pour la page détails d'un livre avec toutes ses infos + les interactions des utilisateurs
public record BookWithUsersInputDTO (
        Long id,
        String title,
        String slug,
        String isbn,
        String description,
        Integer numberOfPages,
        String coverUrl,
        LocalDate publicationDate,

        PublisherResponseDTO publisher,
        CategoryResponseDTO category,
        SeriesResponseDTO series,

        List<AuthorResponseWithRoleDTO> authors,
        List<GenreResponseDTO> genres,
        List<BUWithUserNameDTO> bookUsers
){
}
