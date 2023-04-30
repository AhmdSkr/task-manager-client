package com.rakkiz.taskmanagerclient.controller;

import com.rakkiz.taskmanagerclient.TaskManagerApplication;
import com.rakkiz.taskmanagerclient.data.DerbyTaskRepository;
import com.rakkiz.taskmanagerclient.data.model.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.sql.SQLException;

public class AddTaskController {

    @FXML
    private AnchorPane anchorRoot;
    private final DerbyTaskRepository taskRepository;
    TaskSackController taskSackController;

    public AddTaskController() throws SQLException {
        taskRepository = DerbyTaskRepository.getInstance();
    }

    public void setTaskSackController(TaskSackController taskSackController) {
        this.taskSackController = taskSackController;
    }

    /**
     * Creates a new task
     *
     * @throws SQLException when creating the task in database
     * @throws IOException  when loading the fxml or showing the taskDetails
     */
    @FXML
    public void addTask() throws SQLException, IOException {

        // create a new task in database
        Task task = new Task();
        taskRepository.create(task);

        // load taskCard fxml
        FXMLLoader loader = new FXMLLoader(TaskManagerApplication.class.getResource("fxml/task-card.fxml"));
        AnchorPane taskAnchor = loader.load();
        TaskCardController controller = loader.getController();
        controller.setTaskModel(task);
        controller.setTaskSackController(taskSackController);

        // add taskCard to task Sack
        Scene scene = anchorRoot.getScene();
        GridPane gridPane = (GridPane) scene.lookup("#allTasks");
        gridPane.add(taskAnchor, 0, 0);

        // show the taskDetails of the card
        controller.goToDetails();
    }
}
