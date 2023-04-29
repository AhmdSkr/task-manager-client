package com.rakkiz.taskmanagerclient.controller;

import com.rakkiz.taskmanagerclient.TaskManagerApplication;
import com.rakkiz.taskmanagerclient.data.model.Task;
import com.rakkiz.taskmanagerclient.view.strategy.DateTaskFilter;
import com.rakkiz.taskmanagerclient.view.strategy.duration.DurationTaskFilter;
import com.rakkiz.taskmanagerclient.view.strategy.TaskFilter;
import com.rakkiz.taskmanagerclient.view.strategy.type.TypeTaskFilter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class FilterController implements Initializable {
    private TaskFilter taskFilter;

    @FXML
    private ChoiceBox<String> choiceBox;
    private HBox root;

    public void setTaskFilter(TaskFilter taskFilter) {
        this.taskFilter = taskFilter;
    }

    // Add the date choices to the choiceBox if the filter is for a date
    public void addDateChoiceBox() {
        choiceBox.getItems().addAll("Date", "Past", "Yesterday", "Today", "Tomorrow", "Future");
        choiceBox.setValue("Date");
        root.setOnMouseClicked(event -> choiceBox.show());

        choiceBox.setOnAction(event -> {
            // TODO: String selectedValue = choiceBox.getValue();
            styleRoot();
        });
    }

    // Add the duration choices to the choiceBox if the filter is for a duration
    public void addDurationChoiceBox() {
        choiceBox.getItems().addAll("Duration", "Short", "Medium", "Long");
        choiceBox.setValue("Duration");
        root.setOnMouseClicked(event -> choiceBox.show());

        choiceBox.setOnAction(event -> {
            // TODO: String selectedValue = choiceBox.getValue();
            styleRoot();
        });
    }

    // Add the type choices to the choiceBox if the filter is for a type
    public void addTypeChoiceBox() {
        choiceBox.getItems().addAll("Type", "Scheduled", "Unscheduled");
        choiceBox.setValue("Type");
        root.setOnMouseClicked(event -> choiceBox.show());

        choiceBox.setOnAction(event -> {
            // TODO: String selectedValue = choiceBox.getValue();
            styleRoot();
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        root = (HBox) choiceBox.getParent();
    }

    @FXML
    private ImageView image;

    public void setImage(Image imageValue) {
        image.setImage(imageValue);
    }

    // Styling for a normal filter depending on filter
    @FXML
    public void setNormal() {
        choiceBox.setStyle("-fx-text-fill: #C7C7C7; -fx-background-color: transparent;");
        styleRoot();
        if (taskFilter instanceof DateTaskFilter) {
            image.setImage(new Image(Objects.requireNonNull(TaskManagerApplication.class.getResource("images/filters/date/calendar.png")).toString()));
        } else if (taskFilter instanceof DurationTaskFilter) {
            image.setImage(new Image(Objects.requireNonNull(TaskManagerApplication.class.getResource("images/filters/duration/duration.png")).toString()));
        } else if (taskFilter instanceof TypeTaskFilter) {
            image.setImage(new Image(Objects.requireNonNull(TaskManagerApplication.class.getResource("images/filters/type/type.png")).toString()));
        }
    }

    // Styling of hovered filter depending on the filter
    @FXML
    public void toHover() {
        choiceBox.setStyle("-fx-text-fill: #F6F6F6; -fx-background-color: transparent");
        if (taskFilter instanceof DateTaskFilter) {
            image.setImage(new Image(Objects.requireNonNull(TaskManagerApplication.class.getResource("images/filters/date/calendar-hover.png")).toString()));
            root.setStyle("-fx-background-color: #457B9D");
        } else if (taskFilter instanceof DurationTaskFilter) {
            image.setImage(new Image(Objects.requireNonNull(TaskManagerApplication.class.getResource("images/filters/duration/duration-hover.png")).toString()));
            root.setStyle("-fx-background-color: #457B9D");
        } else if (taskFilter instanceof TypeTaskFilter) {
            image.setImage(new Image(Objects.requireNonNull(TaskManagerApplication.class.getResource("images/filters/type/type-hover.png")).toString()));
            root.setStyle("-fx-background-color: #E63946");
        }
    }

    private void styleRoot() {
        if (taskFilter instanceof DateTaskFilter && !choiceBox.getValue().equals("Date"))
            root.setStyle("-fx-background-color: #457B9D");
        else if (taskFilter instanceof DurationTaskFilter && !choiceBox.getValue().equals("Duration"))
            root.setStyle("-fx-background-color: #457B9D");
        else if (taskFilter instanceof TypeTaskFilter && !choiceBox.getValue().equals("Type"))
            root.setStyle("-fx-background-color: #E63946");
        else root.setStyle("-fx-background-color: transparent");
    }

    // Filter the tasks using the assigned taskFilter
    public void filterTasks(List<Task> tasks) {
        // TODO
        List<Task> filtered = new ArrayList<Task>();
        for (Task task : tasks) {
            if (taskFilter.filter(task)) filtered.add(task);
        }
    }
}
