package Sistema.Reserva.Reserva.Security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import Sistema.Reserva.Reserva.Entity.Users;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Service
@Tag(name = "Token", description = "Controlador dos tokens")
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;

    @Operation(summary = "Gera o token")
    public String generateToken(Users user, String role) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withIssuer("Controle de Reservas")
                    .withSubject(user.getName())
                    .withClaim("role", role)
                    .sign(algorithm);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar token", e);
        }
    }

    @Operation(summary = "Valida o token")
    public String validarToken(String token) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm)
                    .withIssuer("Controle de Reservas")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (Exception e) {
            throw new RuntimeException("Token inválido");
        }
    }
}
