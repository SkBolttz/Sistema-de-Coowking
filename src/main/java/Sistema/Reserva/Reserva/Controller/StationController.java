package Sistema.Reserva.Reserva.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Sistema.Reserva.Reserva.DTO.Station.DeleteStationDTO;
import Sistema.Reserva.Reserva.DTO.Station.ListStation;
import Sistema.Reserva.Reserva.DTO.Station.StationAgendamentoDTO;
import Sistema.Reserva.Reserva.DTO.Station.UpdateStationDTO;
import Sistema.Reserva.Reserva.Entity.Station;
import Sistema.Reserva.Reserva.Exception.ErroAgendamento;
import Sistema.Reserva.Reserva.Exception.ErroCapacidade;
import Sistema.Reserva.Reserva.Exception.ErroEstacao;
import Sistema.Reserva.Reserva.Exception.ErroMaquina;
import Sistema.Reserva.Reserva.Exception.ErroStatus;
import Sistema.Reserva.Reserva.Exception.ErroUsuario;
import Sistema.Reserva.Reserva.Service.StationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/station")
@Tag(name = "Estação", description = "Contolador das Estações")
public class StationController {
    
    @Autowired
    private StationService stationService;

    @Operation(summary = "Realiza o cadastro da estação")
    @PostMapping("/create")
    public ResponseEntity<String> createStation(@RequestBody @Valid Station station) throws ErroEstacao {
        
        stationService.create(station);

        return ResponseEntity.ok("Estação criada com sucesso!");
    }

    @Operation(summary = "Realiza a exclusão da estação")
    @PutMapping("/delete")
    public ResponseEntity<String> deleteStation(@RequestBody @Valid DeleteStationDTO station) throws ErroEstacao {
        
        stationService.deleteStation(station);

        return ResponseEntity.ok("Estação deletada com sucesso!");
    }

    @Operation(summary = "Realiza a atualização da estação")
    @PutMapping("/update")
    public ResponseEntity<String> updateStation(@RequestBody @Valid UpdateStationDTO station) throws ErroEstacao {
        
        stationService.update(station);

        return ResponseEntity.ok("Estação atualizada com sucesso!");
    }

    @Operation(summary = "Realiza a ativação da estação")
    @PutMapping("/enable")
    public ResponseEntity<String> enableStation(@RequestBody @Valid DeleteStationDTO station) throws ErroEstacao {
        
        stationService.reactiveStation(station);

        return ResponseEntity.ok("Estação ativada com sucesso!");
    }


    @Operation(summary = "Realiza a listagem das estações")
    @GetMapping("/list")
    public List<ListStation> listStation() {
        
        return stationService.listStation();
    }

    @Operation(summary = "Verifica o horário de funcionamento das estações")
    @GetMapping("/horarioFuncionamento")
    public ResponseEntity<String> horarioFuncionamento() {
        return ResponseEntity.ok(stationService.horarioAberto());
    }

    @Operation(summary = "Realiza o agendamento das estações")
    @PostMapping("/agendamento")
    public ResponseEntity<String> agendamento(@RequestBody @Valid StationAgendamentoDTO station) throws ErroEstacao, ErroUsuario, ErroStatus, ErroMaquina, ErroCapacidade {
        
        return ResponseEntity.ok(stationService.agendamento(station));
    }

    @Operation(summary = "Verifica a disponibilidade das estações")
    @GetMapping("/disponibilidade")
    public ResponseEntity<String> disponibilidade(){

        return ResponseEntity.ok(stationService.disponibilidade());
    }

    @Operation(summary = "Realiza o cancelamento das estações")
    @PutMapping("/cancelamento")
    public ResponseEntity<String> cancelamento(@RequestBody @Valid StationAgendamentoDTO station) throws ErroEstacao, ErroUsuario, ErroAgendamento{
        
        return ResponseEntity.ok(stationService.cancelamento(station));
    }
}
