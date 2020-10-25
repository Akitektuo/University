package model.expression;

import container.DictionaryInterface;
import model.value.ValueInterface;

public class ValueExpression implements ExpressionInterface {
    private final ValueInterface value;

    public ValueExpression(ValueInterface value) {
        this.value = value;
    }

    @Override
    public ValueInterface evaluate(DictionaryInterface<String, ValueInterface> variableTable) {
        return value;
    }
}
