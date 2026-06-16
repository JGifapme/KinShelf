package com.kinshelf.repositories;

import com.kinshelf.entities.Author;
import com.kinshelf.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre,Long> {
    Optional<Genre> findBySlug(String slug);
    boolean existsBySlug(String slug);
    List<Genre> findAllByOrderByNameAsc();
}
