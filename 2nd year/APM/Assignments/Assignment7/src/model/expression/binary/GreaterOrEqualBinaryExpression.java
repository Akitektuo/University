package model.expression.binary;

import model.expression.ExpressionException;
import model.expression.ExpressionInterface;
import model.type.Types;
import model.value.BooleanValue;
import model.value.ValueInterface;

public class GreaterOrEqualBinaryExpression extends BinaryExpression {

    public GreaterOrEqualBinaryExpression(ExpressionInterface leftExpression, ExpressionInterface rightExpression) {
        super(leftExpression, rightExpression);
    }

    @Override
    public ValueInterface evaluate() throws ExpressionException {
        var leftValue = getIntegerValue(leftExpression);
        var rightValue = getIntegerValue(rightExpression);

        return new BooleanValue(leftValue >= rightValue);
    }

    @Override
    public Types getExpectedType() {
        return Types.NUMBER_TO_BOOLEAN;
    }

    @Override
    public String toString() {
        return String.format("%s >= %s", leftExpression, rightExpression);
    }
}
