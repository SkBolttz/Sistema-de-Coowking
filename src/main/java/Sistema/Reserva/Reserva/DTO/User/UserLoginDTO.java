package Sistema.Reserva.Reserva.DTO.User;

import jakarta.validation.constraints.NotBlank;

public record UserLoginDTO(
        @NotBlank String email,
        @NotBlank String password) {

}
