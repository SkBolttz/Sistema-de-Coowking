package Sistema.Reserva.Reserva.DTO.Room;

public record ListRoom(
        String name,
        String description,
        int capacity,
        boolean status,
        int machines
        ) {

    public ListRoom(String name, String description, int capacity, int machines) {
        this(name, description, capacity, true, machines);
    }
}
