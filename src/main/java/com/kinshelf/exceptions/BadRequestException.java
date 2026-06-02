package com.kinshelf.exceptions;
/// Exception levée lorsqu'une requête est invalide ou contient des données incorrectes.
/// Erreur de type HTTP 400 (Bad Request).
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
