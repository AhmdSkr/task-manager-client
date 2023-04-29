package com.rakkiz.taskmanagerclient.controller;

import com.rakkiz.taskmanagerclient.TaskManagerApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.io.IOException;

public class PomodoroController {
    @FXML
    private Label title, description;
    @FXML
    private VBox content;

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setDescription(String description) {
        this.description.setText(description);
    }


    public void addPomTimer(int duration) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TaskManagerApplication.class.getResource("fxml/pomodoroTimer.fxml"));
        HBox root = fxmlLoader.load();
        PomodoroTimerController pomodoroTimerController = fxmlLoader.getController();
        pomodoroTimerController.setOriginalCycle(duration);
        pomodoroTimerController.setPomodoroController(this);
        content.getChildren().add(0, root);
    }
}
