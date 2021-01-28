package controller;

import container.List;
import container.ListInterface;
import model.ProgramState;
import model.statement.StatementException;
import model.value.ReferenceValue;
import repository.RepositoryInterface;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Controller {
    private final RepositoryInterface repository;
    private final boolean logSteps;
    private final ExecutorService executor = Executors.newFixedThreadPool(2);

    public Controller(RepositoryInterface repository) {
        this.repository = repository;
        this.logSteps = false;
    }

    public Controller(RepositoryInterface repository, boolean logSteps) {
        this.repository = repository;
        this.logSteps = logSteps;
    }

    public void addProgramState(ProgramState programState) {
        programState.checkTypes();
        repository.addProgramState(programState);
    }

    public void executeAllSteps() {
        var programStates = getUncompletedProgramStates();

        while (!programStates.isEmpty()) {
            try {
                executeOneStepForEachProgram(programStates);
            } catch (InterruptedException e) {
                throw new StatementException(e.getMessage());
            }
            collectGarbage(programStates);
            programStates = getUncompletedProgramStates();
        }

        repository.clear();
    }

    public ListInterface<ProgramState> getUncompletedProgramStates() {
        return repository.getAllProgramStates().filter(programState -> !programState.isCompleted());
    }

    public void executeOneStepForEachProgram(ListInterface<ProgramState> programStates) throws InterruptedException {
        if (logSteps) {
            programStates.forEach(repository::logProgramState);
        }

        var callableProgramStates = programStates
                .map(programState -> (Callable<ProgramState>) programState::oneStep);

        var newProgramStates = new List<>(executor.invokeAll(callableProgramStates.toCollection()))
                .map(programStateFuture -> {
                    try {
                        return programStateFuture.get();
                    } catch (InterruptedException | ExecutionException e) {
                        throw new StatementException(e.getMessage());
                    }
                }).filter(Objects::nonNull);

        if (logSteps) {
            programStates.forEach(repository::logProgramState);
        }

        if (!newProgramStates.isEmpty()) {
            repository.addProgramStates(newProgramStates);
        }
    }

    private ListInterface<Integer> getUsedAddresses(ListInterface<ProgramState> inProgramStates) {
        var addresses = new List<Integer>();

        inProgramStates.forEach(programState -> addresses.add(programState.getSystemTableValues()
                .filter(value -> value instanceof ReferenceValue)
                .flatMap(value -> ((ReferenceValue) value).getRelatedAddresses(programState.getMemoryHeap()))));

        return addresses;
    }

    private void collectGarbage(ListInterface<ProgramState> fromProgramStates) {
        var addressesInUse = getUsedAddresses(fromProgramStates);

        var someProgramState = fromProgramStates.first();
        if (someProgramState != null) {
            someProgramState.getMemoryHeap().filtered((key, value) -> addressesInUse.contains(key));
        }
    }
}
