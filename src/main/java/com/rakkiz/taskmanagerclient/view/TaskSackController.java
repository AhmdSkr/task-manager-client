package com.rakkiz.taskmanagerclient.view;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;

public class TaskSackController {
    @FXML
    private Line line;
    @FXML
    private VBox lineVBox, unVBox, sVBox;

    public void initialize() {

        //Bind the height of the line to the height of the vbox
        double maxHeight = Math.max(unVBox.getHeight(), sVBox.getHeight());
        lineVBox.setMaxHeight(maxHeight);
        line.endYProperty().bind(lineVBox.heightProperty());

    }
}
