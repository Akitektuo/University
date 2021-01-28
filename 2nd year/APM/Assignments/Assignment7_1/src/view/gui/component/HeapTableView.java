package view.gui.component;

import container.List;
import javafx.scene.control.TableView;
import view.gui.ObservableProgramStates;
import view.gui.component.view.Table;
import view.gui.component.view.TableViewModel;

public class HeapTableView {
    private final ObservableProgramStates programStates;

    public HeapTableView(ObservableProgramStates programStates) {
        this.programStates = programStates;
    }

    public TableView<TableViewModel> create() {
        var table = new Table("Address", "Value").setGridPosition(0, 1);

        programStates.addListener(programStates -> {
            var someProgramState = programStates.first();

            table.setData(someProgramState == null ?
                    new List<>() :
                    someProgramState.getMemoryHeap()
                        .getEntries()
                        .map(entry -> new TableViewModel(String.valueOf(entry.getKey()), entry.getValue().toString())));
        });

        return table.create();
    }
}
