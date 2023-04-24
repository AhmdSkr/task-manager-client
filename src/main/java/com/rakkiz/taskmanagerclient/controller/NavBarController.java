package com.rakkiz.taskmanagerclient.controller;

import com.rakkiz.taskmanagerclient.TaskManagerApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class NavBarController {
    @FXML
    private Hyperlink taskSack;
    @FXML
    private Hyperlink pomodoro;

    @FXML
    private VBox rootPane;

    public void initialize() {
        VBox.setVgrow(rootPane, Priority.ALWAYS);
    }

    @FXML
    private void onTaskSackClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TaskManagerApplication.class.getResource("fxml/task-sack.fxml"));
        Stage stage = (Stage) taskSack.getScene().getWindow();

        //TODO : add the taskSack as child in MainScaffold

        stage.show();
    }

    @FXML
    private void onPomodoroClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TaskManagerApplication.class.getResource("fxml/pomodoro.fxml"));
        Stage stage = (Stage) pomodoro.getScene().getWindow();

        //TODO : add the taskSack as child in MainScaffold

        stage.show();
    }

}
