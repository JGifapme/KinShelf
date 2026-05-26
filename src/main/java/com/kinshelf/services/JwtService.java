package com.kinshelf.services;

import com.kinshelf.entities.UserDetailsImplementation;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.List;

/// Génère le token JWT, défini son expiration
/// + méthodes pour vérifier sa validitée, son expiration
@Service
@Data
public class JwtService {

    /// Durée d'expiration pour le token JWT
    private final long jwtExpiration= 1*60*60*1000; // (1h) si modification, aussi modifier la durée d'expiration du cookie dans AuthController
    @Value("${jwt.secret-key}")
    private String secretKey;
    public String generateToken( String userId, List<String> roles){

        return Jwts.builder()
                .setSubject(userId)
                .claim("roles", roles)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ jwtExpiration))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }

    public String extractUsername(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    public Long extractUserId(String token) {
        return Long.parseLong(extractUsername(token));
    }
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final Long userId = this.extractUserId(token);
        final Long userEntityId = ((UserDetailsImplementation) userDetails).getUserEntity().getId();
        return userId != null && userId.equals(userEntityId) && !isTokenExpired(token);
    }
}
