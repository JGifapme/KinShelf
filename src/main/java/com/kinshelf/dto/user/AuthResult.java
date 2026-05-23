package com.kinshelf.dto.user;

import java.util.List;

public record AuthResult(
        String message,
        List<String> roles,
        String jwtToken
) {
}
