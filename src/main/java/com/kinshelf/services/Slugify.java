package com.kinshelf.services;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

public class Slugify {
    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s+]");

    public static String toSlug(String input) {
        if (input == null || input.isEmpty()) return "";

        // remplace les espaces blancs par des tirets
        String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");

        // transforme les accents é -> e'
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        //supprime les accents tout seul e' -> e
        String slug = normalized.replaceAll("\\p{M}", "");


        return NONLATIN.matcher(slug).replaceAll("") // retire les caractères non latins et
                .replace("--", "-") // remplace les doubles tirets et
                .toLowerCase(Locale.ENGLISH) // mets tout en minuscule et
                .replaceAll("^-|-$", ""); // retire les tirets au début et à la fin
    }
}
