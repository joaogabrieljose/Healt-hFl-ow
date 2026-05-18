package dev.joaogj.Auth.config;

import java.time.Instant;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

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
    
}
