package exception;

public class IncorrectGameValueException extends RuntimeException {

    public IncorrectGameValueException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }

    public IncorrectGameValueException(String errorMessage) {
        super((errorMessage));
    }
}
