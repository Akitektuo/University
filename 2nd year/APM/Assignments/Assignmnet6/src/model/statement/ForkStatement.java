package model.statement;

import model.ProgramState;
import model.expression.ExpressionException;

public class ForkStatement implements StatementInterface {
    private final StatementInterface blockStatement;

    public ForkStatement(StatementInterface blockStatement) {
        this.blockStatement = blockStatement;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws StatementException, ExpressionException {
        return new ProgramState(blockStatement, programState);
    }

    @Override
    public String toString() {
        return String.format("fork { %s }", blockStatement);
    }
}
