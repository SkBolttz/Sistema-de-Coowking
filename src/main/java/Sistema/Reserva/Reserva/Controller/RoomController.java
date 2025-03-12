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

import Sistema.Reserva.Reserva.DTO.Room.DeleteRoomDTO;
import Sistema.Reserva.Reserva.DTO.Room.ListRoom;
import Sistema.Reserva.Reserva.DTO.Room.RoomAgendamentoDTO;
import Sistema.Reserva.Reserva.DTO.Room.RoomCancelamentoDTO;
import Sistema.Reserva.Reserva.DTO.Room.UpdateRoomDTO;
import Sistema.Reserva.Reserva.Entity.Room;
import Sistema.Reserva.Reserva.Exception.ErroAgendamento;
import Sistema.Reserva.Reserva.Exception.ErroEstacao;
import Sistema.Reserva.Reserva.Exception.ErroMaquina;
import Sistema.Reserva.Reserva.Exception.ErroSala;
import Sistema.Reserva.Reserva.Exception.ErroUsuario;
import Sistema.Reserva.Reserva.Service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/room")
@Tag(name = "Quartos", description = "Controlador das Salas")
public class RoomController {
    
    @Autowired
    private RoomService roomService;

    @Operation(summary = "Realiza o cadastro da sala")
    @PostMapping("/create")
    public ResponseEntity<String> createRoom(@RequestBody @Valid Room room) throws ErroSala {

        roomService.createRoom(room);

        return ResponseEntity.ok("Sala criada com sucesso!");
    }

    @Operation(summary = "Realiza a exclusão da sala")
    @PutMapping("/delete")
    public ResponseEntity<String> deleteRoom(@RequestBody @Valid DeleteRoomDTO room) throws ErroSala{

        roomService.deleteRoom(room);

        return ResponseEntity.ok("Sala deletada com sucesso!");
    }

    @Operation(summary = "Realiza a atualização da sala")
    @PutMapping("/update")
    public ResponseEntity<String> updateRoom(@RequestBody @Valid UpdateRoomDTO room) throws ErroSala{

        roomService.updateRoom(room);

        return ResponseEntity.ok("Sala atualizada com sucesso!");
    }

    @Operation(summary = "Realiza a ativação da sala")
    @PutMapping("/enable")
    public ResponseEntity<String> enableRoom(@RequestBody @Valid DeleteRoomDTO room) throws ErroSala{

        roomService.reactiveRoom(room);

        return ResponseEntity.ok("Sala ativada com sucesso!");
    }  
    
    @Operation(summary = "Realiza a listagem das salas")
    @GetMapping("/list")
    public List<ListRoom> listRoom() throws ErroSala {
        
        return roomService.listRoom();
    }

    @Operation(summary = "Verifica o horário de funcionamento das salas")
    @GetMapping("/horarioFuncionamento")
    public ResponseEntity<String> horarioFuncionamento() {
        return ResponseEntity.ok(roomService.horarioAberto());
    }

    @Operation(summary = "Realiza o agendamento das salas")
    @PostMapping("/agendamento")
    public ResponseEntity<String> agendamento(@RequestBody @Valid RoomAgendamentoDTO room) throws ErroSala, ErroUsuario, ErroSala, ErroMaquina, ErroEstacao {
        
        return ResponseEntity.ok(roomService.agendamento(room));
    }

    @Operation(summary = "Verifica a disponibilidade das salas")
    @GetMapping("/disponibilidade")
    public ResponseEntity<String> disponibilidade(){

        return ResponseEntity.ok(roomService.disponibilidade());
    }

    @Operation(summary = "Realiza o cancelamento das salas")
    @PutMapping("/cancelamento")
    public ResponseEntity<String> cancelamento(@RequestBody @Valid RoomCancelamentoDTO room) throws ErroSala, ErroUsuario, ErroAgendamento{{

        return ResponseEntity.ok(roomService.cancelamento(room));
    }}
}
