package Sistema.Reserva.Reserva.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Sistema.Reserva.Reserva.Entity.AgendamentoStationUser;
import Sistema.Reserva.Reserva.Entity.Station;
import Sistema.Reserva.Reserva.Entity.Users;
import io.swagger.v3.oas.annotations.tags.Tag;

@Repository
@Tag(name = "Repositório de Agendamento de Estacão", description = "Repositorio de Agendamento de Estacão")
public interface AgendamentoStationUserRepository extends JpaRepository<AgendamentoStationUser, Long> {

    AgendamentoStationUser findByUserAndStation(Users userExists, Station stationExists);
    
}
