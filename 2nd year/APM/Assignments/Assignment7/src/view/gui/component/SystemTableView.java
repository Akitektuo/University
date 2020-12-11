package view.gui.component;

import javafx.scene.control.TableView;
import model.ProgramState;
import view.gui.component.view.Table;
import view.gui.component.view.TableViewModel;

public class SystemTableView {
    private final Table table = new Table("Variable name", "Value");

    public TableView<TableViewModel> create() {
        return table.setGridPosition(1, 1).create();
    }

    public void setProgramState(ProgramState programState) {
        table.setData(programState.getSystemTable()
                .getEntries()
                .map(entry -> new TableViewModel(entry.getKey(), entry.getValue().toString())));
    }
}
