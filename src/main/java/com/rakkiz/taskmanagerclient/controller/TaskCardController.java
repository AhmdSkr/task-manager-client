package com.rakkiz.taskmanagerclient.controller;

import com.rakkiz.taskmanagerclient.TaskManagerApplication;
import com.rakkiz.taskmanagerclient.data.model.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

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

        // set stage and id of popup stage
        TaskDetailsController controller = fxmlLoader.getController();
        controller.setStage(popupStage);

        // put stage position on screen
        popupStage.setX(300);
        popupStage.setY(150);

        // set the scene of the popup stage
        popupStage.setScene(popupScene);

        // blur the background stage
        BoxBlur blur = new BoxBlur(10, 10, 3);
        Parent parentRoot = task.getScene().getRoot();
        parentRoot.setEffect(blur);
        popupStage.setOnHidden(event -> parentRoot.setEffect(null));

        // show the popup
        popupStage.showAndWait();
    }
}
