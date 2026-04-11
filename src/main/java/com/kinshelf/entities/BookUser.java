package com.kinshelf.entities;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

@Entity
@Table(
        name = "books_users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"book_id", "user_id"})
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_book_user")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "is_own")
    private Boolean isOwn;

    @Column(name = "is_read")
    private Boolean isRead;

    @Min(0)
    @Max(5)
    private Integer rating;

    private String comment;
}
