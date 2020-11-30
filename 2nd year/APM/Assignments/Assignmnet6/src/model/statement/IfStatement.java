package model.statement;

import model.ProgramState;
import model.expression.ExpressionException;
import model.expression.ExpressionInterface;
import model.type.Types;

public class IfStatement implements StatementInterface {
    private final ExpressionInterface conditionExpression;
    private final StatementInterface trueBlock, falseBlock;

    public IfStatement(ExpressionInterface conditionExpression, StatementInterface trueBlock, StatementInterface falseBlock) {
        this.conditionExpression = conditionExpression;
        this.trueBlock = trueBlock;
        this.falseBlock = falseBlock;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws StatementException, ExpressionException {
        var conditionResultValue = conditionExpression.evaluate(programState);
        if (conditionResultValue.getType().get() != Types.BOOLEAN) {
            throw new StatementException("Condition result is not of type boolean!");
        }

        programState.pushStatement((boolean) conditionResultValue.getValue() ? trueBlock : falseBlock);
        return null;
    }

    @Override
    public String toString() {
        return String.format("if (%s) %s else %s", conditionExpression, trueBlock, falseBlock);
    }
}
