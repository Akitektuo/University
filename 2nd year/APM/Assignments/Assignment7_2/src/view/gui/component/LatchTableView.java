package view.gui.component;

import container.List;
import javafx.scene.control.TableView;
import view.gui.ObservableProgramStates;
import view.gui.component.view.Table;
import view.gui.component.view.TableViewModel;

public class LatchTableView {
    private final ObservableProgramStates programStates;

    public LatchTableView(ObservableProgramStates programStates) {
        this.programStates = programStates;
    }

    public TableView<TableViewModel> create() {
        var table = new Table("Address", "Count").setGridPosition(3, 0);

        programStates.addListener(programStates -> {
            var someProgramState = programStates.first();

            table.setData(someProgramState == null ?
                    new List<>() :
                    someProgramState.getLatchTable()
                            .getEntries()
                            .map(entry -> new TableViewModel(String.valueOf(entry.getKey()), entry.getValue().toString())));
        });

        return table.create();
    }
}
