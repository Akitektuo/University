package model.statement.latch;

import container.DictionaryInterface;
import model.ProgramState;
import model.expression.ExpressionException;
import model.statement.StatementException;
import model.statement.StatementInterface;
import model.type.TypeInterface;
import model.type.Types;

public class AwaitStatement implements StatementInterface {
    private final String variableName;

    public AwaitStatement(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws StatementException, ExpressionException {
        var variableValue = (int) programState.getVariable(variableName).getValue();

        var latch = programState.getLatch(variableValue);
        if (latch == null) {
            throw new StatementException("Latch with address @%s does not exist!", variableValue);
        }

        if (!latch.getValue().equals(0)) {
            programState.pushStatement(this);
        }
        return null;
    }

    @Override
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeTable) throws StatementException, ExpressionException {
        var variableType = typeTable.get(variableName);
        if (variableType == null) {
            throw new StatementException("Variable '%s' is not defined!", variableName);
        }
        if (variableType.get() != Types.NUMBER) {
            throw new StatementException("Variable '%s' is not of type number!", variableName);
        }

        return typeTable;
    }

    @Override
    public String toString() {
        return String.format("await (%s)", variableName);
    }
}
