package com.rakkiz.taskmanagerclient.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TaskDetailsView {

    private int id;
    private Stage stage;

    public void setId(int id) {
        this.id = id;
    }

    public void setStage(Stage stage) {
        System.out.println("SET STAGE IS CALLED");
        this.stage = stage;
        stage.setOnCloseRequest(event -> {
            //TODO : call saveDATA
        });
    }


    // Set title and description
    @FXML
    private TextField title;
    @FXML
    private TextArea description;

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setDescription(String description) {
        this.description.setText(description);
    }

    // Button for going back
    @FXML
    private Button backButton;

    @FXML
    private void goBack() {
        //TODO : CALL saveDATA
        stage.close();
    }

}
