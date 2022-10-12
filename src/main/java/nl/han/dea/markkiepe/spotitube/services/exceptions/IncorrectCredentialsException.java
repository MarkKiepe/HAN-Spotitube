package nl.han.dea.markkiepe.spotitube.services.exceptions;

public class IncorrectCredentialsException extends RuntimeException {
    public IncorrectCredentialsException(String message) {
        super(message);
    }
}
