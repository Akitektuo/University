package model.expression.binary;

import model.ProgramState;
import model.expression.ExpressionException;
import model.expression.ExpressionInterface;
import model.type.Types;
import model.value.BooleanValue;
import model.value.ValueInterface;

public class UnequalBinaryExpression extends BinaryExpression {

    public UnequalBinaryExpression(ExpressionInterface leftExpression, ExpressionInterface rightExpression) {
        super(leftExpression, rightExpression);
    }

    @Override
    public ValueInterface evaluate() throws ExpressionException {
        return null;
    }

    @Override
    public Types getExpectedType() {
        return null;
    }

    @Override
    public ValueInterface evaluate(ProgramState programState) throws ExpressionException {
        var leftValue = leftExpression.evaluate(programState);
        var rightValue = rightExpression.evaluate(programState);

        return new BooleanValue(!leftValue.equals(rightValue));
    }

    @Override
    public String toString() {
        return String.format("%s != %s", leftExpression, rightExpression);
    }
}
