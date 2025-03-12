package Sistema.Reserva.Reserva.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Sistema.Reserva.Reserva.DTO.Station.DeleteStationDTO;
import Sistema.Reserva.Reserva.DTO.Station.ListStation;
import Sistema.Reserva.Reserva.DTO.Station.StationAgendamentoDTO;
import Sistema.Reserva.Reserva.DTO.Station.UpdateStationDTO;
import Sistema.Reserva.Reserva.Entity.AgendamentoStationUser;
import Sistema.Reserva.Reserva.Entity.Station;
import Sistema.Reserva.Reserva.Entity.Users;
import Sistema.Reserva.Reserva.Exception.ErroAgendamento;
import Sistema.Reserva.Reserva.Exception.ErroCapacidade;
import Sistema.Reserva.Reserva.Exception.ErroEstacao;
import Sistema.Reserva.Reserva.Exception.ErroMaquina;
import Sistema.Reserva.Reserva.Exception.ErroStatus;
import Sistema.Reserva.Reserva.Exception.ErroUsuario;
import Sistema.Reserva.Reserva.Repository.AgendamentoStationUserRepository;
import Sistema.Reserva.Reserva.Repository.StationRepository;
import Sistema.Reserva.Reserva.Repository.UsersRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Service
@Tag(name = "Service Estação", description = "Service de Estação")
public class StationService {

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private AgendamentoStationUserRepository agendamentoRepository;

    @Operation(summary = "Realiza o cadastro da estação")
    public void create(Station station) throws ErroEstacao {

        Station stationExist = stationRepository.findByName(station.getName());

        if (stationExist != null) {
            throw new ErroEstacao("Estação já cadastrada");
        }

        stationRepository.save(station);
    }

    @Operation(summary = "Realiza a exclusão da estação")
    public void deleteStation(DeleteStationDTO station) throws ErroEstacao {

        Station stationExist = stationRepository.findByName(station.name());

        if (stationExist == null) {
            throw new ErroEstacao("Estação não cadastrada");
        }

        stationExist.setStatus(false);

        stationRepository.save(stationExist);
    }

    @Operation(summary = "Realiza a atualização da estação")
    public void update(UpdateStationDTO station) throws ErroEstacao {

        Optional<Station> stationExists = stationRepository.findById(station.id());

        if (stationExists.isEmpty()) {
            throw new ErroEstacao("Estação não cadastrada");
        }

        if (station.name() != null) {
            stationExists.get().setName(station.name());
        }

        if (station.description() != null) {
            stationExists.get().setDescription(station.description());
        }

        if (station.capacity() != 0) {
            stationExists.get().setCapacity(station.capacity());
        }
        
        if (station.machines() != 0) {
            stationExists.get().setMachines(station.machines());
        }

        stationRepository.save(stationExists.get());
    }

    @Operation(summary = "Realiza a ativação da estação")
    public void reactiveStation(DeleteStationDTO station) throws ErroEstacao {

        Station stationExits = stationRepository.findByName(station.name());

        if (stationExits == null) {
            throw new ErroEstacao("Estação não cadastrada");
        }

        stationExits.setStatus(true);
        stationRepository.save(stationExits);
    }

    @Operation(summary = "Realiza a listagem das estações")
    public List<ListStation> listStation() {

        return stationRepository.findAll()
                .stream()
                .filter(e -> e.isStatus() == true)
                .map(e -> new ListStation(e.getName(), e.getDescription(), e.getCapacity(), e.getMachines()))
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
        return "Sala aberta! Horário de funcionamento de Segunda a Sexta: 07:00 às 22:00 e Sábado e Domingo: 09:00 às 17:00";
    }

    @Operation(summary = "Realiza o agendamento da estação")
    public String agendamento(StationAgendamentoDTO station)
            throws ErroStatus, ErroEstacao, ErroUsuario, ErroMaquina, ErroCapacidade {

        Users userExists = usersRepository.findByName(station.username().username());

        if (userExists == null) {
            throw new ErroUsuario("Usuário não cadastrado!");
        }

        Station stationExists = stationRepository.findByName(station.nameStation().stationName());

        if (stationExists == null) {
            throw new ErroEstacao(
                    "Estação não cadastrada! Verifique o nome da estação: " + station.nameStation().stationName());
        }

        if (stationExists.isStatus() == false) {
            throw new ErroStatus("Estação fechada!");
        }

        if (stationExists.getCapacity() == 0) {
            throw new ErroCapacidade("Estação lotada!");
        }

        if (stationExists.getMachines() == 0) {
            throw new ErroMaquina("Estação sem máquinas!");
        }

        horarioFuncionamento(station.dataHora());

        stationExists.setCapacity(stationExists.getCapacity() - 1);
        stationExists.setMachines(stationExists.getMachines() - 1);

        LocalDateTime horaFim = station.dataHora().plusHours(3);

        agendamentoRepository
                .save(new AgendamentoStationUser(station.dataHora(), horaFim, stationExists, userExists, true));
        return "Estação reservada com sucesso!" +
                "\nEstação: " + stationExists.getName() +
                "\nUsuário: " + userExists.getName() +
                "\nData e hora: " + station.dataHora() +
                "\nData e hora de término: " + horaFim;

    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    @Operation(summary = "Realiza a finalização do agendamento da sala, looping de 1 minuto")
    protected void terminarAgendamento() {

        List<AgendamentoStationUser> agendamentos = agendamentoRepository.findAll()
                .stream()
                .filter(e -> e.isStatus() == true)
                .toList();

        for (AgendamentoStationUser agendamento : agendamentos) {

            if (agendamento.getDataHoraFim().isBefore(LocalDateTime.now())) {
                agendamento.setStatus(false);

                agendamento.getStation().setCapacity(agendamento.getStation().getCapacity() + 1);
                agendamento.getStation().setMachines(agendamento.getStation().getMachines() + 1);

                agendamentoRepository.save(agendamento);
                stationRepository.save(agendamento.getStation());
            }
        }
    }

    @Scheduled(fixedRate = 60000)
    @Operation(summary = "Verifica a disponibilidade das salas, looping de 1 minuto")
    public String disponibilidade() {

        return "Estações disponíveis: " + stationRepository.findAll()
                .stream()
                .filter(e -> e.getCapacity() > 0 && e.isStatus() == true)
                .map(e -> " Estação: " + e.getName() + "\n Descrição: " + e.getDescription() + "\n Capacidade: "
                        + e.getCapacity() + "\n Máquinas: " + e.getMachines())
                .toList();
    }

    @Operation(summary = "Realiza o cancelamento do agendamento da estação")    
    public String cancelamento(StationAgendamentoDTO station) throws ErroEstacao, ErroUsuario, ErroAgendamento {

        Users userExists = usersRepository.findByName(station.username().username());

        if (userExists == null) {
            throw new ErroUsuario("Usuário não cadastrado!");
        }

        Station stationExists = stationRepository.findByName(station.nameStation().stationName());

        if (stationExists == null) {
            throw new ErroEstacao(
                    "Estação não cadastrada! Verifique o nome da estação: " + station.nameStation().stationName());
        }

        AgendamentoStationUser agendamento = agendamentoRepository.findByUserAndStation(userExists, stationExists);

        if (agendamento == null) {
            throw new ErroAgendamento("Agendamento não encontrado!");
        }

        if (agendamento.isStatus() == false) {
            throw new ErroAgendamento("Agendamento já cancelado!");
        }

        agendamento.setStatus(false);

        stationExists.setCapacity(stationExists.getCapacity() + 1);
        stationExists.setMachines(stationExists.getMachines() + 1);

        agendamentoRepository.save(agendamento);
        stationRepository.save(stationExists);

        return "Agendamento cancelado com sucesso!";
    }

}
