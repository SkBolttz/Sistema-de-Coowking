package Sistema.Reserva.Reserva.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Sistema.Reserva.Reserva.DTO.Room.DeleteRoomDTO;
import Sistema.Reserva.Reserva.DTO.Room.ListRoom;
import Sistema.Reserva.Reserva.DTO.Room.RoomAgendamentoDTO;
import Sistema.Reserva.Reserva.DTO.Room.RoomCancelamentoDTO;
import Sistema.Reserva.Reserva.DTO.Room.UpdateRoomDTO;
import Sistema.Reserva.Reserva.Entity.AgendamentoRoomUser;
import Sistema.Reserva.Reserva.Entity.Room;
import Sistema.Reserva.Reserva.Entity.Users;
import Sistema.Reserva.Reserva.Exception.ErroAgendamento;
import Sistema.Reserva.Reserva.Exception.ErroEstacao;
import Sistema.Reserva.Reserva.Exception.ErroMaquina;
import Sistema.Reserva.Reserva.Exception.ErroSala;
import Sistema.Reserva.Reserva.Exception.ErroUsuario;
import Sistema.Reserva.Reserva.Repository.AgendamentoRoomUserRepository;
import Sistema.Reserva.Reserva.Repository.RoomRepository;
import Sistema.Reserva.Reserva.Repository.UsersRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Service
@Tag(name = "Service Salas", description = "Service Salas")
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private AgendamentoRoomUserRepository agendamentoRepository;

    public RoomService(RoomRepository roomRepository, UsersRepository usersRepository,
            AgendamentoRoomUserRepository agendamentoRepository) {
        this.roomRepository = roomRepository;
        this.userRepository = usersRepository;
        this.agendamentoRepository = agendamentoRepository;
    }

    @Operation(summary = "Realiza o cadastro da sala")
    public void createRoom(Room room) throws ErroSala {

        Room roomExists = roomRepository.findByName(room.getName());

        room.setStatus(true);

        if (roomExists != null) {
            throw new ErroSala("Sala já cadastrada");
        }

        roomRepository.save(room);
    }

    @Operation(summary = "Realiza a exclusão da sala")
    public void deleteRoom(DeleteRoomDTO room) throws ErroSala {

        Room roomExists = roomRepository.findByName(room.name());

        if (roomExists == null) {
            throw new ErroSala("Sala não cadastrada");
        }

        roomExists.setStatus(false);

        roomRepository.save(roomExists);
    }

    @Operation(summary = "Realiza a atualização da sala")
    public void updateRoom(UpdateRoomDTO room) throws ErroSala {

        Optional<Room> roomExists = roomRepository.findById(room.id());

        if (roomExists.isEmpty()) {
            throw new ErroSala("Sala não cadastrada");
        }

        if (room.name() != null) {
            roomExists.get().setName(room.name());
        }

        if (room.description() != null) {
            roomExists.get().setDescription(room.description());
        }

        if (room.capacity() != 0) {
            roomExists.get().setCapacity(room.capacity());
        }

        if (room.machines() != 0) {
            roomExists.get().setMachines(room.machines());
        }

        roomRepository.save(roomExists.get());
    }

    @Operation(summary = "Realiza a reativação da sala")
    public void reactiveRoom(DeleteRoomDTO room) throws ErroSala {

        Room roomExists = roomRepository.findByName(room.name());

        if (roomExists == null) {
            throw new ErroSala("Sala não cadastrada");
        }

        roomExists.setStatus(true);

        roomRepository.save(roomExists);
    }

    @Operation(summary = "Realiza a listagem das salas")
    public List<ListRoom> listRoom() throws ErroSala {

        if (roomRepository.findAll().isEmpty()) {
            throw new ErroSala("Nenhuma sala cadastrada");
        }

        return roomRepository.findAll()
                .stream()
                .filter(e -> e.getStatus() == true)
                .map(room -> new ListRoom(room.getName(), room.getDescription(), room.getCapacity(),
                        room.getMachines()))
                .toList();
    }

    @Operation(summary = "Verifica o horário de funcionamento da Sala")
    public void horarioFuncionamento(LocalDateTime dataHora) throws ErroEstacao {

        if (dataHora.getHour() < 7 || dataHora.getHour() > 22 && dataHora.getDayOfWeek() != DayOfWeek.SATURDAY
                && dataHora.getDayOfWeek() != DayOfWeek.SUNDAY) {
            throw new ErroEstacao("Sala fechada! Horário de funcionamento: 07:00 às 22:00");
        }

        if (dataHora.getHour() < 9 || dataHora.getHour() > 17 && dataHora.getDayOfWeek() == DayOfWeek.SATURDAY
                || dataHora.getDayOfWeek() == DayOfWeek.SUNDAY) {
            throw new ErroEstacao("Sala está fechada! Horário de funcionamento: 09:00 às 17:00");
        }
        horarioAberto();
    }

    @Operation(summary = "Retorna o horário de funcionamento da Sala")
    public String horarioAberto() {
        return "Horário de funcionamento de Segunda a Sexta: 07:00 às 22:00 e Sábado e Domingo: 09:00 às 17:00";
    }

    @Operation(summary = "Realiza o agendamento da sala")
    public String agendamento(RoomAgendamentoDTO room) throws ErroSala, ErroMaquina, ErroUsuario, ErroEstacao {

        Users UserExists = userRepository.findByName(room.name().username());

        if (UserExists == null) {
            throw new ErroUsuario("Usuário não cadastrado!");
        }

        Room roomExists = roomRepository.findByName(room.room().nameRoom());

        if (roomExists == null) {
            throw new ErroSala("Sala não cadastrada! Verifique o nome da sala: " + room.room().nameRoom());
        }

        if (roomExists.getStatus() == false) {
            throw new ErroSala("Sala fechada!");
        }

        if (roomExists.getCapacity() == 0) {
            throw new ErroSala("Sala lotada!");
        }

        if (roomExists.getMachines() == 0) {
            throw new ErroMaquina("Sala sem máquinas!");
        }

        horarioFuncionamento(room.start());

        roomExists.setCapacity(roomExists.getCapacity() - 1);
        roomExists.setMachines(roomExists.getMachines() - 1);

        LocalDateTime horaFim = room.start().plusHours(3);

        agendamentoRepository.save(new AgendamentoRoomUser(room.start(), horaFim, roomExists, UserExists, true));
        return "Estação reservada com sucesso!" +
                "\nQuarto: " + roomExists.getName() +
                "\nUsuário: " + UserExists.getName() +
                "\nData e hora: " + room.start() +
                "\nData e hora de término: " + horaFim;
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    @Operation(summary = "Realiza a finalização do agendamento da sala, looping de 1 minuto")
    protected void terminarAgendamento() {

        List<AgendamentoRoomUser> agendamentos = agendamentoRepository.findAll()
                .stream()
                .filter(e -> e.isStatus() == true)
                .toList();

        for (AgendamentoRoomUser agendamento : agendamentos) {

            if (agendamento.getDataHoraFim().isBefore(LocalDateTime.now())) {
                agendamento.setStatus(false);

                agendamento.getRoom().setCapacity(agendamento.getRoom().getCapacity() + 1);
                agendamento.getRoom().setMachines(agendamento.getRoom().getMachines() + 1);

                agendamentoRepository.save(agendamento);
                roomRepository.save(agendamento.getRoom());
            }
        }
    }

    @Scheduled(fixedRate = 60000)
    @Operation(summary = "Verifica a disponibilidade das salas, looping de 1 minuto")
    public String disponibilidade() {
        return "Estações disponíveis:\n" + roomRepository.findAll()
                .stream()
                .filter(e -> e.getStatus() == true && e.getCapacity() > 0)
                .map(e -> "\nEstação: " + e.getName() +
                        "\nDescrição: " + e.getDescription() +
                        "\nCapacidade: " + e.getCapacity() +
                        "\nMáquinas: " + e.getMachines())
                .collect(Collectors.joining("\n\n")); // Separa cada sala com uma linha em branco
    }

    @Operation(summary = "Realiza o cancelamento do agendamento da sala")
    public String cancelamento(RoomCancelamentoDTO room) throws ErroSala, ErroUsuario, ErroAgendamento {

        Users userExists = userRepository.findByName(room.username().username());

        if (userExists == null) {
            throw new ErroUsuario("Usuário não cadastrado!");
        }

        Room roomExists = roomRepository.findByName(room.roomName().nameRoom());

        if (roomExists == null) {
            throw new ErroSala("Sala não cadastrada! Verifique o nome da sala: " + room.roomName().nameRoom());
        }

        AgendamentoRoomUser agendamentoExists = agendamentoRepository.findByRoomAndUser(roomExists, userExists);

        if (agendamentoExists == null) {
            throw new ErroAgendamento("Agendamento não encontrado!");
        }

        if (agendamentoExists.isStatus() == false) {
            throw new ErroAgendamento("Agendamento já cancelado!");
        }

        agendamentoExists.setStatus(false);

        roomExists.setCapacity(roomExists.getCapacity() + 1);
        roomExists.setMachines(roomExists.getMachines() + 1);

        agendamentoRepository.save(agendamentoExists);
        roomRepository.save(roomExists);

        return "Agendamento cancelado com sucesso!";
    }
}
