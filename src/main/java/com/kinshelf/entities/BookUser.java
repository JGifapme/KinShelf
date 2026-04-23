package com.kinshelf.entities;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

@Entity
@Table(name = "books_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookUser {

    @EmbeddedId
    private BookUserId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("bookId")
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "is_own")
    private Boolean isOwn;

    @Column(name = "is_read")
    private Boolean isRead;

    @Column(name = "is_interested")
    private Boolean isInterested;

    @Min(0)
    @Max(5)
    private Integer rating;

    private String comment;
}
