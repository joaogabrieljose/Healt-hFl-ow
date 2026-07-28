package dev.joaogj.Auth.config;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import dev.joaogj.Auth.entity.User;

@Component
public class TokenConfig {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generatedToken(User user) {

        Algorithm algoritmo = Algorithm.HMAC256(secret);

        List<String> roles = user.getRoles()
                .stream()
                .map(role -> role.name())
                .toList();

        if (roles.isEmpty()) {
            throw new RuntimeException("Utilizador sem role definida");
        }

        return JWT.create()
                .withClaim("userId", user.getId())
                .withClaim("roles", roles)
                .withSubject(user.getEmail())
                .withExpiresAt(Instant.now().plusSeconds(86400))
                .withIssuedAt(Instant.now())
                .sign(algoritmo);
    }

    public Optional<JWTUserData> validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            DecodedJWT decodedJWT = JWT.require(algorithm)
                    .build()
                    .verify(token);

            Long userId = decodedJWT.getClaim("userId").asLong();
            String email = decodedJWT.getSubject();

            List<String> roles = decodedJWT.getClaim("roles").asList(String.class);

            if (userId == null || email == null || email.isBlank()) {
                return Optional.empty();
            }

            if (roles == null) {
                roles = List.of();
            }

            return Optional.of(new JWTUserData(userId, email, roles));

        } catch (JWTVerificationException e) {
            return Optional.empty();
        }
    }
}