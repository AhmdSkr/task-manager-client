package com.rakkiz.taskmanagerclient.controller;

import com.rakkiz.taskmanagerclient.data.DerbyTaskRepository;
import com.rakkiz.taskmanagerclient.data.TaskRepository;
import com.rakkiz.taskmanagerclient.data.model.Task;
import com.rakkiz.taskmanagerclient.view.factory.ConcreteTaskCardViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.SQLException;

import java.util.List;
import java.util.ResourceBundle;

public class TaskSackController implements Initializable {
    @FXML
    private VBox allTasks;
    private final TaskRepository repository;
    private final ConcreteTaskCardViewFactory factory;

    public TaskSackController() throws SQLException{
        repository = DerbyTaskRepository.getInstance();
        factory = new ConcreteTaskCardViewFactory();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources){
        List<Task> tasks;
        try {
            tasks = repository.getAllTasks();
            for(Task task:tasks){
                allTasks.getChildren().add(factory.create(task));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
