package model.statement;

import container.DictionaryInterface;
import model.ProgramState;
import model.expression.ExpressionException;
import model.expression.ExpressionInterface;
import model.type.NumberType;
import model.type.TypeInterface;
import model.type.Types;

public class ForStatement implements StatementInterface {
    private final String variableName;
    private final ExpressionInterface initialExpression;
    private final ExpressionInterface conditionExpression;
    private final ExpressionInterface incrementExpression;
    private final StatementInterface blockStatement;

    public ForStatement(String variableName, ExpressionInterface initialExpression, ExpressionInterface conditionExpression, ExpressionInterface incrementExpression, StatementInterface blockStatement) {
        this.variableName = variableName;
        this.initialExpression = initialExpression;
        this.conditionExpression = conditionExpression;
        this.incrementExpression = incrementExpression;
        this.blockStatement = blockStatement;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws StatementException, ExpressionException {
        if (programState.getVariable(variableName) == null) {
            programState.setVariable(variableName, initialExpression.evaluate(programState));
        } else {
            programState.setVariable(variableName, incrementExpression.evaluate(programState));
        }

        var conditionResult = (int) conditionExpression.evaluate(programState).getValue();

        if ((int) programState.getVariable(variableName).getValue() < conditionResult) {
            programState.pushStatement(this).pushStatement(blockStatement);
        }

        return null;
    }

    @Override
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeTable) throws StatementException, ExpressionException {
        typeTable.set(variableName, new NumberType());

        var initialType = initialExpression.typeCheck(typeTable);
        var conditionType = conditionExpression.typeCheck(typeTable);
        var incrementType = incrementExpression.typeCheck(typeTable);

        if (initialType.get() != Types.NUMBER) {
            throw new StatementException("For initialization does not return type integer!");
        }

        if (conditionType.get() != Types.NUMBER) {
            throw new StatementException("For condition does not return type integer!");
        }

        if (incrementType.get() != Types.NUMBER) {
            throw new StatementException("For incrementation does not return type integer!");
        }

        blockStatement.typeCheck(typeTable.clone());

        return typeTable;
    }

    @Override
    public String toString() {
        return String.format("for (%s = %s; %s < %s; %s = %s) do %s",
                variableName, initialExpression,
                variableName, conditionExpression,
                variableName, incrementExpression,
                blockStatement);
    }
}
