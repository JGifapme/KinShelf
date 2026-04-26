package com.kinshelf.dto.user;

import com.kinshelf.entities.User;

public class UserMapper {

    public static UserResponseDTO toDTO(User user) {
        if (user == null) {
            return null;
        }

        return new UserResponseDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getDateOfBirth()
        );
    }

    public static User toEntity(UserCreateDTO dto) {
        if (dto == null) {
            return null;
        }

        return User.builder()
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .dateOfBirth(dto.dateOfBirth())
                .email(dto.email())
                .password(dto.password()) // à modifier avec spring sécurity quand vu au cours
                .build();
    }

    public static void updateEntity(User user, UserCreateDTO dto) {
        if (user == null || dto == null) {
            return;
        }

        if (dto.firstName() != null) {
            user.setFirstName(dto.firstName());
        }
        if (dto.lastName() != null) {
            user.setLastName(dto.lastName());
        }
        if (dto.dateOfBirth() != null) {
            user.setDateOfBirth(dto.dateOfBirth());
        }
        //à voir si il ne faut pas faire une autre méthode pour mettre les infos sensible à jour.
        if (dto.email() != null) {
            user.setEmail(dto.email());
        }
        if (dto.password() != null) {
            user.setPassword(dto.password());
        }
    }
}
