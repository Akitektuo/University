package view.gui.component;

import javafx.scene.control.ListView;
import view.gui.ObservableProgramStates;
import view.gui.component.view.List;

public class OutputListView {
    private final ObservableProgramStates programStates;

    public OutputListView(ObservableProgramStates programStates) {
        this.programStates = programStates;
    }

    public ListView<String> create() {
        var listView = new List<String>().setGridPosition(2, 1);

        programStates.addListener(programStates -> listView.setData(programStates == null ?
                new container.List<>() :
                programStates.first().getOutput()));

        return listView.create();
    }
}
