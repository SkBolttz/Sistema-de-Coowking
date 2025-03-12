package Sistema.Reserva.Reserva.Exception;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Erro da Estação", description = "Exceções da Estação")
public class ErroEstacao extends Exception implements InterfaceErro{
    
    public ErroEstacao(String message) {
        super(message);
    }
    
    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
