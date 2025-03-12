package Sistema.Reserva.Reserva.Entity;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Roles dos Usuários", description = "Controlador dos roles dos Usuários")
public enum RoleUser {
    
    CLIENTE ("Cliente"),
    FUNCIONARIO ("Funcionario"),
    ADMINISTRADOR ("Administrador");

    @SuppressWarnings("unused")
    private String role;

    RoleUser(String role) {
        this.role = role;
    }
}
