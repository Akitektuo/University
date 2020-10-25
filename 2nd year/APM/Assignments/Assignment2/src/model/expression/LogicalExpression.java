package model.expression;

import container.DictionaryInterface;
import model.type.Types;
import model.value.BooleanValue;
import model.value.ValueInterface;

public class LogicalExpression implements ExpressionInterface {
    private final LogicalOperator operator;
    private final ExpressionInterface leftExpression, rightExpression;

    public LogicalExpression(ExpressionInterface leftExpression, LogicalOperator operator, ExpressionInterface rightExpression) {
        this.leftExpression = leftExpression;
        this.operator = operator;
        this.rightExpression = rightExpression;
    }

    @Override
    public ValueInterface evaluate(DictionaryInterface<String, ValueInterface> systemTable) throws ExpressionException {
        var leftValue = getValue(leftExpression, systemTable, ExpressionErrorType.LEFT_OPERAND_WRONG_TYPE);
        var rightValue = getValue(rightExpression, systemTable, ExpressionErrorType.RIGHT_OPERAND_WRONG_TYPE);

        var leftBoolean = leftValue.getValue();
        var rightBoolean = rightValue.getValue();

        return switch (operator) {
            case EQUALITY -> new BooleanValue(leftBoolean == rightBoolean);
            case INEQUALITY -> new BooleanValue(leftBoolean != rightBoolean);
            case AND -> new BooleanValue(leftBoolean && rightBoolean);
            case OR -> new BooleanValue(leftBoolean || rightBoolean);
        };
    }

    private BooleanValue getValue(
            ExpressionInterface expression,
            DictionaryInterface<String, ValueInterface> systemTable,
            ExpressionErrorType errorType) throws ExpressionException {
        var value = expression.evaluate(systemTable);

        if (value.getType().get() != Types.BOOLEAN) {
            throw new ExpressionException(errorType);
        }

        return (BooleanValue) value;
    }
}
