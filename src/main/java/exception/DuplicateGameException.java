package exception;

public class DuplicateGameException extends RuntimeException{

    public DuplicateGameException(String errorMessage) {
        super((errorMessage));
    }
}
