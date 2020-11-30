package model.statement;

import container.DictionaryInterface;
import model.ProgramState;
import model.expression.ExpressionException;
import model.expression.ExpressionInterface;
import model.type.TypeInterface;
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
        var conditionResult = (boolean) conditionExpression.evaluate(programState).getValue();

        programState.pushStatement(conditionResult ? trueBlock : falseBlock);
        return null;
    }

    @Override
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeTable) throws StatementException, ExpressionException {
        var conditionType = conditionExpression.typeCheck(typeTable);

        if (conditionType.get() != Types.BOOLEAN) {
            throw new StatementException("Condition result is not of type boolean!");
        }

        trueBlock.typeCheck(typeTable.clone());
        falseBlock.typeCheck(typeTable.clone());

        return typeTable;
    }

    @Override
    public String toString() {
        return String.format("if (%s) %s else %s", conditionExpression, trueBlock, falseBlock);
    }
}
