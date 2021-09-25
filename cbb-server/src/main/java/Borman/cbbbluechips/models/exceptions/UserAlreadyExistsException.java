package Borman.cbbbluechips.models.exceptions;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String emailEntered) {
        super("User trying to create an email that already exists: " + emailEntered);
    }

}