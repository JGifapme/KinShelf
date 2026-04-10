package com.kinshelf.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long id;

    @Column(name = "fname", nullable = false, length = 75)
    private String firstName;

    @Column(name = "lname", nullable = false, length = 75)
    private String lastName;

    @Column(name = "dob", nullable = false)
    private LocalDate dateOfBirth;

    @Column(length = 255, unique = true)
    private String email;

    @Column(length = 255)
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookUser> bookUsers;

    @JsonIgnore
    @OneToMany(mappedBy = "owner")
    private List<Loan> ownedLoans;

    @JsonIgnore
    @OneToMany(mappedBy = "borrower")
    private List<Loan> borrowedLoans;
}