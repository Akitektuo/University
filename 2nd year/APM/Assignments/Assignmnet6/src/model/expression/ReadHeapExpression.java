package model.expression;

import container.DictionaryInterface;
import model.ProgramState;
import model.type.ReferenceType;
import model.type.TypeInterface;
import model.type.Types;
import model.value.ValueInterface;

public class ReadHeapExpression implements ExpressionInterface {
    private final ExpressionInterface expression;

    public ReadHeapExpression(ExpressionInterface expression) {
        this.expression = expression;
    }

    @Override
    public ValueInterface evaluate(ProgramState programState) throws ExpressionException {
        ValueInterface value = expression.evaluate(programState);

        return programState.getFromMemory((int) value.getValue());
    }

    @Override
    public TypeInterface typeCheck(DictionaryInterface<String, TypeInterface> typeTable) throws ExpressionException {
        var type = expression.typeCheck(typeTable);

        if (type.get() != Types.REFERENCE) {
            throw new ExpressionException(String.format("Expression '%s' does not represent a reference type!", expression));
        }

        return ((ReferenceType) type).getGenericType();
    }

    @Override
    public String toString() {
        return String.format("readHead(%s)", expression);
    }
}
