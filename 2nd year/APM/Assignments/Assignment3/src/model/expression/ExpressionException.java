package model.expression;

import static model.expression.ExpressionErrorType.UNKNOWN;

public class ExpressionException extends RuntimeException {
    private final String message;
    private final ExpressionErrorType errorType;

    public ExpressionException() {
        errorType = UNKNOWN;
        message = setMessageForErrorType(this.errorType);
    }

    public ExpressionException(ExpressionErrorType errorType) {
        this.errorType = errorType;
        message = setMessageForErrorType(errorType);
    }

    public ExpressionException(String message) {
        errorType = UNKNOWN;
        this.message = message;
    }

    public ExpressionException(ExpressionErrorType errorType, String message) {
        this.errorType = errorType;
        this.message = message;
    }

    public ExpressionErrorType getErrorType() {
        return errorType;
    }

    @Override
    public String getMessage() {
        return message;
    }

    private String setMessageForErrorType(ExpressionErrorType errorType) {
        return switch (errorType) {
            case LEFT_OPERAND_WRONG_TYPE -> "The left operand is not the expected value!";
            case RIGHT_OPERAND_WRONG_TYPE -> "The right operand is not the expected value!";
            case DIVISION_BY_ZERO -> "Division by zero not allowed!";
            case UNKNOWN -> "Unknown error has occurred in expression!";
        };
    }
}
