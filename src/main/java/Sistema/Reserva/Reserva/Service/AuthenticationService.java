package Sistema.Reserva.Reserva.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import Sistema.Reserva.Reserva.Repository.UsersRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Service
@Tag(name = "Authentication Service", description = "Service de autenticação")
public class AuthenticationService implements UserDetailsService{

    @Autowired
    private UsersRepository repository;

    @Override
    @Operation(summary = "Realiza a autenticação do usúario")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = repository.findByEmail(username);

        if(user != null){
            return user;
        }

        throw new UsernameNotFoundException("User not found");
    }
    
}
