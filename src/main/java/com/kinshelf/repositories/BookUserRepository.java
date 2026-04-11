package com.kinshelf.repositories;

import com.kinshelf.entities.BookUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookUserRepository extends JpaRepository<BookUser, Long> {
}
