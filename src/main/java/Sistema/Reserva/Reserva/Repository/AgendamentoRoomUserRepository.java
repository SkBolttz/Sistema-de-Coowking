package Sistema.Reserva.Reserva.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Sistema.Reserva.Reserva.Entity.AgendamentoRoomUser;
import Sistema.Reserva.Reserva.Entity.Room;
import Sistema.Reserva.Reserva.Entity.Users;
import io.swagger.v3.oas.annotations.tags.Tag;

@Repository
@Tag(name = "Repositório de Agendamento de Sala", description = "Repositório de Agendamento de Sala")
public interface AgendamentoRoomUserRepository extends JpaRepository<AgendamentoRoomUser, Long> {

    AgendamentoRoomUser findByRoomAndUser(Room roomExists, Users userExists);
    
}
