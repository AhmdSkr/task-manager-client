package com.rakkiz.taskmanagerclient.controller;

import com.rakkiz.taskmanagerclient.TaskManagerApplication;
import com.rakkiz.taskmanagerclient.data.DerbyTaskRepository;
import com.rakkiz.taskmanagerclient.data.model.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
import java.util.Date;

public class TaskCardController {
    private Task model;
    @FXML
    private AnchorPane task;
    @FXML
    private Label title, description;

    public void setTaskModel(Task model) {
        this.model = model;
        this.setDetails();
    }

    public void setDetails() {
        title.setText(this.model.getName());
        description.setText(this.model.getDescription());
        if(model.isScheduled()){
            Label date = (Label) title.getParent().lookup("#schedule");
            date.setText("Scheduled For: "+model.getScheduledTime().atZone(ZoneId.systemDefault()).toLocalDate());
        }
    }

    @FXML
    private void deleteTask() throws SQLException {
        Scene scene = task.getScene();
        GridPane gridPane = (GridPane) scene.lookup("#allTasks");

        int rowIndex = GridPane.getRowIndex(task);
        int columnIndex = GridPane.getColumnIndex(task);

        int index = gridPane.getChildren().indexOf(task);
        gridPane.getChildren().remove(index);

        for (Node child : gridPane.getChildren()) {
            int row = GridPane.getRowIndex(child);
            int column = GridPane.getColumnIndex(child);
            if (row == rowIndex && column > columnIndex) {
                // Shift nodes in the same row to the left
                GridPane.setColumnIndex(child, column - 1);
            } else if (row > rowIndex) {
                // If it is a beginning node, shift node to the top row with the last column, else just shift column to the left
                if (column == 0) {
                    GridPane.setColumnIndex(child, gridPane.getColumnCount() - 1);
                    GridPane.setRowIndex(child, row - 1);
                } else {
                    GridPane.setColumnIndex(child, column - 1);
                }
            }
        }

        DerbyTaskRepository taskRepository = DerbyTaskRepository.getInstance();
        taskRepository.delete(model);
    }

    @FXML
    public void goToDetails() throws IOException {
        System.out.println("going to details");
        // create the popup stage
        Stage popupStage = new Stage();
        popupStage.initStyle(StageStyle.UNDECORATED);
        popupStage.setResizable(false);
        popupStage.initModality(Modality.APPLICATION_MODAL);

        // create the popup scene from the FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(TaskManagerApplication.class.getResource("fxml/task-details.fxml"));
        Parent popupContent = fxmlLoader.load();
        Scene popupScene = new Scene(popupContent);

        // set stage popup stage
        TaskDetailsController taskDetailsController = fxmlLoader.getController();
        taskDetailsController.setStage(popupStage);
        taskDetailsController.setTaskDetails(this.model);

        // put stage position on screen
        popupStage.setX(300);
        popupStage.setY(150);

        // set the scene of the popup stage
        popupStage.setScene(popupScene);

        // blur the background stage
        BoxBlur blur = new BoxBlur(10, 10, 3);
        Parent parentRoot = task.getScene().getRoot();
        parentRoot.setEffect(blur);
        popupStage.setOnHidden(event -> {
            parentRoot.setEffect(null);
            this.setDetails();
        });

        // set the anchorPane from the scaffold
        AnchorPane anchorPane = (AnchorPane) parentRoot.lookup("#content");
        taskDetailsController.setAnchorPane(anchorPane);

        // show the popup
        popupStage.showAndWait();
    }


}
