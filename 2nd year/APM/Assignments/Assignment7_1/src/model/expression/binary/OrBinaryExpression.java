package model.expression.binary;

import model.expression.ExpressionException;
import model.expression.ExpressionInterface;
import model.type.Types;
import model.value.BooleanValue;
import model.value.ValueInterface;

public class OrBinaryExpression extends BinaryExpression {

    public OrBinaryExpression(ExpressionInterface leftExpression, ExpressionInterface rightExpression) {
        super(leftExpression, rightExpression);
    }

    @Override
    public ValueInterface evaluate() throws ExpressionException {
        var leftValue = getBooleanValue(leftExpression);
        var rightValue = getBooleanValue(rightExpression);

        return new BooleanValue(leftValue || rightValue);
    }

    @Override
    public Types getExpectedType() {
        return Types.BOOLEAN;
    }

    @Override
    public String toString() {
        return String.format("%s || %s", leftExpression, rightExpression);
    }
}
