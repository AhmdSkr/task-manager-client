package com.rakkiz.taskmanagerclient.view;

import com.rakkiz.taskmanagerclient.TaskManagerApplication;
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

public class TaskView {
    private int id;
    @FXML
    private Label title, description;

    public void setTitle(String realTitle) {
        title.setText(realTitle);
    }

    public void setDescription(String realDescription) {
        description.setText(realDescription);
    }

    @FXML
    private AnchorPane task;
    @FXML
    private void goToDetails() throws IOException {
        // create the popup stage
        Stage popupStage = new Stage();
        popupStage.initStyle(StageStyle.UNDECORATED);
        popupStage.setResizable(false);
        popupStage.initModality(Modality.APPLICATION_MODAL);

        // create the popup scene from the FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(TaskManagerApplication.class.getResource("fxml/task-details.fxml"));
        Parent popupContent = (Parent) fxmlLoader.load();
        Scene popupScene = new Scene(popupContent);

        // set stage and id of popup stage
        TaskDetailsView controller = fxmlLoader.getController();
        controller.setStage(popupStage);
        controller.setId(this.id);

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
        });
        // show the popup
        popupStage.showAndWait();
    }
}
