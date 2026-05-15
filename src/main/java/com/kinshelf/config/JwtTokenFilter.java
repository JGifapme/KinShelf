package com.kinshelf.config;

import com.kinshelf.services.JwtService;
import com.kinshelf.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.apache.logging.log4j.util.Strings.isEmpty;

/// Ce filtre Spring Security vérifie la présence et
/// la validité d'un JWT dans chaque requête pour
/// authentifier l'utilisateur.
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserService userService;

    /// Filtre qui vérifie le jwt token et authentifie l'utilisateur
    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        //Flux de traitement
        // 1. Récupérer le header Authorization
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        // 2. Vérifier s'il commence par "Bearer "
        if (isEmpty(authHeader) || !authHeader.startsWith("Bearer ")) {
            //    Si le header est mal formaté,
            //    passe au filtre suivant sans authentification
            //“Pas de JWT → “Je donne la requête au prochain filtre.”
            filterChain.doFilter(request, response);
            return;
        }
        System.out.println(authHeader);
        // 3. Extraire le token (sans le "Bearer ")
        final String token = authHeader.split(" ")[1].trim();

        // 4. Extraire le nom d'utilisateur depuis le token
        String username = jwtService.extractUsername(token);

        // 6. Charger les détails de l'utilisateur depuis la base de données
        UserDetails userDetails = userService.loadUserByUsername(username);
        // 7. Vérifier si le token est bien valide pour cet utilisateur
        if (jwtService.isTokenValid(token, userDetails)) {
            // 8. Créer un objet d'authentification et le stocker dans le contexte de sécurité
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
            // Étape 9 : Stocker dans le contexte de sécurité
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        // Étape 10 : Continuer la chaîne de filtres
        filterChain.doFilter(request, response);
    }
}
