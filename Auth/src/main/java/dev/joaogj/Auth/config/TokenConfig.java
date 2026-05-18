package dev.joaogj.Auth.config;


import java.time.Instant;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import dev.joaogj.Auth.entity.User;

@Component
public class TokenConfig {

    private String secret = "secret";

    public String generatedToken(User user){
        Algorithm algoritmo = Algorithm.HMAC256(secret);
        return JWT.create()
            .withClaim("userId", user.getId())
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

            JWTUserData userData = JWTUserData.builder()
                    .userId(decodedJWT.getClaim("userId").asLong())
                    .email(decodedJWT.getSubject())
                    .build();

            return Optional.of(userData);

        } catch (JWTVerificationException e) {
            return Optional.empty();
        }
    }
    
}
