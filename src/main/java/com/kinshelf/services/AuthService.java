package com.kinshelf.services;

import com.kinshelf.dto.user.*;
import com.kinshelf.entities.UserDetailsImplementation;
import com.kinshelf.exceptions.BadRequestException;
import com.kinshelf.exceptions.ForbiddenException;
import com.kinshelf.exceptions.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
///  Classe qui possède la logique derrière l'authentification : login et register
public class AuthService {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    /**
     * Authentifie un utilisateur à partir de son identifiant et de son mot de passe.
     * @param request contient le nom d'utilisateur et le mot de passe.
     * @return un objet {@link AuthResult} contenant le message de succès,
     *         les rôles de l'utilisateur et le token JWT généré.
     * @throws UnauthorizedException si les identifiants sont incorrects.
     * @throws ForbiddenException si le compte utilisateur est désactivé.
     */
    public AuthResult login(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.username(), request.password())
            );
            UserDetailsImplementation userDetails = (UserDetailsImplementation) authentication.getPrincipal();
            String userId = String.valueOf(userDetails.getUserEntity().getId());
            List<String> roles = authentication.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .filter(role -> role.startsWith("ROLE_"))
                    .toList();
            String jwtToken = jwtService.generateToken(userId, roles);

            return new AuthResult(
                    "Utilisateur authentifié.",
                    roles,
                    jwtToken
            );
        } catch (BadCredentialsException e) {
            throw new UnauthorizedException("Identifiants invalides.");
        } catch (DisabledException e) {
            throw new ForbiddenException("Compte désactivé.");
        }
    }
    /**
     * Crée un nouveau compte utilisateur puis génère un token JWT
     * permettant son authentification immédiate.
     * @param request contient les informations nécessaires à l'inscription
     *                (nom d'utilisateur, email, mot de passe, date de naissance).
     * @return un objet {@link AuthResult} contenant le message de succès,
     *         les rôles de l'utilisateur et le token JWT généré.
     * @throws BadRequestException si le nom d'utilisateur, l'email ou
     *                             une autre donnée fournie est invalide.
     * @throws ResponseStatusException si une erreur technique survient
     *                                 lors de la création du compte.
     */
    public AuthResult register(RegisterRequest request) {
        try {
            // on vérifie si le nom et l'email ne sont pas déjà pris
            if (userService.existsByUsername(request.username())) {
                throw new BadRequestException("Nom d'utilisateur déjà pris.");
            }
            if (userService.existsByEmail(request.email())) {
                throw new BadRequestException("Email déjà enregistré.");
            }
            // on crée l'utilisateur
            UserCreateDTO user = new UserCreateDTO(
                    request.username(),
                    request.dateOfBirth(),
                    request.email(),
                    passwordEncoder.encode(request.password()),
                    List.of("USER")
            );
            UserResponseDTO urd = userService.create(user);
            String userId = String.valueOf(urd.id());
            // on crée le jwt token
            String jwtToken = jwtService.generateToken(userId, List.of("ROLE_USER"));

            // on retourne juste le token pour que le controller pose le cookie
            return new AuthResult(
                    "Compte créé avec succès.",
                    List.of("ROLE_USER"),
                    jwtToken
            );

        } catch (BadRequestException e) {
            throw e; // on récupère les bad request si il y en a (email invalide, ...)
        } catch (Exception e) { // et si on a une autre erreur :
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de la création du compte.");
        }
    }
}
