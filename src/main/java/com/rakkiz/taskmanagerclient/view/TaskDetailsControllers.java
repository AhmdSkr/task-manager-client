package com.rakkiz.taskmanagerclient.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class TaskDetailsControllers {

    @FXML
    private Button backButton;

    @FXML
    private void goBack() {
        Stage popUp =  (Stage) backButton.getScene().getWindow();
        popUp.close();
    }
}
