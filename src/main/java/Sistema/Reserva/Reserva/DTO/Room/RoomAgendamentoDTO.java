package Sistema.Reserva.Reserva.DTO.Room;

import java.time.LocalDateTime;

public record RoomAgendamentoDTO(
    UsernameDTO name,
    RoomNameDTO room,
    LocalDateTime start
) {
    
}
