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
        var systemTable = programState.getSystemTable();

        var expectedVariableType = systemTable.get(variableName).getType();
        var newVariableValue = expression.evaluate(systemTable);

        if (!newVariableValue.getType().equals(expectedVariableType)) {
            throw new StatementException("Types do not match in assignment!");
        }

        return programState.setVariable(variableName, newVariableValue);
    }

    @Override
    public String toString() {
        return String.format("%s = %s", variableName, expression);
    }
}
