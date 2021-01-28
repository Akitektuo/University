package view.gui.component.view;

import container.ListInterface;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.GridPane;
import model.ProgramState;
import view.gui.ObservableProgramStates;

import java.util.function.Consumer;

public class List<T> {
    private final ListView<T> listView = new ListView<>();

    public ListView<T> create() {
        return listView;
    }

    public List<T> setGridPosition(int column, int row) {
        GridPane.setConstraints(listView, column, row);

        return this;
    }

    public List<T> setData(ListInterface<T> data) {
        listView.getItems().clear();
        listView.getItems().addAll(data.toCollection());

        return this;
    }

    public List<T> setOnSelectListener(ObservableProgramStates programStates, Consumer<ProgramState> onSelectListener) {
        if (onSelectListener != null) {
            listView.getSelectionModel()
                    .selectedItemProperty()
                    .addListener((observable, oldValue, newValue) -> {
                        if (newValue != null) {
                            onSelectListener.accept(programStates.getProgramState()
                                    .find(programState -> programState.getId().equals(newValue)));
                        }
                    });
        }

        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        return this;
    }
}
