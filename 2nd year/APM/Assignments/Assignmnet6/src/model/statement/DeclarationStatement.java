package model.statement;

import container.DictionaryInterface;
import model.ProgramState;
import model.expression.ExpressionException;
import model.type.TypeInterface;

public class DeclarationStatement implements StatementInterface {
    private final String variableName;
    private final TypeInterface variableType;

    public DeclarationStatement(String variableName, TypeInterface variableType) {
        this.variableName = variableName;
        this.variableType = variableType;
    }

    @Override
    public ProgramState execute(ProgramState programState) {
        programState.setVariable(variableName, variableType.getDefaultValue());

        return null;
    }

    @Override
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeTable) throws StatementException, ExpressionException {
        typeTable.set(variableName, variableType);

        return typeTable;
    }

    @Override
    public String toString() {
        return String.format("%s %s", variableType, variableName);
    }
}
