package teamx.app.backend.models;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.security.Key;
import java.util.Date;

/**
 * JWToken class represents a utility for generating JSON Web Tokens (JWT).
 *
 * This class provides methods to generate JWTs with user-related claims such as
 * user ID, email, and role. It uses the HS512 signature algorithm for token
 * signing.
 *
 * The defaulttoken expiration is set to 1 hour.
 * These values can be customized using configuration properties.
 *
 * @author Nizar Amine
 * @see io.jsonwebtoken.Jwts
 * @see io.jsonwebtoken.SignatureAlgorithm
 */

@Data
@Entity(name = "JWToken")
@NoArgsConstructor
@AllArgsConstructor
@Configuration
public class JWToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String email;
    private String role;

    @Value("${jwt.issuer:Solar Sedum}")
    private String issuer;

    @Value("${jwt.expiration:3600000}") // Default expiration set to 1 hour (in milliseconds)
    private long tokenExpiration;

    private static final String JWT_USERID_CLAIM = "id";
    private static final String JWT_EMAIL_CLAIM = "email";
    private static final String JWT_ROLE_CLAIM = "role";
    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

    /**
     * Provides the primary bean instance of JWToken for dependency injection.
     * @return The primary JWToken bean instance.
     */
    @Primary
    @Bean
    public JWToken jwtToken() {
        return new JWToken();
    }

    /**
     * Generates a JWT token with user-related claims.
     * Claims can be changed to desired values as the JWT is generated it encodes
     * the claims in the token for further decoding as desired.
     * @param userId The user ID to include in the JWT.
     * @param email  The user email to include in the JWT.
     * @param role   The user role to include in the JWT.
     * @return The generated JWT token as a string.
     */

    public String generateToken(Long userId, String email, String role) {
        Key key = Keys.secretKeyFor(SIGNATURE_ALGORITHM);
        String jwtToken = Jwts.builder()
                .claim(JWT_USERID_CLAIM, userId)
                .claim(JWT_EMAIL_CLAIM, email)
                .claim(JWT_ROLE_CLAIM, role)
                .setIssuer(issuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .signWith(key, SIGNATURE_ALGORITHM)
                .compact();
        return jwtToken;
    }
}
