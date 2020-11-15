package model.statement;

import model.ProgramState;
import model.expression.ExpressionException;
import model.expression.ExpressionInterface;

public class AssignmentStatement implements StatementInterface {
    private final String variableName;
    private final ExpressionInterface expression;

    public AssignmentStatement(String variableName, ExpressionInterface expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws StatementException, ExpressionException {
        var expectedVariable = programState.getVariable(variableName);
        if (expectedVariable == null) {
            throw new StatementException(String.format("Variable '%s' has not been declared!", variableName));
        }

        var newVariableValue = expression.evaluate(programState);
        if (!newVariableValue.getType().equals(expectedVariable.getType())) {
            throw new StatementException("Types do not match in assignment!");
        }

        return programState.setVariable(variableName, newVariableValue);
    }

    @Override
    public String toString() {
        return String.format("%s = %s", variableName, expression);
    }
}
