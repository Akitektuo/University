package model.expression;

import static model.expression.ErrorType.Unknown;

public class ExpressionException extends Exception {
    private final String message;
    private final ErrorType errorType;

    public ExpressionException() {
        errorType = Unknown;
        message = setMessageForErrorType(this.errorType);
    }

    public ExpressionException(ErrorType errorType) {
        this.errorType = errorType;
        message = setMessageForErrorType(errorType);
    }

    public ExpressionException(String message) {
        errorType = Unknown;
        this.message = message;
    }

    public ExpressionException(ErrorType errorType, String message) {
        this.errorType = errorType;
        this.message = message;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public String getMessage() {
        return message;
    }

    private String setMessageForErrorType(ErrorType errorType) {
        return switch (errorType) {
            case LeftOperandWrongType -> "The left operand is not the expected value!";
            case RightOperandWrongType -> "The right operand is not the expected value!";
            case DivisionByZero -> "Division by zero not allowed!";
            case Unknown -> "Unknown error has occurred in expression!";
        };
    }
}
