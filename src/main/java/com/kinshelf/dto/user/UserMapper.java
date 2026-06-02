package com.kinshelf.dto.user;

import com.kinshelf.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
/// Classe utilitaire pour convertir les objets User en DTO et inversément
public class UserMapper {
    private final PasswordEncoder passwordEncoder;

    /// Convertit un objet User en DTO UserResponseDTO
    public UserResponseDTO toDTO(User user) {
        if (user == null) {
            return null;
        }

        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getSlug(),
                user.getDateOfBirth()
        );
    }
    /// Convertit un DTO UserCreateDTO en objet User
    public User toEntity(UserCreateDTO dto) {
        if (dto == null) {
            return null;
        }

        return User.builder()
                .username(dto.username())
                .dateOfBirth(dto.dateOfBirth())
                .email(dto.email())
                .password(dto.password()) // à modifier avec spring sécurity quand vu au cours
                .userRoles(dto.userRoles())
                .build();
    }
    /// Met à jour un objet User avec les données du DTO
    public void updateEntity(User user, UserUpdateDTO dto) {
        if (user == null || dto == null) {
            return;
        }
        if (dto.username() != null) {
            user.setUsername(dto.username());
        }
        if (dto.dateOfBirth() != null) {
            user.setDateOfBirth(dto.dateOfBirth());
        }
        //à voir si il ne faut pas faire une autre méthode pour mettre les infos sensible à jour.
        if (dto.email() != null) {
            user.setEmail(dto.email());
        }
        if (dto.password() != null && !dto.password().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.password()));
        }
    }
}
