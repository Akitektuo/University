package model.expression;

import container.DictionaryInterface;
import model.value.ValueInterface;

public class ArithmeticExpression implements ExpressionInterface {


    @Override
    public ValueInterface evaluate(DictionaryInterface<String, ValueInterface> variableTable) throws ExpressionException {
        return null;
    }
}
