package com.rakkiz.management.task.client.controller;

import com.rakkiz.management.task.client.TaskManagerApplication;
import com.rakkiz.management.task.client.data.DerbyTaskRepository;
import com.rakkiz.management.task.client.data.model.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.SQLException;

public class PomodoroController {
    @FXML
    private Label title, description;
    @FXML
    private VBox content;
    private Task task;
    private final DerbyTaskRepository repository = DerbyTaskRepository.getInstance();

    public PomodoroController() throws SQLException {
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setDescription(String description) {
        this.description.setText(description);
    }

    /**
     * Add the timer to the pomodoro fxml
     *
     * @param duration initial number of cycles
     * @throws IOException when loading the pomodoroTimer fxml
     */
    public void addPomTimer(int duration) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TaskManagerApplication.class.getResource("fxml/pomodoroTimer.fxml"));
        HBox root = fxmlLoader.load();
        PomodoroTimerController pomodoroTimerController = fxmlLoader.getController();
        pomodoroTimerController.setOriginalCycle(duration);
        pomodoroTimerController.setPomodoroController(this);
        content.getChildren().add(1, root);
    }

    /**
     * Updates the duration of the task in the database
     *
     * @param duration new duration
     * @throws SQLException when updating database
     */
    public void updateTaskDuration(int duration) throws SQLException {
        task.setDuration(duration);
        repository.update(task);
    }
}
