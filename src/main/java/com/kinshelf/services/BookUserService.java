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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
/// Service chargé de gérer les relations entre les utilisateurs et les livres.
public class BookUserService {

    private final BookUserRepository bookUserRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;


//    public List<BookUserResponseDTO> findAll() {
//        return bookUserRepository.findAll()
//                .stream()
//                .map(BookUserMapper::toDTO)
//                .toList();
//    }
//
    public BookUserResponseDTO findById(BookUserId id) {
        BookUser bu = bookUserRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("relation livre/utilisateur introuvable pour l'id : " + id));

        return BookUserMapper.toDTO(bu);
    }

//    @Transactional
//    public BookUserResponseDTO update(BookUserId id, BookUserCreateDTO dto) {
//
//        BookUser bu = bookUserRepository.findById(id)
//                .orElseThrow(() -> new NotFoundException("relation livre/utilisateur introuvable pour l'id : " + id));
//
//        BookUserMapper.updateEntity(bu, dto);
//
//        return BookUserMapper.toDTO(bookUserRepository.save(bu));
//    }

    @Transactional
    public BookUserResponseDTO upCreate(BookUserId id, BookUserCreateDTO dto) {
        //on vérifie que le livre et l'utilisateur existe :
        Book book = bookRepository.findById(id.getBookId())
                .orElseThrow(() -> new NotFoundException(
                        "Livre introuvable pour l'id : " + id.getBookId()));

        User user = userRepository.findById(id.getUserId())
                .orElseThrow(() ->new NotFoundException(
                        "Utilisateur introuvable pour l'id : " + id.getUserId()));
        // On cherche si la relation Book/user existe déjà
        Optional<BookUser> bookUserExiste = bookUserRepository.findById(id);

        BookUser bu;
        if (bookUserExiste.isPresent()) {
            // si oui on le récupère
            bu = bookUserExiste.get();
        } else {
            // sinon on créer un nouveau BookUser
            bu = new BookUser();
            bu.setId(id); // On créer l'id unique bookUser
            // on set le book et le user
            bu.setBook(book);
            bu.setUser(user);
        }
        // Dans tout les cas on update et on save qu'il existe ou non
        BookUserMapper.updateEntity(bu, dto);
        return BookUserMapper.toDTO(bookUserRepository.save(bu));
    }
//    public List<BookUserResponseDTO> findByUser(Long userId) {
//        return bookUserRepository.findByUserId(userId)
//                .stream()
//                .map(BookUserMapper::toDTO)
//                .toList();
//    }
}
