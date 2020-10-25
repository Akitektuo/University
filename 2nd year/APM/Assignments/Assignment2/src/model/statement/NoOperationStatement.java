package model.statement;

import model.ProgramState;

public class NoOperationStatement implements StatementInterface {
    @Override
    public ProgramState execute(ProgramState programState) {
        return programState;
    }
}
