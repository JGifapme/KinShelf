package com.kinshelf.repositories;

import com.kinshelf.entities.BookUser;
import com.kinshelf.entities.BookUserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookUserRepository extends JpaRepository<BookUser, BookUserId> {
    List<BookUser> findByUserId(Long userId);
    Optional<BookUser> findByBookIdAndUserId(Long bookId, Long userId);
}
