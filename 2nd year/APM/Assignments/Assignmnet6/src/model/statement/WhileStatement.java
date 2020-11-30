package model.statement;

import model.ProgramState;
import model.expression.ExpressionException;
import model.expression.ExpressionInterface;
import model.type.Types;

public class WhileStatement implements StatementInterface {
    private final ExpressionInterface conditionExpression;
    private final StatementInterface blockStatement;

    public WhileStatement(ExpressionInterface conditionExpression, StatementInterface blockStatement) {
        this.conditionExpression = conditionExpression;
        this.blockStatement = blockStatement;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws StatementException, ExpressionException {
        var conditionValue = conditionExpression.evaluate(programState);
        if (conditionValue.getType().get() != Types.BOOLEAN) {
            throw new StatementException("The condition must return a boolean value!");
        }

        if ((boolean)conditionValue.getValue()) {
            programState.pushStatement(this).pushStatement(blockStatement);
        }

        return null;
    }

    @Override
    public String toString() {
        return String.format("while (%s) do %s", conditionExpression, blockStatement);
    }
}
