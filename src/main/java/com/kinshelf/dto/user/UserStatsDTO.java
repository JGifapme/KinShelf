package com.kinshelf.dto.user;

import java.util.Map;
/// DTO qui retourne les statistiques de lecture de l'utilisateur connecté pour sa page profil/stat
public record UserStatsDTO(
        int totalOwned,
        int totalRead,
        int totalLent,
        Map<String, Integer> booksByCategory // ex: Roman: 12, BD: 5, Manga: 3
) {}
