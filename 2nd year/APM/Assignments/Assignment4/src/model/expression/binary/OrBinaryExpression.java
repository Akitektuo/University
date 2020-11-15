package model.expression.binary;

import model.expression.ExpressionErrorType;
import model.expression.ExpressionException;
import model.expression.ExpressionInterface;
import model.value.BooleanValue;
import model.value.ValueInterface;

public class OrBinaryExpression extends BinaryExpression {

    public OrBinaryExpression(ExpressionInterface leftExpression, ExpressionInterface rightExpression) {
        super(leftExpression, rightExpression);
    }

    @Override
    public ValueInterface evaluate() throws ExpressionException {
        var leftValue = getBooleanValue(leftExpression, ExpressionErrorType.LEFT_OPERAND_WRONG_TYPE);
        var rightValue = getBooleanValue(rightExpression, ExpressionErrorType.RIGHT_OPERAND_WRONG_TYPE);

        return new BooleanValue(leftValue || rightValue);
    }
}
