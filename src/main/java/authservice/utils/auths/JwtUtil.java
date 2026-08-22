package authservice.utils.auths;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    @Value( "${jwt.secret}")
    private String SECRET_KEY;
    @Value( "${jwt.expiration}")
    private final long EXPIRATION_TIME = 1000 * 60 * 1000;

    public String generateToken(String email) {
        return JWT.create()
                .withSubject(email)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    public String extractEmail(String token) {
        return decodedToken(token).getSubject();
    }

    public boolean isTokenExpired(String token) {
        return decodedToken(token).getExpiresAt().before(new Date());
    }

    public boolean validateToken(String token, String email) {
        return (email.equals(extractEmail(token)) && !isTokenExpired(token));
    }

    private DecodedJWT decodedToken(String token) {
        return JWT.require(Algorithm.HMAC256(SECRET_KEY)).build().verify(token);
    }
}

