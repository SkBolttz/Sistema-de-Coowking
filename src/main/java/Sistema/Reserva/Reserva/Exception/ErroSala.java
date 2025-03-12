package Sistema.Reserva.Reserva.Exception;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Erro da Sala", description = "Exceções das Salas")
public class ErroSala extends Exception implements InterfaceErro{
    
    public ErroSala(String message) {
        super(message);
    }
    
    @Override
    public String getMessage() {
        return super.getMessage();
    }
    
}
