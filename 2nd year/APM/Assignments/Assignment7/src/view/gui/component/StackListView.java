package view.gui.component;

import javafx.scene.control.ListView;
import model.ProgramState;
import view.gui.component.view.List;

public class StackListView {
    private final List<String> listView = new List<>();

    public ListView<String> create() {
        return listView.setGridPosition(1, 0).create();
    }

    public void setProgramState(ProgramState programState) {
        listView.setData(programState.getExecutionStack().toList().map(Object::toString));
    }
}
