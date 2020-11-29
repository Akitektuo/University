package model.statement;

import model.ProgramState;
import model.expression.ExpressionException;

public interface StatementInterface {
    ProgramState execute(ProgramState programState) throws StatementException, ExpressionException;
}
