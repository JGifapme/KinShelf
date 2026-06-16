package com.kinshelf.repositories;

import com.kinshelf.entities.Author;
import com.kinshelf.entities.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    Optional<Publisher> findBySlug(String slug);
    boolean existsBySlug(String slug);
    List<Publisher> findAllByOrderByNameAsc();
}
