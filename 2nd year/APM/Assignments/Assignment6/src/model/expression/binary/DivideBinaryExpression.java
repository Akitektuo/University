package model.expression.binary;

import model.expression.ExpressionErrorType;
import model.expression.ExpressionException;
import model.expression.ExpressionInterface;
import model.type.Types;
import model.value.IntegerValue;
import model.value.ValueInterface;

public class DivideBinaryExpression extends BinaryExpression {

    public DivideBinaryExpression(ExpressionInterface leftExpression, ExpressionInterface rightExpression) {
        super(leftExpression, rightExpression);
    }

    @Override
    public ValueInterface evaluate() throws ExpressionException {
        var leftValue = getIntegerValue(leftExpression);
        var rightValue = getIntegerValue(rightExpression);

        if (rightValue == 0) {
            throw new ExpressionException(ExpressionErrorType.DIVISION_BY_ZERO);
        }
        return new IntegerValue(leftValue / rightValue);
    }

    @Override
    public Types getExpectedType() {
        return Types.NUMBER;
    }

    @Override
    public String toString() {
        return String.format("%s / %s", leftExpression, rightExpression);
    }
}
