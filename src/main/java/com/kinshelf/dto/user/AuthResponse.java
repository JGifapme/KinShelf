package com.kinshelf.dto.user;

import java.time.Instant;
import java.util.List;

public record AuthResponse(
        String message,
        List<String> roles,
        String token,
        Instant expiredAt
) {
}
