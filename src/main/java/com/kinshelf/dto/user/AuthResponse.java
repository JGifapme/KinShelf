package com.kinshelf.dto.user;

import java.time.Instant;
import java.util.List;
/// Retourne le message de succès à l'authentification avec les roles de l'utilisateur connecté
public record AuthResponse(
        String message,
        List<String> roles
) {
}
