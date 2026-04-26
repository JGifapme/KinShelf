package com.kinshelf.dto.category;

import com.kinshelf.dto.book.BookTitleAndImgDTO;
import com.kinshelf.dto.book.BookTitleDTO;
import com.kinshelf.entities.Book;
import com.kinshelf.entities.BookAuthor;
import com.kinshelf.entities.Category;

import java.util.ArrayList;
import java.util.List;


public class CategoryMapper {

    public static CategoryResponseDTO toDTO(Category category) {
        if (category == null) {
            return null;
        }

        return new CategoryResponseDTO(
                category.getId(),
                category.getName()
        );
    }

    public static Category toEntity(CategoryCreateDTO dto) {
        if (dto == null) {
            return null;
        }

        return Category.builder()
                .name(dto.name())
                .build();
    }

    public static void updateEntity(Category category, CategoryCreateDTO dto) {
        if (category == null || dto == null) {
            return;
        }
        if (dto.name() != null) {
            category.setName(dto.name());
        }
    }

    public static CategoryWithBooksDTO toDTOCatWithBooks(Category category) {
        if (category == null) {
            return null;
        }
        List<BookTitleAndImgDTO> bookList = mapBooks(category);
        return new CategoryWithBooksDTO(
                category.getId(),
                category.getName(),
                bookList
        );
    }
    private static List<BookTitleAndImgDTO> mapBooks(Category category) {
        List<BookTitleAndImgDTO> bookTitles = new ArrayList<>();

        if (category.getBooks() == null) {
            return bookTitles;
        }
        for (Book book : category.getBooks()) {
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