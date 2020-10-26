package controller;

import container.ListInterface;
import model.ProgramState;
import model.expression.ExpressionException;
import model.statement.StatementException;
import repository.RepositoryInterface;

public class Controller {
    private final RepositoryInterface repository;
    private final boolean displaySteps;

    public Controller(RepositoryInterface repository) {
        this.repository = repository;
        this.displaySteps = false;
    }

    public Controller(RepositoryInterface repository, boolean displaySteps) {
        this.repository = repository;
        this.displaySteps = displaySteps;
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
            var executedProgramState = executeOneStep(currentProgramState);

            if (displaySteps) {
                System.out.println(executedProgramState);
            }
        }

        return currentProgramState.getOutput();
    }
}
