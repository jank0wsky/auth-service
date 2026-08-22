package authservice.utils.exceptions;

public class EntityNotSavedException extends RuntimeException {

    public EntityNotSavedException(String message) {
        super(message);
    }
}
