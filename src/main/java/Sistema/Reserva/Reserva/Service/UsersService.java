package Sistema.Reserva.Reserva.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import Sistema.Reserva.Reserva.Entity.Users;
import Sistema.Reserva.Reserva.Exception.ErroAutenticacao;
import Sistema.Reserva.Reserva.Repository.UsersRepository;
import Sistema.Reserva.Reserva.Security.SecurityConfiguration;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Service
@Tag(name = "Service Usúario", description = "Service do Usuário")
public class UsersService {

    @Autowired
    private UsersRepository repository;

    @Autowired
    private SecurityConfiguration securityConfiguration;

    @Operation(summary = "Realiza o cadastro do usúario")
    public void register(Users user) throws ErroAutenticacao {
        
        var dadosUsuario = repository.findByName(user.getName());

        user.setPassword(securityConfiguration.passwordEncoder().encode(user.getPassword()));

        if(dadosUsuario != null){
            throw new ErroAutenticacao("Usuário já cadastrado");
        }

        dadosUsuario = repository.save(user);
    }
    
}
