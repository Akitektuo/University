package model.expression;

import container.DictionaryInterface;
import model.value.ValueInterface;

public class VariableExpression implements ExpressionInterface {
    private final String variableName;

    public VariableExpression(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public ValueInterface evaluate(DictionaryInterface<String, ValueInterface> variableTable) {
        return variableTable.get(variableName);
    }
}
