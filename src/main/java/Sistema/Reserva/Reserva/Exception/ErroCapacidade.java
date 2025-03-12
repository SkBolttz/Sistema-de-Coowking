package Sistema.Reserva.Reserva.Exception;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Erro de Capacidade", description = "Exceções de Capacidade")
public class ErroCapacidade extends Exception implements InterfaceErro{
    
    public ErroCapacidade(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
