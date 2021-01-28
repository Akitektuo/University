package model.statement;

import container.DictionaryInterface;
import model.ProgramState;
import model.expression.ExpressionException;
import model.expression.ExpressionInterface;
import model.type.TypeInterface;
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
        var conditionResult = (boolean) conditionExpression.evaluate(programState).getValue();

        if (conditionResult) {
            programState.pushStatement(this).pushStatement(blockStatement);
        }

        return null;
    }

    @Override
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeTable) throws StatementException, ExpressionException {
        var conditionType = conditionExpression.typeCheck(typeTable);

        if (conditionType.get() != Types.BOOLEAN) {
            throw new StatementException("Condition result is not of type boolean!");
        }

        blockStatement.typeCheck(typeTable.clone());

        return typeTable;
    }

    @Override
    public String toString() {
        return String.format("while (%s) do %s", conditionExpression, blockStatement);
    }
}
