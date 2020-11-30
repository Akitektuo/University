package model.expression.binary;

import model.ProgramState;
import model.expression.ExpressionErrorType;
import model.expression.ExpressionException;
import model.expression.ExpressionInterface;
import model.type.Types;
import model.value.BooleanValue;
import model.value.IntegerValue;
import model.value.ValueInterface;

public abstract class BinaryExpression implements ExpressionInterface {
    protected final ExpressionInterface leftExpression, rightExpression;
    private ProgramState programState;

    public BinaryExpression(ExpressionInterface leftExpression, ExpressionInterface rightExpression) {
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
    }

    public abstract ValueInterface evaluate() throws ExpressionException;

    protected final int getIntegerValue(
            ExpressionInterface expression,
            ExpressionErrorType errorType) throws ExpressionException {
        var value = expression.evaluate(programState);

        if (value.getType().get() != Types.NUMBER) {
            throw new ExpressionException(errorType);
        }

        return ((IntegerValue) value).getValue();
    }

    protected final boolean getBooleanValue(
            ExpressionInterface expression,
            ExpressionErrorType errorType) throws ExpressionException {
        var value = expression.evaluate(programState);

        if (value.getType().get() != Types.BOOLEAN) {
            throw new ExpressionException(errorType);
        }

        return ((BooleanValue) value).getValue();
    }

    @Override
    public ValueInterface evaluate(ProgramState programState) throws ExpressionException {
        this.programState = programState;
        return evaluate();
    }
}
