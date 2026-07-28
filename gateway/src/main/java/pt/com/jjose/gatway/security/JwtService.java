package pt.com.jjose.gatway.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class JwtService {

    @Value("${api.security.token.secret}")
    private String secret;

    public boolean isTokenValid(String token) {
        try {
            DecodedJWT decodedJWT = verifyToken(token);

            Date expiresAt = decodedJWT.getExpiresAt();

            return expiresAt != null && expiresAt.after(new Date());

        } catch (Exception exception) {
            System.out.println("Erro ao validar token no Gateway: " + exception.getMessage());
            return false;
        }
    }

    public String extractEmail(String token) {
        DecodedJWT decodedJWT = verifyToken(token);
        return decodedJWT.getSubject();
    }

    public List<String> extractRoles(String token) {
        DecodedJWT decodedJWT = verifyToken(token);

        List<String> roles = decodedJWT.getClaim("roles").asList(String.class);

        if (roles == null) {
            return List.of();
        }

        return roles;
    }

    private DecodedJWT verifyToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.require(algorithm)
                .build()
                .verify(token);
    }
}