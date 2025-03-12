package Sistema.Reserva.Reserva.Entity;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_agendamento_room")
@Tag(name = "Agendamento das Salas", description = "Tabelas de agendamento das salas")
public class AgendamentoRoomUser {

    public AgendamentoRoomUser(LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim, Room roomExists,
            Users userExists, boolean status) {
        this.dataHoraInicio = dataHoraInicio;
        this.dataHoraFim = dataHoraFim;
        this.room = roomExists;
        this.user = userExists;
        this.status = status;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;
    @NotNull
    private LocalDateTime dataHoraInicio;
    @NotNull
    private LocalDateTime dataHoraFim;
    @NotNull
    private boolean status;
}
