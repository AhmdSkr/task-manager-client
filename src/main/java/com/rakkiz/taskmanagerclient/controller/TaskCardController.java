package com.rakkiz.taskmanagerclient.controller;

import com.rakkiz.taskmanagerclient.TaskManagerApplication;
import com.rakkiz.taskmanagerclient.data.DerbyTaskRepository;
import com.rakkiz.taskmanagerclient.data.model.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.SQLException;
import java.time.ZoneId;

public class TaskCardController {
    @FXML
    private AnchorPane task;
    @FXML
    private Label title, description;
    private Task model;
    private TaskSackController taskSackController;

    public void setTaskSackController(TaskSackController taskSackController) {
        this.taskSackController = taskSackController;
    }

    public void setTaskModel(Task model) {
        this.model = model;
        this.setDetails();
    }

    /**
     * Set the information in the card according to the model
     */
    public void setDetails() {
        title.setText(this.model.getName());
        description.setText(this.model.getDescription());
        if (model.isScheduled() && model.getDuration() > 0) {
            Label date = (Label) title.getParent().lookup("#schedule");
            date.setText("Scheduled For: " + model.getScheduledTime().atZone(ZoneId.systemDefault()).toLocalDate());
        }
    }

    /**
     * Delete task
     *
     * @throws SQLException when accessing database
     */
    @FXML
    private void deleteTask() throws SQLException {
        Scene scene = task.getScene();
        GridPane gridPane = (GridPane) scene.lookup("#allTasks");

        // delete task from taskSack
        gridPane.getChildren().remove(task);

        // delete task from database
        DerbyTaskRepository taskRepository = DerbyTaskRepository.getInstance();
        taskRepository.delete(model);

        // refresh tasks
        try {
            taskSackController.addTasks();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Shows the taskDetails stage
     *
     * @throws IOException when loading the taskDetails fxml
     */
    @FXML
    public void goToDetails() throws IOException {

        // create the popup stage
        Stage popupStage = new Stage();
        popupStage.initStyle(StageStyle.UNDECORATED);
        popupStage.setResizable(false);
        popupStage.initModality(Modality.APPLICATION_MODAL);

        // create the popup scene from task-details.fxml
        FXMLLoader fxmlLoader = new FXMLLoader(TaskManagerApplication.class.getResource("fxml/task-details.fxml"));
        Parent popupContent = fxmlLoader.load();
        Scene popupScene = new Scene(popupContent);
        popupStage.setScene(popupScene);

        // set popup stage
        TaskDetailsController taskDetailsController = fxmlLoader.getController();
        taskDetailsController.setStage(popupStage);
        taskDetailsController.setTaskDetails(this.model);
        taskDetailsController.addDetails();

        // put stage position on screen
        popupStage.setX(300);
        popupStage.setY(150);

        // blur the background stage
        BoxBlur blur = new BoxBlur(10, 10, 3);
        Parent parentRoot = task.getScene().getRoot();
        parentRoot.setEffect(blur);

        // add functionality when popup closes
        popupStage.setOnHidden(event -> {
            parentRoot.setEffect(null);
            try {
                taskSackController.addTasks();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        // set the anchorPane from the scaffold
        AnchorPane anchorPane = (AnchorPane) parentRoot.lookup("#content");
        taskDetailsController.setAnchorPane(anchorPane);

        // show the popup
        popupStage.show();
    }
}
