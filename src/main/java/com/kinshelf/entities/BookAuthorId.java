package com.kinshelf.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BookAuthorId implements Serializable {

    @Column(name = "book_id")
    private Integer bookId;

    @Column(name = "author_id")
    private Integer authorId;
}
