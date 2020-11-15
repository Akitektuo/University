package model.expression;

import container.DictionaryInterface;
import model.value.ValueInterface;

public interface ExpressionInterface {
    ValueInterface evaluate(DictionaryInterface<String, ValueInterface> systemTable) throws ExpressionException;
}
