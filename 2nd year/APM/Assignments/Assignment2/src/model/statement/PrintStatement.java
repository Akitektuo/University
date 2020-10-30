package model.statement;

import model.ProgramState;
import model.expression.ExpressionException;
import model.expression.ExpressionInterface;

public class PrintStatement implements StatementInterface {
    private final ExpressionInterface expression;

    public PrintStatement(ExpressionInterface expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws ExpressionException {
        var valueToOutput = expression.evaluate(programState.getSystemTable());

        return programState.addOutput(valueToOutput.toString());
    }

    @Override
    public String toString() {
        return String.format("print %s", expression);
    }
}
