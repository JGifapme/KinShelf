package com.kinshelf.services;

import com.kinshelf.dto.bookUser.BookUserCreateDTO;
import com.kinshelf.entities.Book;
import com.kinshelf.entities.BookUser;
import com.kinshelf.entities.User;
import com.kinshelf.repositories.BookRepository;
import com.kinshelf.repositories.BookUserRepository;
import com.kinshelf.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookUserService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookUserRepository bookUserRepository;

    public BookUser create(BookUserCreateDTO dto) {

        Book book = bookRepository.findById(dto.bookId()).orElseThrow();
        User user = userRepository.findById(dto.userId()).orElseThrow();

        BookUser bu = BookUser.builder()
                .book(book)
                .user(user)
                .isOwn(dto.isOwn())
                .isRead(dto.isRead())
                .rating(dto.rating())
                .comment(dto.comment())
                .build();

        return bookUserRepository.save(bu);
    }
}
