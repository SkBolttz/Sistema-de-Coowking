package Sistema.Reserva.Reserva.DTO.Station;

import jakarta.validation.constraints.NotNull;

public record UpdateStationDTO(
        @NotNull long id,
        String name,
        String description,
        int capacity,
        int machines) {

}
