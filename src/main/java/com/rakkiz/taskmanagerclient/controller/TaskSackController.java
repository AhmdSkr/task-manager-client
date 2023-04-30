package com.rakkiz.taskmanagerclient.controller;

import com.rakkiz.taskmanagerclient.TaskManagerApplication;
import com.rakkiz.taskmanagerclient.data.DerbyTaskRepository;
import com.rakkiz.taskmanagerclient.data.TaskRepository;
import com.rakkiz.taskmanagerclient.data.model.Task;
import com.rakkiz.taskmanagerclient.view.factory.Filter.ConcreteFilterViewFactory;
import com.rakkiz.taskmanagerclient.view.factory.TaskCard.ConcreteTaskCardViewFactory;
import com.rakkiz.taskmanagerclient.view.strategy.date.DateTaskFilter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TaskSackController implements Initializable {
    @FXML
    private GridPane allTasks;
    @FXML
    private HBox menus, filters;
    private ArrayList<FilterController> filterControllers;
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
        // add tasks, filters, and addTask button
        try {
            filterControllers = filterViewFactory.addFilters(filters, this);
            addAddTaskButton();
            addTasks();
        } catch (Exception e) {
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

    /**
     * Adds the tasks depending on the filters
     *
     * @throws Exception when adding to grid
     */
    public void addTasks() throws Exception {

        // style type and date filters according to their values
        Node filterTypeRoot = filters.lookup("#TypeFilterRoot");
        ChoiceBox<String> typeChoiceBox = (ChoiceBox<String>) filterTypeRoot.lookup("#choiceBox");
        Node filterDateRoot = filters.lookup("#DateFilterRoot");
        ChoiceBox<String> dateChoiceBox = (ChoiceBox<String>) filterDateRoot.lookup("#choiceBox");
        if (typeChoiceBox.getValue().equals("Scheduled")) filterDateRoot.setDisable(false);
        else {
            filterControllers.get(filterControllers.size() - 1).setTaskFilter(new DateTaskFilter());
            dateChoiceBox.setValue("Date");
            filterControllers.get(filterControllers.size() - 1).setNormal();
            filterDateRoot.setDisable(true);
        }

        // filter all tasks from database
        List<Task> tasks = repository.getAllTasks();
        List<Task> filtered = new ArrayList<>();
        for (Task task : tasks) {
            boolean filterFlag = true;
            for (FilterController filterController : filterControllers) {
                if (!filterController.getTaskFilter().filter(task)) {
                    filterFlag = false;
                    break;
                }
            }
            if (filterFlag) filtered.add(task);
        }

        addToGrid(filtered);
    }

    public void addToGrid(List<Task> tasks) throws Exception {
        if (tasks == null) tasks = repository.getAllTasks();

        allTasks.getChildren().clear();
        int cols = allTasks.getColumnCount();
        int i = 0, j = 0;
        try {
            for (Task task : tasks) {
                allTasks.add(factory.create(task, this), j, i);
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