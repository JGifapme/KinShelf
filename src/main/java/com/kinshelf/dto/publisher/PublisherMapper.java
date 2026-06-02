package com.kinshelf.dto.publisher;

import com.kinshelf.dto.book.BookTitleAndImgDTO;
import com.kinshelf.dto.publisher.PublisherWithBooksDTO;
import com.kinshelf.entities.Book;
import com.kinshelf.entities.Publisher;
import com.kinshelf.entities.Publisher;

import java.util.ArrayList;
import java.util.List;
/// Classe utilitaire pour convertir les objets Publisher (éditeur) en DTO et inversément
public class PublisherMapper {

    /// Convertit un objet Publisher (éditeur) en DTO PublisherResponseDTO
    public static PublisherResponseDTO toDTO(Publisher publisher) {
        if (publisher == null){
            return null;
        }

        return new PublisherResponseDTO(
                publisher.getId(),
                publisher.getName(),
                publisher.getSlug()
        );
    }

    /// Convertit un DTO PublisherCreateDTO en objet Publisher
    public static Publisher toEntity(PublisherCreateDTO dto) {
        if (dto == null) {
            return null;
        }

        return Publisher.builder()
                .name(dto.name())
                .build();
    }
    /// Met à jour un objet Publisher avec les données du DTO
    public static void updateEntity(Publisher publisher, PublisherCreateDTO dto) {
        if (publisher == null || dto == null){
            return;
        }
        if (dto.name() != null) {
            publisher.setName(dto.name());
        }
    }
    /// Convertit un objet Publisher en DTO avec sa liste de livres associés
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
    /// Récupère la liste des livres associés a l'éditeur en DTO simplifié juste le titre, le slug et l'image
    private static List<BookTitleAndImgDTO> mapBooks(Publisher publisher) {
        List<BookTitleAndImgDTO> bookTitles = new ArrayList<>();

        if (publisher.getBooks() == null) {
            return bookTitles;
        }
        for (Book book : publisher.getBooks()) {
            BookTitleAndImgDTO dto = new BookTitleAndImgDTO(
                    book.getId(),
                    book.getTitle(),
                    book.getSlug(),
                    book.getCoverUrl()
            );
            bookTitles.add(dto);
        }
        return bookTitles;
    }
}
