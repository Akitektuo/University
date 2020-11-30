package repository;

import container.ListInterface;
import model.ProgramState;

public interface RepositoryInterface {
    void addProgramState(ProgramState programState);

    ListInterface<ProgramState> addProgramStates(ListInterface<ProgramState> programStates);

    ListInterface<ProgramState> getAllProgramStates();

    void logProgramState(ProgramState programState);

    void clear();
}
