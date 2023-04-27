package com.rakkiz.taskmanagerclient.controller;

import com.rakkiz.taskmanagerclient.data.DerbyTaskRepository;
import com.rakkiz.taskmanagerclient.data.TaskRepository;
import com.rakkiz.taskmanagerclient.data.model.Task;
import com.rakkiz.taskmanagerclient.view.factory.Filter.ConcreteFilterViewFactory;
import com.rakkiz.taskmanagerclient.view.factory.TaskCard.ConcreteTaskCardViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class TaskSackController implements Initializable {
    @FXML
    private VBox allTasks;
    @FXML
    private HBox filters;
    private final TaskRepository repository;
    private final ConcreteTaskCardViewFactory factory;
    private final ConcreteFilterViewFactory filterViewFactory;

    public TaskSackController() throws SQLException {
        repository = DerbyTaskRepository.getInstance();
        factory = new ConcreteTaskCardViewFactory();
        filterViewFactory = new ConcreteFilterViewFactory();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Add all the tasks in the database
        List<Task> tasks;
        try {
            tasks = repository.getAllTasks();
            for (Task task : tasks) {
                allTasks.getChildren().add(factory.create(task));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Add the necessary filters
        try {
            filterViewFactory.addFilters(filters);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
