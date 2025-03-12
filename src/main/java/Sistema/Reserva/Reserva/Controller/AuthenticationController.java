package Sistema.Reserva.Reserva.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Sistema.Reserva.Reserva.DTO.User.UserLoginDTO;
import Sistema.Reserva.Reserva.Entity.Users;
import Sistema.Reserva.Reserva.Exception.ErroAutenticacao;
import Sistema.Reserva.Reserva.Security.TokenService;
import Sistema.Reserva.Reserva.Service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Controlador das Autenticações")
public class AuthenticationController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager manager;

    @Operation(summary = "Realiza a autenticação do usúario") 
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid UserLoginDTO userLoginDTO) {

        var tokenAuthenticaion = new UsernamePasswordAuthenticationToken(userLoginDTO.email(), userLoginDTO.password());
        var authentication = manager.authenticate(tokenAuthenticaion);

        var user = (Users) authentication.getPrincipal();
        var token = tokenService.generateToken(user, user.getRole().toString());

        return ResponseEntity.ok(token);
    }

    @Operation(summary = "Realiza o cadastro do usúario")
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid Users user) throws ErroAutenticacao {

         usersService.register(user);

         return ResponseEntity.ok("Cadastro realizado com sucesso!");
    }
}
