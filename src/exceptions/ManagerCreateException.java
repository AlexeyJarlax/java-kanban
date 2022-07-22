package exceptions; //6/ отлов исключений

public class ManagerCreateException extends RuntimeException {
    public ManagerCreateException(final String message) {
        super(message);
    }
}