package model.expression;

import container.DictionaryInterface;
import model.ProgramState;
import model.type.TypeInterface;
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

    @Override
    public TypeInterface typeCheck(DictionaryInterface<String, TypeInterface> typeTable) throws ExpressionException {
        return value.getType();
    }
}
