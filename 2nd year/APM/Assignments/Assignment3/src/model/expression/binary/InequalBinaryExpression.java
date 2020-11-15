package model.expression.binary;

import container.DictionaryInterface;
import model.expression.ExpressionException;
import model.expression.ExpressionInterface;
import model.value.BooleanValue;
import model.value.ValueInterface;

public class InequalBinaryExpression extends BinaryExpression {

    public InequalBinaryExpression(ExpressionInterface leftExpression, ExpressionInterface rightExpression) {
        super(leftExpression, rightExpression);
    }

    @Override
    public ValueInterface evaluate(DictionaryInterface<String, ValueInterface> systemTable) throws ExpressionException {
        var leftValue = leftExpression.evaluate(systemTable);
        var rightValue = rightExpression.evaluate(systemTable);

        return new BooleanValue(!leftValue.equals(rightValue));
    }
}
