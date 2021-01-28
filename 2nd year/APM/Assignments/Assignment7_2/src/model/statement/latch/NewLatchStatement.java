package model.statement.latch;

import container.DictionaryInterface;
import model.ProgramState;
import model.expression.ExpressionException;
import model.expression.ExpressionInterface;
import model.statement.StatementException;
import model.statement.StatementInterface;
import model.type.TypeInterface;
import model.type.Types;
import model.value.IntegerValue;

public class NewLatchStatement implements StatementInterface {
    private final String variableName;
    private final ExpressionInterface expression;

    public NewLatchStatement(String variableName, ExpressionInterface expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws StatementException, ExpressionException {
        var address = programState.defineLatch(expression.evaluate(programState));
        programState.setVariable(variableName, new IntegerValue(address));

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

        var evaluatedExpression = expression.typeCheck(typeTable);
        if (evaluatedExpression.get() != Types.NUMBER) {
            throw new StatementException("Expression in newLatch() must return an integer value!");
        }

        return typeTable;
    }

    @Override
    public String toString() {
        return String.format("newLatch(%s, %s)", variableName, expression);
    }
}
