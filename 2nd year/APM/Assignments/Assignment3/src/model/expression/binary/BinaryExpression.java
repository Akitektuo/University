package model.expression.binary;

import container.DictionaryInterface;
import model.expression.ExpressionErrorType;
import model.expression.ExpressionException;
import model.expression.ExpressionInterface;
import model.type.Types;
import model.value.BooleanValue;
import model.value.IntegerValue;
import model.value.ValueInterface;

public class BinaryExpression implements ExpressionInterface {
    protected final ExpressionInterface leftExpression, rightExpression;
    private DictionaryInterface<String, ValueInterface> systemTable;

    public BinaryExpression(ExpressionInterface leftExpression, ExpressionInterface rightExpression) {
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
    }

    @Override
    public ValueInterface evaluate(DictionaryInterface<String, ValueInterface> systemTable) throws ExpressionException {
        this.systemTable = systemTable;
        return null;
    }

    protected final int getIntegerValue(
            ExpressionInterface expression,
            ExpressionErrorType errorType) throws ExpressionException {
        var value = expression.evaluate(systemTable);

        if (value.getType().get() != Types.NUMBER) {
            throw new ExpressionException(errorType);
        }

        return ((IntegerValue) value).getValue();
    }

    protected final boolean getBooleanValue(
            ExpressionInterface expression,
            ExpressionErrorType errorType) throws ExpressionException {
        var value = expression.evaluate(systemTable);

        if (value.getType().get() != Types.BOOLEAN) {
            throw new ExpressionException(errorType);
        }

        return ((BooleanValue) value).getValue();
    }
}
