package view.gui;

import container.List;
import container.ListInterface;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import view.command.RunExampleCommand;

public class MainWindow extends Application {
    private static final ListInterface<RunExampleCommand> commands = new List<>();

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Toy Language Interpreter");
        stage.setOnCloseRequest(e -> System.exit(0));

        stage.setScene(new Scene(buildGrid(), 1200, 680));
        stage.show();
    }

    public void show() {
        launch();
    }

    public void addCommand(RunExampleCommand command) {
        commands.add(command);
    }

    private GridPane buildGrid() {
        var grid = new GridPane();

        grid.setPadding(new Insets(64));
        grid.setVgap(16);
        grid.setHgap(16);

        grid.getChildren().addAll(setupLabels().toCollection());

        return grid;
    }

    private ListInterface<Label> setupLabels() {
        return commands.mapIndexed((value, index) -> {
            var label = new Label(value.getDescription());

            label.setPadding(new Insets(8));
            label.setStyle("-fx-border-color: black");
            GridPane.setConstraints(label, index % 5, index / 5);
            label.setOnMouseClicked(e -> new ProgramExecutionWindow(value.getInitializedController()).showAndWait());

            return label;
        });
    }
}
