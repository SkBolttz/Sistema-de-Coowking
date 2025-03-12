package Sistema.Reserva.Reserva.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Sistema.Reserva.Reserva.Entity.Station;
import io.swagger.v3.oas.annotations.tags.Tag;

@Repository
@Tag(name = "Repositório de Estação", description = "Repositorio de Estação")
public interface StationRepository extends JpaRepository<Station,Long>{

    Station findByName(String name);
    
}
