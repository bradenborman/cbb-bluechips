package Borman.cbbbluechips.controllers.errorhandling;

import Borman.cbbbluechips.models.exceptions.TeamLockedOnTransactionException;
import Borman.cbbbluechips.models.exceptions.UserAlreadyExistsException;
import Borman.cbbbluechips.models.exceptions.UserNotLoggedInException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandlerController {

    private Logger logger = LoggerFactory.getLogger(ErrorHandlerController.class);

    @ExceptionHandler(UserNotLoggedInException.class)
    public ResponseEntity<String> catchError(Exception e) {
        logger.warn("UserNotLoggedInException: {}", e.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> userAlreadyExistsException(Exception e) {
        logger.warn("UserAlreadyExistsException: {}", e.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getLocalizedMessage());
    }

    @ExceptionHandler(TeamLockedOnTransactionException.class)
    public ResponseEntity<String> teamLockedOnTransactionException(Exception e) {
        logger.warn("TeamLockedOnTransactionException: {}", e.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getLocalizedMessage());
    }

}