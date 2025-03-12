package Sistema.Reserva.Reserva.DTO.Station;

public record ListStation(
        String name,
        String description,
        int capacity,
        boolean status,
        int machines
        ) {

    public ListStation(String name, String description, int capacity, int machines) {
        this(name, description, capacity, true, machines);
    }
}
