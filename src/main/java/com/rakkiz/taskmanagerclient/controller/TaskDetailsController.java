package com.rakkiz.taskmanagerclient.controller;

import com.rakkiz.taskmanagerclient.data.model.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TaskDetailsController {
    private Task model;
    private Stage stage;
    @FXML
    private TextField title;
    @FXML
    private TextArea description;

    public TaskDetailsController(){this.model = new Task();}
    public TaskDetailsController(Task model)
    {
        this.model = model;
        setDetails();
    }
    public void setStage(Stage stage) {
        this.stage = stage;
        stage.setOnCloseRequest(event -> {
            saveData();
        });
    }

    // Set title and description
    public void setDetails(){
        title.setText(model.getName());
        description.setText(model.getDescription());
    }

    // save changed data to model
    private void saveData(){
        System.out.println("SAVED DATA");
        model.setName(title.getText());
        model.setDescription(title.getText());
    }

    // Button for going back
    @FXML
    private void goBack() {
        saveData();
        stage.close();
    }
}
