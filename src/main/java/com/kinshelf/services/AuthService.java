package com.kinshelf.services;

import com.kinshelf.dto.user.AuthResponse;
import com.kinshelf.dto.user.LoginRequest;
import com.kinshelf.dto.user.RegisterRequest;
import com.kinshelf.dto.user.UserCreateDTO;
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
public class AuthService {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public AuthResponse login(LoginRequest request) {
        try{
            Authentication authentication= authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.username(), request.password())
            );
            String username= authentication.getName();
            List<String> roles = authentication.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .filter(role -> role.startsWith("ROLE_"))
                    .toList();
            String jwt = jwtService.generateToken(username, roles);
            return  new AuthResponse(
                            "Utilisateur authentifié.",
                            roles,
                            jwt,
                            Instant.now().plusMillis(jwtService.getJwtExpiration())
                    );
        }
        catch (BadCredentialsException e){
            throw  new UnauthorizedException("Vous n'êtes pas autorisé à accéder a ce contenu.");
        }
        catch (DisabledException e){
            throw  new ForbiddenException("Compte désactivé.");
        }
    }

    public AuthResponse register(RegisterRequest request) {
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
                    List.of("ROLE_USER")
            );
            userService.create(user);

            // on crée le jwt token
            String jwtToken = jwtService.generateToken(request.username(), List.of("ROLE_USER"));
            return new AuthResponse(
                            "Compte créé avec succès.",
                            List.of("ROLE_USER"),
                            jwtToken,
                            Instant.now().plusMillis(jwtService.getJwtExpiration())
                    );
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de la création du compte.");
        }
    }
}
