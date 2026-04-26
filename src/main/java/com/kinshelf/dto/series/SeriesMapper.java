package com.kinshelf.dto.series;

import com.kinshelf.dto.book.BookTitleAndImgDTO;
import com.kinshelf.dto.series.SeriesWithBooksDTO;
import com.kinshelf.entities.Book;
import com.kinshelf.entities.Series;
import com.kinshelf.entities.Series;

import java.util.ArrayList;
import java.util.List;

public class SeriesMapper {

    public static SeriesResponseDTO toDTO(Series series) {
        if (series == null) {
            return null;
        }

        return new SeriesResponseDTO(
                series.getId(),
                series.getName(),
                series.getStatus()
        );
    }

    public static Series toEntity(SeriesCreateDTO dto) {
        if (dto == null){
            return null;
        }

        return Series.builder()
                .name(dto.name())
                .status(dto.status())
                .build();
    }

    public static void updateEntity(Series series, SeriesCreateDTO dto) {
        if (series == null || dto == null) {
            return;
        }

        if (dto.name() != null) {
            series.setName(dto.name());
        }
        if (dto.status() != null) {
            series.setStatus(dto.status());
        }
    }

    public static SeriesWithBooksDTO toDTOSeriesWithBooks(Series series) {
        if (series == null) {
            return null;
        }
        List<BookTitleAndImgDTO> bookList = mapBooks(series);
        return new SeriesWithBooksDTO(
                series.getId(),
                series.getName(),
                series.getStatus(),
                bookList
        );
    }
    private static List<BookTitleAndImgDTO> mapBooks(Series series) {
        List<BookTitleAndImgDTO> bookTitles = new ArrayList<>();

        if (series.getBooks() == null) {
            return bookTitles;
        }
        for (Book book : series.getBooks()) {
            BookTitleAndImgDTO dto = new BookTitleAndImgDTO(
                    book.getId(),
                    book.getTitle(),
                    book.getCoverUrl()
            );
            bookTitles.add(dto);
        }
        return bookTitles;
    }
}
