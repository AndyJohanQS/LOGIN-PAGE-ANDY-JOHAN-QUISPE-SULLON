package andyquispesullon.com.example.demo.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import andyquispesullon.com.example.demo.domain.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service // Marca la clase como un servicio de Spring.
public class TokenService {
    @Value("${api.security.token.secret}") // Inyecta el valor del secreto del token desde la configuración.
    private String secret;

    // Genera un token JWT para un usuario
    public String generateToken(User user){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            String token = JWT.create()
                    .withIssuer("login-auth-api") // Establece el emisor del token.
                    .withSubject(user.getEmail()) // Establece el sujeto del token.
                    .withExpiresAt(this.generateExpirationDate()) // Establece la fecha de expiración.
                    .sign(algorithm); // Firma el token con el algoritmo HMAC256.
            return token;
        } catch (JWTCreationException exception){
            throw new RuntimeException("Error while authenticating");
        }
    }

    // Valida un token JWT y devuelve el sujeto (email) si es válido
    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("login-auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return null;
        }
    }

    // Genera la fecha de expiración del token
    private Instant generateExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
