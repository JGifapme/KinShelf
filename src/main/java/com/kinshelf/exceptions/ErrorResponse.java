package com.kinshelf.exceptions;

import java.time.LocalDateTime;
/// Objet de transfert utilisé pour retourner les détails d'une erreur.
public record ErrorResponse(
        int status,
        String message,
        LocalDateTime timestamp
) {}