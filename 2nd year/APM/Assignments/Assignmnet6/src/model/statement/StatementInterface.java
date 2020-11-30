package model.statement;

import container.DictionaryInterface;
import model.ProgramState;
import model.expression.ExpressionException;
import model.type.TypeInterface;

public interface StatementInterface {
    ProgramState execute(ProgramState programState) throws StatementException, ExpressionException;

    DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeTable)
            throws StatementException, ExpressionException;
}
