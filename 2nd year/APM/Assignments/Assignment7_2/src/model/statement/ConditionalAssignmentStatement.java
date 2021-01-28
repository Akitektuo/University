package model.statement;

import container.DictionaryInterface;
import model.ProgramState;
import model.expression.ExpressionException;
import model.expression.ExpressionInterface;
import model.type.TypeInterface;
import model.type.Types;

public class ConditionalAssignmentStatement implements StatementInterface {
    private final String variableName;
    private final ExpressionInterface conditionExpression;
    private final ExpressionInterface trueExpression;
    private final ExpressionInterface falseExpression;

    public ConditionalAssignmentStatement(String variableName, ExpressionInterface conditionExpression, ExpressionInterface trueExpression, ExpressionInterface falseExpression) {
        this.variableName = variableName;
        this.conditionExpression = conditionExpression;
        this.trueExpression = trueExpression;
        this.falseExpression = falseExpression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws StatementException, ExpressionException {
        var expectedVariable = programState.getVariable(variableName);
        if (expectedVariable == null) {
            throw new StatementException("Variable '%s' has not been declared!", variableName);
        }

        programState.setVariable(variableName,
                (boolean) conditionExpression.evaluate(programState).getValue() ?
                    trueExpression.evaluate(programState) :
                    falseExpression.evaluate(programState));

        return null;
    }

    @Override
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeTable) throws StatementException, ExpressionException {
        var conditionType = conditionExpression.typeCheck(typeTable);
        if (conditionType.get() != Types.BOOLEAN) {
            throw new StatementException("The condition must return boolean type!");
        }

        var variableType = typeTable.get(variableName);

        var trueType = trueExpression.typeCheck(typeTable);
        if (!trueType.equals(variableType)) {
            throw new StatementException("Types do not match in true assignment!");
        }

        var falseType = falseExpression.typeCheck(typeTable);
        if (!falseType.equals(variableType)) {
            throw new StatementException("Types do not match in false assignment!");
        }

        return typeTable;
    }

    @Override
    public String toString() {
        return String.format("%s = %s ? %s : %s", variableName, conditionExpression, trueExpression, falseExpression);
    }
}
