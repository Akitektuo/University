package view.gui;

import container.List;
import container.ListInterface;
import model.ProgramState;

import java.util.function.Consumer;

public class ObservableProgramStates {
    private final ListInterface<Consumer<ListInterface<ProgramState>>> listeners = new List<>();
    private ListInterface<ProgramState> programStates;

    public void addListener(Consumer<ListInterface<ProgramState>> onChange) {
        listeners.add(onChange);
    }

    public ListInterface<ProgramState> getProgramState() {
        return programStates;
    }

    public void setProgramState(ListInterface<ProgramState> programStates) {
        this.programStates = programStates;
        listeners.forEach(listener -> listener.accept(programStates));
    }
}
