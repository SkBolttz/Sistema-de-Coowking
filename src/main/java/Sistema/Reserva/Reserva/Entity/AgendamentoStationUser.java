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
@Table(name = "tb_agendamento_station")
@Tag(name = "Agendamento das Estações", description = "Tabelas de agendamento das estações")
public class AgendamentoStationUser {
    
    public AgendamentoStationUser(LocalDateTime dataHoraInicio,LocalDateTime dataHoraFim, Station stationExists, Users userExists, boolean status) {
        this.dataHoraInicio = dataHoraInicio;
        this.dataHoraFim = dataHoraFim;
        this.station = stationExists;
        this.user = userExists;
        this.status = status;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "station_id")
    private Station station;
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
