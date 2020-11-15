package model.expression;

import container.DictionaryInterface;
import model.value.ValueInterface;

public class VariableExpression implements ExpressionInterface {
    private final String variableName;

    public VariableExpression(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public ValueInterface evaluate(DictionaryInterface<String, ValueInterface> systemTable) {
        return systemTable.get(variableName);
    }

    @Override
    public String toString() {
        return variableName;
    }
}
