package com.kinshelf.repositories;

import com.kinshelf.entities.Author;
import com.kinshelf.entities.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface SeriesRepository extends JpaRepository<Series, Long> {
    Optional<Series> findBySlug(String slug);
    boolean existsBySlug(String slug);
    List<Series> findAllByOrderByNameAsc();
}
