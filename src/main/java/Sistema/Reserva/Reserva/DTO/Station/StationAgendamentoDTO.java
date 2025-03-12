package Sistema.Reserva.Reserva.DTO.Station;

import java.time.LocalDateTime;

import Sistema.Reserva.Reserva.DTO.Room.UsernameDTO;

public record StationAgendamentoDTO(
        UsernameDTO username,
        NameStationDTO nameStation,
        LocalDateTime dataHora) {

}
