package com.kinshelf.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "series")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Series {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_series")
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(name = "nb_tomes")
    private Integer numberOfVolumes;

    @Enumerated(EnumType.STRING)
    private SeriesStatus status;

    @JsonIgnore
    @OneToMany(mappedBy = "series", fetch = FetchType.LAZY)
    private List<Book> books;
}