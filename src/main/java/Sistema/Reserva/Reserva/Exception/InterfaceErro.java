package Sistema.Reserva.Reserva.Exception;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Erros", description = "Controlador dos erros")
public interface InterfaceErro {
    String getMessage();
}
