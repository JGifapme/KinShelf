package com.kinshelf.controllers;

import com.kinshelf.dto.user.*;
import com.kinshelf.services.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request,
                                              HttpServletResponse response) {
        AuthResult result = authService.login(request);

        // pose le cookie
        ResponseCookie cookie = ResponseCookie.from("jwt", result.jwtToken())
                .httpOnly(true)
                .secure(false) // mettre true en production, le site doit être en https pour que ça fonctionne
                .sameSite("Strict")
                .path("/")
                .maxAge(Duration.ofHours(1)) // même temps que l'expiration du JWT token dans jwtService
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        // renvoie le body SANS le jwtToken
        return ResponseEntity.ok(new AuthResponse(
                result.message(),
                result.roles()
        ));
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterRequest request,
                                      HttpServletResponse response) {
        AuthResult authResult = authService.register(request);

        // pose le cookie
        ResponseCookie cookie = ResponseCookie.from("jwt", authResult.jwtToken())
                .httpOnly(true)
                .secure(false) // mettre true en production, le site doit être en https pour que ça fonctionne
                .sameSite("Strict")
                .path("/")
                .maxAge(Duration.ofHours(1)) // même temps que l'expiration du JWT token dans jwtService
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        // renvoie le body SANS le jwtToken
        return ResponseEntity.ok(new AuthResponse(
                authResult.message(),
                authResult.roles()
        ));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        // supprime le contenu du cookie jwt
        ResponseCookie cookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .secure(false) // mettre true en production, le site doit être en https pour que ça fonctionne
                .sameSite("Strict")
                .path("/")
                .maxAge(0) // supprime le cookie immédiatement
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok().build();
    }
}