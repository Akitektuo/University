package model.expression;

import model.ProgramState;
import model.value.ValueInterface;

public class ValueExpression implements ExpressionInterface {
    private final ValueInterface value;

    public ValueExpression(ValueInterface value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public ValueInterface evaluate(ProgramState programState) throws ExpressionException {
        return value;
    }
}
