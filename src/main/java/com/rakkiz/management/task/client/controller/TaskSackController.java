package com.rakkiz.management.task.client.controller;

import com.rakkiz.management.task.client.data.DerbyTaskRepository;
import com.rakkiz.management.task.client.data.TaskRepository;
import com.rakkiz.management.task.client.data.model.Task;
import com.rakkiz.management.task.client.view.factory.task.card.ConcreteTaskCardViewFactory;
import com.rakkiz.management.task.client.view.factory.task.filter.ConcreteFilterViewFactory;
import com.rakkiz.management.task.client.view.strategy.date.DateTaskFilter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import lombok.SneakyThrows;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TaskSackController implements Initializable {
    @FXML
    private GridPane allTasks;
    @FXML
    private HBox filters;
    @FXML
    private AddTaskController addTaskController;
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
        addTaskController.setOnCreation(this::onTaskAddition);
        try {
            filterControllers = filterViewFactory.addFilters(filters, this);

            addTasks();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    private void onTaskAddition(Task toAdd) {
        repository.create(toAdd);
        String path = "/com/rakkiz/management/task/client/fxml/task-card.fxml";
        URL fxml = getClass().getResource(path);
        FXMLLoader loader = new FXMLLoader(fxml);
        Node taskCard = loader.load();
        TaskCardController controller = loader.getController();
        controller.setTaskModel(toAdd);
        controller.setTaskSackController(this);
        allTasks.add(taskCard, 0, 0);
        controller.goToDetails();
    }

    /**
     * Adds the tasks depending on the filters
     *
     * @throws Exception when adding to grid
     */
    // TODO: safely cast to ChoiceBox
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