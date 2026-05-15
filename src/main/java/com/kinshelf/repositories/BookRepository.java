package com.kinshelf.repositories;

import com.kinshelf.entities.Author;
import com.kinshelf.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findBySlug(String slug);
    boolean existsBySlug(String slug);
    boolean existsByIsbn(String isbn);
    List<Book> findAllByOrderByTitleAsc();
    @Query("""
    SELECT DISTINCT b FROM Book b 
    LEFT JOIN b.bookAuthors ba 
    LEFT JOIN ba.author a 
    LEFT JOIN b.series s
    LEFT JOIN b.genres g
    LEFT JOIN b.category c
    LEFT JOIN b.bookUsers bu 
    LEFT JOIN bu.user u 
    WHERE (:search IS NULL 
        OR LOWER(b.title) LIKE LOWER(CONCAT('%', :search, '%')) 
        OR LOWER(s.name) LIKE LOWER(CONCAT('%', :search, '%'))
        OR LOWER(CONCAT(a.firstName, ' ', a.lastName)) LIKE LOWER(CONCAT('%', :search, '%')))
        AND (:genreSlug IS NULL OR g.slug = :genreSlug)
        AND (:categorySlug IS NULL OR c.slug = :categorySlug)
        AND (:userSlug IS NULL OR (bu.isOwn = true AND u.slug = :userSlug))
        AND (:allUsers IS NULL OR bu.isOwn = true)
    ORDER BY b.title ASC
""")
    List<Book> findBookSearch(
            @Param("search") String search,
            @Param("genreSlug") String genreSlug,
            @Param("userSlug") String userSlug,
            @Param("categorySlug") String categorySlug,
            @Param("allUsers") String allUsers

    );
}
