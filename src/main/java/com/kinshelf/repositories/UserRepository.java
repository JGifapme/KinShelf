package com.kinshelf.repositories;

import com.kinshelf.entities.Author;
import com.kinshelf.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findBySlug(String slug);
    boolean existsBySlug(String slug);
}
