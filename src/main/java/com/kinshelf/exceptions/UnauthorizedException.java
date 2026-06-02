package com.kinshelf.exceptions;
/// Exception levée lorsqu'un utilisateur tente d'accéder à une ressource
/// sans être authentifié ou avec des informations d'authentification invalides.
/// Erreur HTTP 401 (Unauthorized).
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
