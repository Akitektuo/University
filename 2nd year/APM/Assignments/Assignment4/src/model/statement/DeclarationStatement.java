package model.statement;

import model.ProgramState;
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
        return programState.setVariable(variableName, variableType.getDefaultValue());
    }

    @Override
    public String toString() {
        return String.format("%s %s", variableType, variableName);
    }
}
