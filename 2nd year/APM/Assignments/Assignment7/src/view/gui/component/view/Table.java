package view.gui.component.view;

import container.ListInterface;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

public class Table {
    private final TableView<TableViewModel> tableView = new TableView<>();

    public Table(String keyName, String valueName) {
        initializeColumns(keyName, valueName);
    }

    public TableView<TableViewModel> create() {
        return tableView;
    }

    @SuppressWarnings("unchecked")
    private Table initializeColumns(String keyName, String valueName) {
        var keyColumn = new TableColumn<TableViewModel, String>(keyName);
        keyColumn.setMinWidth(200);
        keyColumn.setCellValueFactory(new PropertyValueFactory<>("key"));

        var valueColumn = new TableColumn<TableViewModel, String>(valueName);
        valueColumn.setMinWidth(200);
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

        tableView.getColumns().addAll(keyColumn, valueColumn);
        return this;
    }

    public Table setGridPosition(int column, int row) {
        GridPane.setConstraints(tableView, column, row);

        return this;
    }

    public Table setData(ListInterface<TableViewModel> data) {
        tableView.getItems().clear();
        tableView.getItems().addAll(data.toCollection());

        return this;
    }
}
