package view.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {

    public static void show(String title, String message) {
        var stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.setMinWidth(250);

        var messageLabel = new Label(message);
        var okButton = new Button("OK");

        okButton.setOnAction(e -> stage.close());

        var layout = new VBox(10);
        layout.getChildren().addAll(messageLabel, okButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(16));

        stage.setScene(new Scene(layout));
        stage.showAndWait();
    }
}
