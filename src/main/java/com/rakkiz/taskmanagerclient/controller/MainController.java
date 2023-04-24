package com.rakkiz.taskmanagerclient.controller;

import com.rakkiz.taskmanagerclient.TaskManagerApplication;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    @FXML
    private Hyperlink taskSack;
    @FXML
    private Hyperlink pomodoro;
    @FXML
    private AnchorPane content;

    @FXML
    private void onTaskSackClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TaskManagerApplication.class.getResource("fxml/task-sack.fxml"));
        Stage stage = (Stage) taskSack.getScene().getWindow();
        ObservableList<Node> list = content.getChildren();
        list.remove(0);
        list.add(0, fxmlLoader.load());
        stage.show();
    }

    @FXML
    private void onPomodoroClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TaskManagerApplication.class.getResource("fxml/pomodoro.fxml"));
        Stage stage = (Stage) pomodoro.getScene().getWindow();
        ObservableList<Node> list = content.getChildren();
        list.remove(0);
        list.add(0, fxmlLoader.load());
        stage.show();
    }

}
