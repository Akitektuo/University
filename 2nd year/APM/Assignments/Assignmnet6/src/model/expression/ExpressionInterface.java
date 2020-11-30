package model.expression;

import container.DictionaryInterface;
import model.ProgramState;
import model.type.TypeInterface;
import model.value.ValueInterface;

public interface ExpressionInterface {
    ValueInterface evaluate(ProgramState programState) throws ExpressionException;
    
    TypeInterface typeCheck(DictionaryInterface<String, TypeInterface> typeTable) throws ExpressionException;
}
