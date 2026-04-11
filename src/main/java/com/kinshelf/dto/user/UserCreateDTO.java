package com.kinshelf.dto.user;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record UserCreateDTO(

        @NotBlank
        String firstName,

        @NotBlank
        String lastName,

        @NotNull
        LocalDate dateOfBirth,

        @Email
        @NotBlank
        String email,

        @NotBlank
        @Size(min = 6)
        String password

) {}