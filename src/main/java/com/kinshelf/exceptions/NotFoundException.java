package com.kinshelf.exceptions;
/// Exception levée lorsqu'une ressource demandée est introuvable.
/// Erreur HTTP 404 (Not Found).
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
