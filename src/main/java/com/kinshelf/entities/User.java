package com.kinshelf.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long id;

    @Column(name = "username", nullable = false, length = 150, unique = true)
    private String username;

    @Column(nullable = false, length = 150, unique = true)
    private String slug;

    @Column(name = "dob", nullable = false)
    private LocalDate dateOfBirth;

    @Column(length = 255, unique = true)
    private String email;

    @Column(length = 255)
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookUser> bookUsers = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "owner")
    private List<Loan> ownedLoans = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "borrower")
    private List<Loan> borrowedLoans = new ArrayList<>();

    //Pour Spring security : roles
    @Builder.Default
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private List<String> userRoles = new ArrayList<>(List.of("USER"));

    public void setRoles(List<String> roleUser) {
        this.userRoles = roleUser;
    }
}