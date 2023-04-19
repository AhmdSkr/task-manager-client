package com.rakkiz.taskmanagerclient.view;

import com.rakkiz.taskmanagerclient.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("fxml/task-sack.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) taskSack.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void onPomodoroClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("fxml/pomodoro.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) pomodoro.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}
