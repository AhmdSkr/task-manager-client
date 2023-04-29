package com.rakkiz.taskmanagerclient.controller;

import com.rakkiz.taskmanagerclient.TaskManagerApplication;
import com.rakkiz.taskmanagerclient.data.DerbyTaskRepository;
import com.rakkiz.taskmanagerclient.data.model.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.io.IOException;
import java.sql.SQLException;

public class PomodoroController {
    private Task task;
    @FXML
    private Label title, description;
    @FXML
    private VBox content;

    private final DerbyTaskRepository repository = DerbyTaskRepository.getInstance();

    public PomodoroController() throws SQLException {
    }

    public void setTask(Task task){this.task = task;}

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setDescription(String description) {
        this.description.setText(description);
    }

    public void addPomTimer(int duration) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TaskManagerApplication.class.getResource("fxml/pomodoroTimer.fxml"));
        HBox root = fxmlLoader.load();
        PomodoroTimerController pomodoroTimerController = fxmlLoader.getController();
        pomodoroTimerController.setOriginalCycle(duration);
        pomodoroTimerController.setPomodoroController(this);
        content.getChildren().add(0, root);
    }

    public void updateTaskDuration(int duration) throws SQLException {
        System.out.println("Old duration: "+task.getDuration()+"  New duration: "+duration);
        task.setDuration(duration);
        repository.update(task);
    }
}
