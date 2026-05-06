package exception;

public class CorreoException extends RuntimeException {
    public CorreoException(String message) {
        super(message);
    }

    public CorreoException(String message, Throwable cause) {
        super(message, cause);
    }
}
