package Sistema.Reserva.Reserva.Exception;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Erros de Status", description = "Exceções de Status")
public class ErroStatus extends Exception implements InterfaceErro{

    public ErroStatus(String message) {
        super(message);
    }
    
    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
