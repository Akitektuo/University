package repository;

import model.ProgramState;

public interface RepositoryInterface {
    void addProgramState(ProgramState programState);

    ProgramState getCurrentProgramState();

    void logProgramState(ProgramState programState);
}
