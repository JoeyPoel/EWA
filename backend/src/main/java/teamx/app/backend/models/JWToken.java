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

    @Primary
    @Bean
    public JWToken jwtToken() {
        return new JWToken();
    }

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
