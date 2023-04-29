package com.rakkiz.taskmanagerclient.controller;

import com.rakkiz.taskmanagerclient.TaskManagerApplication;
import com.rakkiz.taskmanagerclient.data.DerbyTaskRepository;
import com.rakkiz.taskmanagerclient.data.TaskRepository;
import com.rakkiz.taskmanagerclient.data.model.Task;
import com.rakkiz.taskmanagerclient.view.factory.Filter.ConcreteFilterViewFactory;
import com.rakkiz.taskmanagerclient.view.factory.TaskCard.ConcreteTaskCardViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class TaskSackController implements Initializable {
    @FXML
    private GridPane allTasks;
    @FXML
    private HBox menus,filters;

    @FXML
    private AnchorPane addTaskButton;


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
        addTasks();
        // Add the necessary filters
        try {
            filterViewFactory.addFilters(filters);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            addAddTaskButton();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void addAddTaskButton() throws IOException {
        FXMLLoader addTaskLoader = new FXMLLoader(TaskManagerApplication.class.getResource("fxml/addTask.fxml"));
        AnchorPane root = addTaskLoader.load();
        AddTaskController addTaskController = addTaskLoader.getController();
        addTaskController.setTaskSackController(this);
        menus.getChildren().add(root);
    }

    public void addTasks() {
        // Add all the tasks in the database
        allTasks.getChildren().clear();
        List<Task> tasks;
        int cols = allTasks.getColumnCount();
        int i = 0, j = 0;
        try {
            tasks = repository.getAllTasks();
            for (Task task : tasks) {
                allTasks.add(factory.create(task,this), j, i);
                j++;
                if (j == cols) {
                    j = 0;
                    i++;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
