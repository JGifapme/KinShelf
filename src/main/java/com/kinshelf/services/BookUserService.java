package com.kinshelf.services;

import com.kinshelf.dto.book.BookResponseDTO;
import com.kinshelf.dto.bookUser.*;
import com.kinshelf.dto.user.UserResponseDTO;
import com.kinshelf.entities.Book;
import com.kinshelf.entities.BookUser;
import com.kinshelf.entities.BookUserId;
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
                .isInterested(dto.isInterested())
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

    public BookUserResponseDTO findById(BookUserId id) {
        BookUser bu = bookUserRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("relation livre/utilisateur introuvable pour l'id : " + id));

        return BookUserMapper.toDTO(bu);
    }

    public BookUserResponseDTO update(BookUserId id, BookUserCreateDTO dto) {

        BookUser bu = bookUserRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("relation livre/utilisateur introuvable pour l'id : " + id));

        BookUserMapper.updateEntity(bu, dto);

        return BookUserMapper.toDTO(bookUserRepository.save(bu));
    }

    public void delete(BookUserId id) {
        if (!bookUserRepository.existsById(id)) {
            throw new NotFoundException("relation livre/utilisateur introuvable pour l'id : " + id);
        }

        bookUserRepository.deleteById(id);
    }

    public List<BookUserResponseDTO> findByUser(Long userId) {
        return bookUserRepository.findByUserId(userId)
                .stream()
                .map(BookUserMapper::toDTO)
                .toList();
    }

    // Lecture : tout le monde peut voir qui possède quoi
    //sur un livre on peut voir qui le possède
    public List<UserResponseDTO> getOwnersListByBook(Long bookId) {
        return null;
    }

    // sur un utilisateur quels livres il a
    public List<BookResponseDTO> getBooksListByOwner(Long UserId) {
        return null;
    }

    // Modif : Seul le propriétaire (l'user identifié) peut modifier
    public void updateMyBookStatus(Long bookId, Long currentUserId, BookUserCreateDTO dto) {
        BookUserId id = new BookUserId(bookId, currentUserId);
        BookUser entry = bookUserRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ce n'est pas votre fiche !")); // Créer une autre exception

        bookUserRepository.save(entry);
    }
}
