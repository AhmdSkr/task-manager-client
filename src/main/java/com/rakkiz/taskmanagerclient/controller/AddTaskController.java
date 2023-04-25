package com.rakkiz.taskmanagerclient.controller;

import com.rakkiz.taskmanagerclient.TaskManagerApplication;
import com.rakkiz.taskmanagerclient.data.DerbyTaskRepository;
import com.rakkiz.taskmanagerclient.data.model.Task;
import com.rakkiz.taskmanagerclient.view.factory.TaskCardFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.SQLException;

public class AddTaskController {

    @FXML
    private AnchorPane anchorRoot;
    DerbyTaskRepository taskrepo = DerbyTaskRepository.getInstance();

    public AddTaskController() throws SQLException {
    }

    @FXML
    public void addTask() throws SQLException, IOException {
        Task task = new Task();
        taskrepo.create(task);

        // add the taskCard to the taskSack
        FXMLLoader loader = new FXMLLoader(TaskManagerApplication.class.getResource("fxml/task-card.fxml"));
        AnchorPane taskAnchor = loader.load();
        TaskCardController controller = loader.getController();
        controller.setTaskModel(task);

        Scene scene = anchorRoot.getScene();
        VBox vbox = (VBox) scene.lookup("#allTasks");
        vbox.getChildren().add(taskAnchor);

        // show the taskDetails of the card
        controller.goToDetails();

        System.out.println("addTask Complete");
    }
}
