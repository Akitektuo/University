package model.expression;

import container.DictionaryInterface;
import model.type.Types;
import model.value.BooleanValue;
import model.value.IntegerValue;
import model.value.ValueInterface;

public class ArithmeticExpression implements ExpressionInterface {
    private final ArithmeticOperator operator;
    private final ExpressionInterface leftExpression, rightExpression;

    public ArithmeticExpression(ExpressionInterface leftExpression, ArithmeticOperator operator, ExpressionInterface rightExpression) {
        this.leftExpression = leftExpression;
        this.operator = operator;
        this.rightExpression = rightExpression;
    }

    @Override
    public ValueInterface evaluate(DictionaryInterface<String, ValueInterface> systemTable) throws ExpressionException {
        var leftValue = getValue(leftExpression, systemTable, ExpressionErrorType.LEFT_OPERAND_WRONG_TYPE);
        var rightValue = getValue(rightExpression, systemTable, ExpressionErrorType.RIGHT_OPERAND_WRONG_TYPE);

        var leftNumber = leftValue.getValue();
        var rightNumber = rightValue.getValue();

        return switch (operator) {
            case ADDITION -> new IntegerValue(leftNumber + rightNumber);
            case SUBTRACTION -> new IntegerValue(leftNumber - rightNumber);
            case MULTIPLICATION -> new IntegerValue(leftNumber * rightNumber);
            case DIVISION -> {
                if (rightNumber == 0) {
                    throw new ExpressionException(ExpressionErrorType.DIVISION_BY_ZERO);
                }

                yield new IntegerValue(leftNumber / rightNumber);
            }
            case GREATER -> new BooleanValue(leftNumber > rightNumber);
            case SMALLER -> new BooleanValue(leftNumber < rightNumber);
            case GREATER_OR_EQUAL -> new BooleanValue(leftNumber >= rightNumber);
            case SMALLER_OR_EQUAL -> new BooleanValue(leftNumber <= rightNumber);
        };
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", leftExpression, switch (operator) {
            case ADDITION -> "+";
            case SUBTRACTION -> "-";
            case MULTIPLICATION -> "*";
            case DIVISION -> "/";
            case GREATER -> ">";
            case SMALLER -> "<";
            case GREATER_OR_EQUAL -> ">=";
            case SMALLER_OR_EQUAL -> "<=";
        }, rightExpression);
    }

    private IntegerValue getValue(
            ExpressionInterface expression,
            DictionaryInterface<String, ValueInterface> systemTable,
            ExpressionErrorType errorType) throws ExpressionException {
        var value = expression.evaluate(systemTable);

        if (value.getType().get() != Types.NUMBER) {
            throw new ExpressionException(errorType);
        }

        return (IntegerValue) value;
    }
}
