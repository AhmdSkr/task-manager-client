package com.rakkiz.taskmanagerclient.controller;

import com.rakkiz.taskmanagerclient.data.DerbyTaskRepository;
import com.rakkiz.taskmanagerclient.data.model.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class TaskDetailsController {
    private Task task;
    private Stage stage;
    @FXML
    private TextField title;
    @FXML
    private TextArea description;

    public TaskDetailsController() {
        this.task = new Task();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        stage.setOnCloseRequest(event -> {
            try {
                saveData();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    // show the taskDetails
    public void setTaskDetails(Task task)
    {
        this.task = task;
        title.setText(task.getName());
        description.setText(task.getDescription());
        System.out.println("setTaskDetails Complete");
    }

    // save changed data to model
    private void saveData() throws SQLException {
        task.setName(title.getText());
        task.setDescription(title.getText());

        DerbyTaskRepository taskrepo = DerbyTaskRepository.getInstance();
        taskrepo.update(this.task);
        System.out.println("saveData Complete");
    }

    // Button for going back
    @FXML
    private void goBack() throws SQLException {
        saveData();
        stage.close();
    }
}
