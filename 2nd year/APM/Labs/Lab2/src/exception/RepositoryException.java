package exception;

public class RepositoryException extends Exception {

    private final String message;

    public RepositoryException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
