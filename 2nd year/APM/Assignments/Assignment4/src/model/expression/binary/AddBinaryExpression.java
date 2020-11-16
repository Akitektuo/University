package model.expression.binary;

import container.DictionaryInterface;
import model.expression.ExpressionErrorType;
import model.expression.ExpressionException;
import model.expression.ExpressionInterface;
import model.value.IntegerValue;
import model.value.ValueInterface;

public class AddBinaryExpression extends BinaryExpression {

    public AddBinaryExpression(ExpressionInterface leftExpression, ExpressionInterface rightExpression) {
        super(leftExpression, rightExpression);
    }

    @Override
    public ValueInterface evaluate() throws ExpressionException {
        var leftValue = getIntegerValue(leftExpression, ExpressionErrorType.LEFT_OPERAND_WRONG_TYPE);
        var rightValue = getIntegerValue(rightExpression, ExpressionErrorType.RIGHT_OPERAND_WRONG_TYPE);

        return new IntegerValue(leftValue + rightValue);
    }

    @Override
    public String toString() {
        return String.format("%s + %s", leftExpression, rightExpression);
    }
}
