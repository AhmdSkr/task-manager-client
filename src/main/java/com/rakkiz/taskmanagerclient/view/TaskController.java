package com.rakkiz.taskmanagerclient.view;

import com.rakkiz.taskmanagerclient.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class TaskController {

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
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("fxml/task-details.fxml"));
        Parent popupContent = (Parent) fxmlLoader.load();
        Scene popupScene = new Scene(popupContent);


        // set the scene of the popup stage
        popupStage.setScene(popupScene);

        // show the popup
        popupStage.showAndWait();
    }
}
