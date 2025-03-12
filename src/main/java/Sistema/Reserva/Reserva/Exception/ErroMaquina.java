package Sistema.Reserva.Reserva.Exception;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Erro de Maquina", description = "Exceções de Maquinas")
public class ErroMaquina extends Exception implements InterfaceErro{
    
    public ErroMaquina(String message) { 
        super(message);
    }
    
    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
