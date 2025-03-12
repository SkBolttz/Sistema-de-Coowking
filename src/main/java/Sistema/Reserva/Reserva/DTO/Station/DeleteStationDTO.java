package Sistema.Reserva.Reserva.DTO.Station;

import jakarta.validation.constraints.NotBlank;

public record DeleteStationDTO(
        @NotBlank String name) {

}
