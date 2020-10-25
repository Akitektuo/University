package repository;

import container.List;
import container.ListInterface;
import model.ProgramState;

public class Repository implements RepositoryInterface {
    private final ListInterface<ProgramState> programStates = new List<>();

    @Override
    public void addProgramState(ProgramState programState) {
        programStates.add(programState);
    }

    @Override
    public ProgramState getCurrentProgramState() {
        return programStates.getLast();
    }
}
