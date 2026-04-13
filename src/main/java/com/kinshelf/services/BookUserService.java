package com.kinshelf.services;

import com.kinshelf.dto.bookUser.*;
import com.kinshelf.entities.Book;
import com.kinshelf.entities.BookUser;
import com.kinshelf.entities.User;
import com.kinshelf.exceptions.NotFoundException;
import com.kinshelf.repositories.BookRepository;
import com.kinshelf.repositories.BookUserRepository;
import com.kinshelf.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookUserService {

    private final BookUserRepository bookUserRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public BookUserResponseDTO create(BookUserCreateDTO dto) {

        //Vérifier que l'association est unique : dans le repo faire un findByBookIdAndUserId puis vérifier

        Book book = bookRepository.findById(dto.bookId())
                .orElseThrow(() -> new NotFoundException("Livre introuvable"));

        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new NotFoundException("Utilisateur introuvable"));

        BookUser bu = BookUser.builder()
                .book(book)
                .user(user)
                .isOwn(dto.isOwn())
                .isRead(dto.isRead())
                .rating(dto.rating())
                .comment(dto.comment())
                .build();

        return BookUserMapper.toDTO(bookUserRepository.save(bu));
    }

    public List<BookUserResponseDTO> findAll() {
        return bookUserRepository.findAll()
                .stream()
                .map(BookUserMapper::toDTO)
                .toList();
    }

    public BookUserResponseDTO findById(Integer id) {
        BookUser bu = bookUserRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("relation livre/utilisateur introuvable pour l'id : " + id));

        return BookUserMapper.toDTO(bu);
    }

    public BookUserResponseDTO update(Integer id, BookUserCreateDTO dto) {

        BookUser bu = bookUserRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("relation livre/utilisateur introuvable pour l'id : " + id));

        BookUserMapper.updateEntity(bu, dto);

        return BookUserMapper.toDTO(bookUserRepository.save(bu));
    }

    public void delete(Integer id) {
        if (!bookUserRepository.existsById(id)) {
            throw new NotFoundException("relation livre/utilisateur introuvable pour l'id : " + id);
        }

        bookUserRepository.deleteById(id);
    }

    public List<BookUserResponseDTO> findByUser(Integer userId) {
        return bookUserRepository.findByUserId(userId)
                .stream()
                .map(BookUserMapper::toDTO)
                .toList();
    }
}
