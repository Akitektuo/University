package controller;

import container.ListInterface;
import model.ProgramState;
import model.expression.ExpressionException;
import model.statement.StatementException;
import repository.RepositoryInterface;

public class Controller {
    private final RepositoryInterface repository;
    private final boolean logSteps;

    public Controller(RepositoryInterface repository) {
        this.repository = repository;
        this.logSteps = false;
    }

    public Controller(RepositoryInterface repository, boolean logSteps) {
        this.repository = repository;
        this.logSteps = logSteps;
    }

    public void addProgramState(ProgramState programState) {
        repository.addProgramState(programState);
    }

    public ProgramState executeOneStep(ProgramState state) throws StatementException, ExpressionException {
        return state.executeNext();
    }

    public ListInterface<String> executeAllSteps() throws StatementException, ExpressionException {
        var currentProgramState = repository.getCurrentProgramState();

        if (logSteps) {
            repository.logProgramState(currentProgramState);
        }

        while (currentProgramState.canExecute()) {
            var executedProgramState = executeOneStep(currentProgramState);

            if (logSteps) {
                repository.logProgramState(executedProgramState);
            }

            var wasMemoryHeapModified = executedProgramState.collectGarbage();

            if (logSteps && wasMemoryHeapModified) {
                repository.logProgramState(executedProgramState);
            }
        }

        return currentProgramState.getOutput();
    }
}
