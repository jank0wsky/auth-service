package authservice.utils.auths;

import authservice.utils.exceptions.AuthException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.JWTVerifier;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Instant;

@Slf4j
@Component
public class JwtUtil {
    @Value( "${jwt.secret:auth-service-default-secret-key-dont-share-this-it-is-top-secret}")
    private String SECRET_KEY;

    @Value( "${jwt.expiration:3600000}")
    private long EXPIRATION_TIME;

    @Value("${spring.application.name:auth-service}")
    private String issuer;

    private Algorithm algorithm;

    private JWTVerifier verifier;

    @PostConstruct
    private void init() {
        //If SECRET_KEY is short or accidentally left as a placeholder value, HMAC256 will sign with a weak key
        if (SECRET_KEY == null || SECRET_KEY.getBytes(StandardCharsets.UTF_8).length < 32) {
            throw new IllegalStateException("jwt.secret must be at least 256 bits (32 bytes)");
        }

        this.algorithm = Algorithm.HMAC256(SECRET_KEY);
        this.verifier = JWT
                .require(algorithm)
                .withIssuer(issuer)
                .build();
    }

    /**
     * Generate a JWT with email as subject
     */
    public String generateToken(String email) {
        Instant now = Instant.now();
        return JWT.create()
                .withSubject(email)
                .withIssuer(issuer)
                .withIssuedAt(now)
                .withExpiresAt(now.plusMillis(EXPIRATION_TIME))
                .sign(algorithm);
    }

    /**
     * Validates the token's signature and expiration
     */
    public boolean validateToken(String token) {
        try {
            verifier.verify(token);
            return true;
        } catch (TokenExpiredException e) {
            throw new AuthException("JWT expired");
        } catch (JWTVerificationException e) {
            throw new AuthException("JWT invalid");
        }
    }

    /**
     * Extracts an email from JWT, but also checks is it valid
     */
    public String extractEmail(String token) {
        return verifier.verify(token).getSubject();
    }
}
