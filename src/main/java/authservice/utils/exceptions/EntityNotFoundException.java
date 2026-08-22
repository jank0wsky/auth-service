package authservice.utils.exceptions;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(Long id) {
        super("Entity with id: " + id + " not found");
    }

    public EntityNotFoundException(String param, String value) {
        super("Entity for param: " + param + "and value: " + value + " not found");
    }
}
