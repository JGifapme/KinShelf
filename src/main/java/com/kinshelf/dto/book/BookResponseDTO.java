package com.kinshelf.dto.book;

import com.kinshelf.dto.author.AuthorResponseDTO;
import com.kinshelf.dto.author.AuthorResponseWithRoleDTO;
import com.kinshelf.dto.category.CategoryResponseDTO;
import com.kinshelf.dto.genre.GenreResponseDTO;
import com.kinshelf.dto.publisher.PublisherResponseDTO;
import com.kinshelf.dto.series.SeriesResponseDTO;
import com.kinshelf.entities.Category;
import com.kinshelf.entities.Publisher;
import com.kinshelf.entities.Series;

import java.time.LocalDate;
import java.util.List;

public record BookResponseDTO(

        Long id,
        String title,
        String description,
        Integer numberOfPages,
        String coverUrl,
        LocalDate publicationDate,

        PublisherResponseDTO publisher,
        CategoryResponseDTO category,
        SeriesResponseDTO series,

        List<AuthorResponseWithRoleDTO> authors,
        List<GenreResponseDTO> genres

) {}
