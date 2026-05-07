package com.kinshelf.repositories;

import com.kinshelf.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findBySlug(String slug);
    boolean existsBySlug(String slug);
}
