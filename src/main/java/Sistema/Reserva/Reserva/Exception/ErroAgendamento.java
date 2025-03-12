package Sistema.Reserva.Reserva.Exception;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Erro Agendamento", description = "Exceções dos agendamentos")
public class ErroAgendamento extends Exception implements InterfaceErro{
    
    public ErroAgendamento(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
