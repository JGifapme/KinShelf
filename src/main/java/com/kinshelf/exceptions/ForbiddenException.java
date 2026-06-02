package com.kinshelf.exceptions;
/// Exception levée lorsqu'un utilisateur tente d'accéder à une ressource
/// ou d'exécuter une action pour laquelle il ne dispose pas des autorisations requises.
/// Erreur HTTP 403 (Forbidden).
public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) {
        super(message);
    }
}
