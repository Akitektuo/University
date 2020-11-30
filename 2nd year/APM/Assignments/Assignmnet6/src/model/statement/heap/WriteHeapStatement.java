package model.statement.heap;

import model.ProgramState;
import model.expression.ExpressionException;
import model.expression.ExpressionInterface;
import model.statement.StatementException;
import model.statement.StatementInterface;
import model.type.Types;
import model.value.ReferenceValue;

public class WriteHeapStatement implements StatementInterface {
    private final String variableName;
    private final ExpressionInterface expression;

    public WriteHeapStatement(String variableName, ExpressionInterface expression) {
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

        programState.setInMemory((Integer) variable.getValue(), value);
        return null;
    }

    @Override
    public String toString() {
        return String.format("writeHeap(%s, %s)", variableName, expression);
    }
}
