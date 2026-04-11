package com.kinshelf.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "genres")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_genre")
    private Integer id;

    @Column(nullable = false, length = 150)
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "genres")
    private List<Book> books;
}