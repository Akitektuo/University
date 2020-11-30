package model.statement;

import container.DictionaryInterface;
import model.ProgramState;
import model.expression.ExpressionException;
import model.type.TypeInterface;

public class NoOperationStatement implements StatementInterface {
    @Override
    public ProgramState execute(ProgramState programState) {
        return null;
    }

    @Override
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeTable) throws StatementException, ExpressionException {
        return typeTable;
    }

    @Override
    public String toString() {
        return "NoOp";
    }
}
