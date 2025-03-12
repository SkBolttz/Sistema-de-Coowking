package Sistema.Reserva.Reserva.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Sistema.Reserva.Reserva.Entity.Room;
import io.swagger.v3.oas.annotations.tags.Tag;

@Repository
@Tag(name = "Repositório de Sala", description = "Repositório de Sala")
public interface RoomRepository extends JpaRepository<Room,Long>{

    Room findByName(String name);
    
}
