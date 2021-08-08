package Borman.cbbbluechips.models.exceptions;

public class UserNotLoggedInException extends RuntimeException {

    public UserNotLoggedInException(String message) {
        super(message);
    }

}