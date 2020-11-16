package model.statement;

public class StatementException extends RuntimeException {
    private final String message;

    public StatementException(String message) {
        this.message = message;
    }

    public StatementException(String format, Object... arguments) {
        this.message = String.format(format, arguments);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
