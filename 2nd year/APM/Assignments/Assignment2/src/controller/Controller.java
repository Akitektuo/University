package controller;

import container.ListInterface;
import model.ProgramState;
import model.expression.ExpressionException;
import model.statement.StatementException;
import repository.RepositoryInterface;

public class Controller {
    RepositoryInterface repository;

    public Controller(RepositoryInterface repository) {
        this.repository = repository;
    }

    public void addProgramState(ProgramState programState) {
        repository.addProgramState(programState);
    }

    public ProgramState executeOneStep(ProgramState state) throws StatementException, ExpressionException {
        return state.executeNext();
    }

    public ListInterface<String> executeAllSteps() throws StatementException, ExpressionException {
        var currentProgramState = repository.getCurrentProgramState();

        while (currentProgramState.canExecute()) {
            executeOneStep(currentProgramState);
        }

        return currentProgramState.getOutput();
    }
}
