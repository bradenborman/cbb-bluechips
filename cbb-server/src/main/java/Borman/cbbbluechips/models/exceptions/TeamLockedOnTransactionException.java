package Borman.cbbbluechips.models.exceptions;

public class TeamLockedOnTransactionException extends RuntimeException {

    public TeamLockedOnTransactionException(String teamId) {
        super("Transaction attempted on team while locked: " + teamId);
    }

}