package view.gui.component;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.ProgramState;
import view.gui.ObservableProgramStates;
import view.gui.component.view.List;

import java.util.function.Consumer;

public class ProgramListView {
    private final ObservableProgramStates programStates;
    private final Consumer<ProgramState> onSelectProgram;

    public ProgramListView(ObservableProgramStates programStates, Consumer<ProgramState> onSelectProgram) {
        this.programStates = programStates;
        this.onSelectProgram = onSelectProgram;
    }

    public VBox create() {
        var programsLayout = new VBox(16);

        programsLayout.setAlignment(Pos.CENTER);
        GridPane.setConstraints(programsLayout, 0, 0);

        programsLayout.getChildren().addAll(createLabel(), createList());

        return programsLayout;
    }

    private Label createLabel() {
        var label = new Label("Number of uncompleted program states.");

        // React to program state changes
        programStates.addListener(programStates -> {
            var numberOfPrograms = programStates.getSize();

            var text = numberOfPrograms == 1 ?
                    "There is one uncompleted program state." :
                    String.format("There are %d uncompleted program states.", numberOfPrograms);

            label.setText(text);
        });

        return label;
    }

    private ListView<Integer> createList() {
        var listView = new List<Integer>();

        // React to program state changes
        programStates.addListener(programStates -> {
            listView.setData(programStates.map(ProgramState::getId));
            onSelectProgram.accept(programStates.first());
        });

        listView.setOnSelectListener(programStates, onSelectProgram);

        return listView.create();
    }
}
