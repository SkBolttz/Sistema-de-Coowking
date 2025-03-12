package Sistema.Reserva.Reserva.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Sistema.Reserva.Reserva.Entity.Users;
import io.swagger.v3.oas.annotations.tags.Tag;

@Repository
@Tag(name = "Repositório de Usuário", description = "Repositório de Usuários")
public interface UsersRepository extends JpaRepository<Users, Long>{

    Users findByName(String username);

    Users findByEmail(String email);
}
