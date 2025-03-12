package Sistema.Reserva.Reserva.Entity;

import java.util.List;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
@Entity
@Table(name = "tb_room")
@Tag(name = "Salas", description = "Tabelas de Salas")
public class 
Room {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true) @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    private int capacity;
    @NotNull
    private Boolean status;
    @NotNull
    private int machines;

    @OneToMany(mappedBy = "room")
    private List<AgendamentoRoomUser> agendamentos;
}
