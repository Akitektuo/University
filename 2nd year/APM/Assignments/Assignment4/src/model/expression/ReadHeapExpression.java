package model.expression;

import model.ProgramState;
import model.value.ReferenceValue;
import model.value.ValueInterface;

public class ReadHeapExpression implements ExpressionInterface {
    private final ExpressionInterface expression;

    public ReadHeapExpression(ExpressionInterface expression) {
        this.expression = expression;
    }

    @Override
    public ValueInterface evaluate(ProgramState programState) throws ExpressionException {
        ValueInterface value = expression.evaluate(programState);

        if (!(value instanceof ReferenceValue)) {
            throw new ExpressionException(String.format("Expression '%s' does not represent a reference type!", expression));
        }

        return programState.getFromMemory((int) value.getValue());
    }

    @Override
    public String toString() {
        return String.format("readHead(%s)", expression);
    }
}
