package com.kinshelf.dto.user;

import java.util.List;
///  Retourne le message de succès de connexion de l'utilisateur, ses roles et son JWT token
/// stocké dans les cookies
public record AuthResult(
        String message,
        List<String> roles,
        String jwtToken
) {
}
