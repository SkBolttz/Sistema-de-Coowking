package Sistema.Reserva.Reserva.DTO.Room;

import jakarta.validation.constraints.NotNull;

public record UpdateRoomDTO(
    @NotNull long id,
    String name,
    String description,
    int capacity,
    int machines
    ) {
    
}
