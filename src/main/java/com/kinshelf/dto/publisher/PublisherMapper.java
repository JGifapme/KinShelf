package com.kinshelf.dto.publisher;

import com.kinshelf.dto.book.BookTitleAndImgDTO;
import com.kinshelf.dto.publisher.PublisherWithBooksDTO;
import com.kinshelf.entities.Book;
import com.kinshelf.entities.Publisher;
import com.kinshelf.entities.Publisher;

import java.util.ArrayList;
import java.util.List;

public class PublisherMapper {

    public static PublisherResponseDTO toDTO(Publisher publisher) {
        if (publisher == null){
            return null;
        }

        return new PublisherResponseDTO(
                publisher.getId(),
                publisher.getName()
        );
    }

    public static Publisher toEntity(PublisherCreateDTO dto) {
        if (dto == null) {
            return null;
        }

        return Publisher.builder()
                .name(dto.name())
                .build();
    }

    public static void updateEntity(Publisher publisher, PublisherCreateDTO dto) {
        if (publisher == null || dto == null){
            return;
        }
        if (dto.name() != null) {
            publisher.setName(dto.name());
        }
    }
    public static PublisherWithBooksDTO toDTOPublisherWithBooks(Publisher publisher) {
        if (publisher == null) {
            return null;
        }
        List<BookTitleAndImgDTO> bookList = mapBooks(publisher);
        return new PublisherWithBooksDTO(
                publisher.getId(),
                publisher.getName(),
                bookList
        );
    }
    private static List<BookTitleAndImgDTO> mapBooks(Publisher publisher) {
        List<BookTitleAndImgDTO> bookTitles = new ArrayList<>();

        if (publisher.getBooks() == null) {
            return bookTitles;
        }
        for (Book book : publisher.getBooks()) {
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
