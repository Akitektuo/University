package model.statement;

public class StatementException extends RuntimeException {
    private final String message;

    public StatementException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
