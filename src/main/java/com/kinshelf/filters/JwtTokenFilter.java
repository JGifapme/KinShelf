package com.kinshelf.filters;

import com.kinshelf.services.JwtService;
import com.kinshelf.services.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

import static org.apache.logging.log4j.util.Strings.isEmpty;

/// Ce filtre Spring Security vérifie la présence et
/// la validité d'un JWT dans le cookie pour
/// authentifier l'utilisateur.
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Test pour docker  : laisse passer les requêtes OPTIONS sans vérifier le JWT
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }
        // 1. Récupérer le cookie "jwt"
        String token = null;
        if (request.getCookies() != null) {
            token = Arrays.stream(request.getCookies())
                    .filter(c -> "jwt".equals(c.getName()))
                    .map(Cookie::getValue)
                    .findFirst()
                    .orElse(null);
        }

        // 2. Pas de cookie → passe au filtre suivant sans authentification
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }
        // Note : quand on passe par les headers le jwt commence par Bearer
        // mais pas dans les cookies

        // 3. Valider le token et authentifier l'utilisateur
        try {
            Long userId = jwtService.extractUserId(token);
            // 4. Charger les détails de l'utilisateur depuis la base de données
            UserDetails userDetails = userService.loadUserById(userId);
            // 5. Vérifier si le token est bien valide pour cet utilisateur
            if (jwtService.isTokenValid(token, userDetails)) {
                // 6. Créer un objet d'authentification et le stocker dans le contexte de sécurité
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                // Étape 7 : Stocker dans le contexte de sécurité
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
            // Étape 8 : Continuer la chaîne de filtres
            filterChain.doFilter(request, response);

        // Étape 9 : retourner des erreurs si nécessaire
        // récupération des exceptions : retour 401 : Unauthorized
        // comme on est dans les filtres, c'est trop tôt, on ne peut pas utiliser le GlobalExceptionHandler
        // donc on les envoie d'ici
        } catch (ExpiredJwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"message\":\"Authentification expirée, veuillez vous reconnecter.\"}");
        } catch (JwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"message\":\"Token invalide.\"}");
        }
    }

}
