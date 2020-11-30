package model.statement;

import container.DictionaryInterface;
import model.ProgramState;
import model.expression.ExpressionException;
import model.type.TypeInterface;

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
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeTable) throws StatementException, ExpressionException {
        blockStatement.typeCheck(typeTable.clone());

        return typeTable;
    }

    @Override
    public String toString() {
        return String.format("fork { %s }", blockStatement);
    }
}
