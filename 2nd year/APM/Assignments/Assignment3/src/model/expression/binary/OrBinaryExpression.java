package model.expression.binary;

import container.DictionaryInterface;
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
    public ValueInterface evaluate(DictionaryInterface<String, ValueInterface> systemTable) throws ExpressionException {
        super.evaluate(systemTable);
        var leftValue = getBooleanValue(leftExpression, ExpressionErrorType.LEFT_OPERAND_WRONG_TYPE);
        var rightValue = getBooleanValue(rightExpression, ExpressionErrorType.RIGHT_OPERAND_WRONG_TYPE);

        return new BooleanValue(leftValue || rightValue);
    }
}
