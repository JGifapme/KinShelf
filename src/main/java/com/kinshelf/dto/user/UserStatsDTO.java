package com.kinshelf.dto.user;

import java.util.Map;

public record UserStatsDTO(
        int totalOwned,
        int totalRead,
        int totalLent,
        Map<String, Integer> booksByCategory // ex: Roman: 12, BD: 5, Manga: 3
) {}
