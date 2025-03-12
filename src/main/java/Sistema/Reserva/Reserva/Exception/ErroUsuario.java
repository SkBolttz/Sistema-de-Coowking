package Sistema.Reserva.Reserva.Exception;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Erro do Usuário", description = "Exceções dos Usuários")
public class ErroUsuario extends Exception implements InterfaceErro{

    public ErroUsuario(String message) {
        super(message);
    }
    
    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
