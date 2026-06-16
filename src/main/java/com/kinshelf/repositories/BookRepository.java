package com.kinshelf.repositories;

import com.kinshelf.entities.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    Page<Book> findAllByOrderByTitleAsc(Pageable pageable);
    @Query("""
    SELECT DISTINCT b FROM Book b 
    LEFT JOIN b.bookAuthors ba 
    LEFT JOIN ba.author a 
    LEFT JOIN b.series s
    LEFT JOIN b.genres g
    LEFT JOIN b.category c
    LEFT JOIN b.publisher p
    LEFT JOIN b.bookUsers buUser WITH (buUser.user.id = :userId)
    LEFT JOIN b.bookUsers buOwner WITH (buOwner.user.slug = :userSlug)
    LEFT JOIN b.bookUsers buAll
    WHERE (:search IS NULL 
        OR LOWER(b.title) LIKE LOWER(CONCAT('%', :search, '%')) 
        OR LOWER(s.name) LIKE LOWER(CONCAT('%', :search, '%'))
        OR LOWER(a.name) LIKE LOWER(CONCAT('%', :search, '%')))
        AND (:genreSlug IS NULL OR g.slug = :genreSlug)
        AND (:categorySlug IS NULL OR c.slug = :categorySlug)
        AND (:publisherSlug IS NULL OR p.slug = :publisherSlug)
        AND (:userSlug IS NULL OR buOwner.isOwn = true)
        AND (:allUsers IS NULL OR buAll.isOwn = true)
        AND (:userIsRead IS NULL OR (
            :userIsRead = true AND buUser.isRead = true
            OR :userIsRead = false AND (buUser.isRead = false OR buUser.isRead IS NULL)
        ))
        AND (:userIsInterested IS NULL OR buUser.isInterested = true)
""")
    Page<Book> findBookSearch(
            @Param("search") String search,
            @Param("genreSlug") String genreSlug,
            @Param("userSlug") String userSlug,
            @Param("categorySlug") String categorySlug,
            @Param("allUsers") String allUsers,
            @Param("publisherSlug") String publisherSlug,
            @Param("userId") Long userId,
            @Param("userIsRead") Boolean userIsRead,
            @Param("userIsInterested") Boolean userIsInterested,
            Pageable pageable);

    @Query("""
    SELECT DISTINCT b FROM Book b 
    LEFT JOIN b.bookAuthors ba 
    LEFT JOIN b.bookUsers buFrom WITH (buFrom.user.slug = :userSlug)
    LEFT JOIN b.bookUsers buYou WITH (buYou.user.id = :userId)
    WHERE (buFrom.isOwn = true)
        AND (buYou.isInterested = true)
""")
    List<Book> findWishBooksFromUserLibrary(
            @Param("userSlug") String userSlug,
            @Param("userId") Long userId);

    @Query("""
    SELECT DISTINCT b FROM Book b 
    LEFT JOIN b.bookAuthors ba 
    LEFT JOIN b.bookUsers buFrom WITH (buFrom.user.id = :userId)
    LEFT JOIN b.bookUsers buWishBy WITH (buWishBy.user.slug = :userSlug)
    WHERE (buFrom.isOwn = true)
        AND (buWishBy.isInterested = true)
""")
    List<Book> findWishedBooksFromUserFromMyLibrary(
            @Param("userSlug") String userSlug,
            @Param("userId") Long userId);

}
