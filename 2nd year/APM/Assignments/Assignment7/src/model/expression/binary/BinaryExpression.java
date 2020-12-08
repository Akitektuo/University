package model.expression.binary;

import container.DictionaryInterface;
import model.ProgramState;
import model.expression.ExpressionErrorType;
import model.expression.ExpressionException;
import model.expression.ExpressionInterface;
import model.type.BooleanType;
import model.type.TypeInterface;
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

    public abstract Types getExpectedType();

    protected final int getIntegerValue(ExpressionInterface expression) {
        var value = expression.evaluate(programState);

        return ((IntegerValue) value).getValue();
    }

    protected final boolean getBooleanValue(ExpressionInterface expression) {
        var value = expression.evaluate(programState);

        return ((BooleanValue) value).getValue();
    }

    @Override
    public ValueInterface evaluate(ProgramState programState) throws ExpressionException {
        this.programState = programState;
        return evaluate();
    }

    @Override
    public TypeInterface typeCheck(DictionaryInterface<String, TypeInterface> typeTable) throws ExpressionException {
        var leftType = leftExpression.typeCheck(typeTable);
        var rightType = rightExpression.typeCheck(typeTable);

        var expectedType = getExpectedType();
        if (expectedType == null) {
            return new BooleanType();
        }

        if (leftType.get() != expectedType) {
            throw new ExpressionException(ExpressionErrorType.LEFT_OPERAND_WRONG_TYPE);
        }
        if (rightType.get() != expectedType) {
            throw new ExpressionException(ExpressionErrorType.RIGHT_OPERAND_WRONG_TYPE);
        }

        return leftType;
    }
}
