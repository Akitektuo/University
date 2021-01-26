package view.gui;

import container.ListInterface;
import controller.Controller;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.ProgramState;
import view.gui.component.*;

public class ProgramExecutionWindow {
    private final Controller controller;
    private final ObservableProgramStates programStates = new ObservableProgramStates();
    private final Stage window = new Stage();

    public ProgramExecutionWindow(Controller controller) {
        this.controller = controller;
    }

    public void showAndWait() {
        if (controller == null) {
            AlertBox.show("Type checker failed!", "See your console for more details.");
            return;
        }
        initializeStage().show();
    }

    private Stage initializeStage() {

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Toy Language Interpreter");

        var layout = createMainLayout(createGridLayout(), createOneStepButton());
        window.setScene(new Scene(layout, 1600, 900));

        return window;
    }

    private VBox createMainLayout(GridPane gridLayout, Button button) {
        var layout = new VBox(16);

        layout.getChildren().addAll(gridLayout, button);
        layout.setAlignment(Pos.CENTER);

        return layout;
    }

    private Button createOneStepButton() {
        var button = new Button("Start program");

        button.setOnAction(event -> {
            button.setText("Run one step");

            var programStates = controller.getUncompletedProgramStates();
            if (programStates.isEmpty()) {
                AlertBox.show("Done", "Execution has completed successfully!");
                button.setText("Close window");
                button.setOnAction(e -> window.close());
            } else {
                handleExecute(programStates);
            }
        });

        return button;
    }

    private GridPane createGridLayout() {
        var grid = new GridPane();

        grid.setAlignment(Pos.CENTER);

        var stackList = new StackListView();
        var systemTable = new SystemTableView();
        var fileList = new FileListView();

        var programList = new ProgramListView(programStates, programState -> {
            stackList.setProgramState(programState);
            systemTable.setProgramState(programState);
            fileList.setProgramState(programState);
        });

        var heapTable = new HeapTableView(programStates);
        var outputList = new OutputListView(programStates);


        grid.getChildren().addAll(
                programList.create(),
                heapTable.create(),
                stackList.create(),
                systemTable.create(),
                outputList.create(),
                fileList.create());

        return grid;
    }

    private void handleExecute(ListInterface<ProgramState> programStates) {
        this.programStates.setProgramState(programStates);

        try {
            controller.executeOneStepForEachProgram(programStates);
        } catch (InterruptedException interruptedException) {
            handleException(interruptedException);
        }
    }

    private void handleException(Exception exception) {
        AlertBox.show("Error", exception.getMessage());
        window.close();
    }
}
