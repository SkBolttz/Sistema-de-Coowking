package Sistema.Reserva.Reserva.Exception;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Erro de Autenticação", description = "Exceções de Autenticação")
public class ErroAutenticacao extends Exception implements InterfaceErro{
    
    public ErroAutenticacao(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
