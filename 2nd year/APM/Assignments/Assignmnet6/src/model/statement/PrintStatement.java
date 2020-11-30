package model.statement;

import container.DictionaryInterface;
import model.ProgramState;
import model.expression.ExpressionException;
import model.expression.ExpressionInterface;
import model.type.TypeInterface;

public class PrintStatement implements StatementInterface {
    private final ExpressionInterface expression;

    public PrintStatement(ExpressionInterface expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws ExpressionException {
        var valueToOutput = expression.evaluate(programState);

        programState.addOutput(valueToOutput.toString());
        return null;
    }

    @Override
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeTable) throws StatementException, ExpressionException {
        expression.typeCheck(typeTable);

        return typeTable;
    }

    @Override
    public String toString() {
        return String.format("print %s", expression);
    }
}
