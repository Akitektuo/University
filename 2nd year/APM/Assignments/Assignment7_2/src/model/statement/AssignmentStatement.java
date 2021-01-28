package model.statement;

import container.DictionaryInterface;
import model.ProgramState;
import model.expression.ExpressionException;
import model.expression.ExpressionInterface;
import model.type.TypeInterface;

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
            throw new StatementException("Variable '%s' has not been declared!", variableName);
        }

        programState.setVariable(variableName, expression.evaluate(programState));
        return null;
    }

    @Override
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeTable) throws StatementException, ExpressionException {
        var expectedType = typeTable.get(variableName);
        var type = expression.typeCheck(typeTable);

        if (!expectedType.equals(type)) {
            throw new StatementException("Types do not match in assignment!");
        }

        return typeTable;
    }

    @Override
    public String toString() {
        return String.format("%s = %s", variableName, expression);
    }
}
