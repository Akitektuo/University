package model.statement.heap;

import model.ProgramState;
import model.expression.ExpressionException;
import model.expression.ExpressionInterface;
import model.statement.StatementException;
import model.statement.StatementInterface;
import model.type.Types;
import model.value.ReferenceValue;

public class AllocateHeapStatement implements StatementInterface {
    private final String variableName;
    private final ExpressionInterface expression;

    public AllocateHeapStatement(String variableName, ExpressionInterface expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws StatementException, ExpressionException {
        var variable = programState.getVariable(variableName);
        if (variable == null) {
            throw new StatementException("Variable '%s' has not been declared!", variableName);
        }
        if (variable.getType().get() != Types.REFERENCE) {
            throw new StatementException("Variable '%s' is not of type reference!", variableName);
        }

        var value = expression.evaluate(programState);
        if (!((ReferenceValue) variable).getGenericType().equals(value.getType())) {
            throw new StatementException("The reference of variable '%s' does not match the given value's type", variableName);
        }

        var address = programState.allocateInMemory(value);
        programState.setVariable(variableName, new ReferenceValue(address, value.getType()));
        return null;
    }

    @Override
    public String toString() {
        return String.format("new(%s, %s)", variableName, expression);
    }
}
