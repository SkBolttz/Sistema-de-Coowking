package Sistema.Reserva.Reserva.Security;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Token JWT", description = "Controlador do Token JWT")
public record TokenJWT(String token) {
    
}
