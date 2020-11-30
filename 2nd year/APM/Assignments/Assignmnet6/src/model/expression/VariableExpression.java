package model.expression;

import container.DictionaryInterface;
import model.ProgramState;
import model.value.ValueInterface;

public class VariableExpression implements ExpressionInterface {
    private final String variableName;

    public VariableExpression(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public String toString() {
        return variableName;
    }

    @Override
    public ValueInterface evaluate(ProgramState programState) throws ExpressionException {
        return programState.getVariable(variableName);
    }
}
