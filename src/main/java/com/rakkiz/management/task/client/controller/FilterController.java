package com.rakkiz.management.task.client.controller;

import com.rakkiz.management.task.client.view.strategy.date.*;
import com.rakkiz.management.task.client.TaskManagerApplication;
import com.rakkiz.management.task.client.view.strategy.TaskFilter;
import com.rakkiz.management.task.client.view.strategy.duration.DurationTaskFilter;
import com.rakkiz.management.task.client.view.strategy.duration.LongDurationTaskFilter;
import com.rakkiz.management.task.client.view.strategy.duration.MediumDurationTaskFilter;
import com.rakkiz.management.task.client.view.strategy.duration.ShortDurationTaskFilter;
import com.rakkiz.management.task.client.view.strategy.type.ScheduledTypeTaskFilter;
import com.rakkiz.management.task.client.view.strategy.type.TypeTaskFilter;
import com.rakkiz.management.task.client.view.strategy.type.UnscheduledTypeTaskFilter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class FilterController implements Initializable {
    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private ImageView image;
    private TaskFilter taskFilter;
    private TaskSackController taskSackController;
    private HBox root;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        root = (HBox) choiceBox.getParent();

        choiceBox.setOnMouseEntered(event -> choiceBox.setStyle("-fx-text-fill: #F6F6F6; -fx-background-color: transparent"));
        choiceBox.setOnMouseExited(event -> choiceBox.setStyle("-fx-text-fill: #C7C7C7; -fx-background-color: transparent;"));
    }

    public void setTaskSackController(TaskSackController taskSackController) {
        this.taskSackController = taskSackController;
    }

    public void setImage(Image imageValue) {
        image.setImage(imageValue);
    }

    public void setTaskFilter(TaskFilter taskFilter) {
        this.taskFilter = taskFilter;
    }

    public TaskFilter getTaskFilter() {
        return taskFilter;
    }

    /**
     * Duration choiceBox
     */
    public void addDurationChoiceBox() {
        choiceBox.getItems().addAll("Duration", "Short", "Medium", "Long");
        choiceBox.setValue("Duration");
        root.setOnMouseClicked(event -> choiceBox.show());

        choiceBox.setOnAction(event -> {
            String value = choiceBox.getValue();
            switch (value) {
                case "Duration" -> setTaskFilter(new DurationTaskFilter());
                case "Short" -> setTaskFilter(new ShortDurationTaskFilter());
                case "Medium" -> setTaskFilter(new MediumDurationTaskFilter());
                case "Long" -> setTaskFilter(new LongDurationTaskFilter());
            }

            try {
                taskSackController.addTasks();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            styleRoot();
        });
    }

    /**
     * Type choiceBox
     */
    public void addTypeChoiceBox() {
        choiceBox.getItems().addAll("Type", "Scheduled", "Unscheduled");
        choiceBox.setValue("Type");
        root.setOnMouseClicked(event -> choiceBox.show());

        choiceBox.setOnAction(event -> {
            String value = choiceBox.getValue();
            switch (value) {
                case "Type" -> setTaskFilter(new TypeTaskFilter());
                case "Scheduled" -> setTaskFilter(new ScheduledTypeTaskFilter());
                case "Unscheduled" -> setTaskFilter(new UnscheduledTypeTaskFilter());
            }

            try {
                taskSackController.addTasks();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            styleRoot();
        });
    }

    /**
     * Date choiceBox
     */
    public void addDateChoiceBox() {
        choiceBox.getItems().addAll("Date", "Past", "Yesterday", "Today", "Tomorrow", "Future");
        choiceBox.setValue("Date");
        root.setOnMouseClicked(event -> choiceBox.show());

        choiceBox.setOnAction(event -> {
            String value = choiceBox.getValue();
            switch (value) {
                case "Date" -> setTaskFilter(new DateTaskFilter());
                case "Past" -> setTaskFilter(new PastDateTaskFilter());
                case "Yesterday" -> setTaskFilter(new YesterdayDateTaskFilter());
                case "Today" -> setTaskFilter(new TodayDateTaskFilter());
                case "Tomorrow" -> setTaskFilter(new TomorrowDateTaskFilter());
                case "Future" -> setTaskFilter(new FutureDateTaskFilter());
            }

            try {
                taskSackController.addTasks();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            styleRoot();
        });
    }

    /**
     * Default style
     */
    @FXML
    public void setNormal() {
        styleRoot();
        if (taskFilter instanceof DateTaskFilter) {
            image.setImage(new Image(Objects.requireNonNull(TaskManagerApplication.class.getResource("images/filters/date/calendar.png")).toString()));
        } else if (taskFilter instanceof DurationTaskFilter) {
            image.setImage(new Image(Objects.requireNonNull(TaskManagerApplication.class.getResource("images/filters/duration/duration.png")).toString()));
        } else if (taskFilter instanceof TypeTaskFilter) {
            image.setImage(new Image(Objects.requireNonNull(TaskManagerApplication.class.getResource("images/filters/type/type.png")).toString()));
        }
    }

    /**
     * Hover style
     */
    @FXML
    public void toHover() {
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

    /**
     * Root style depending on values
     */
    private void styleRoot() {
        if (taskFilter instanceof DateTaskFilter && !choiceBox.getValue().equals("Date"))
            root.setStyle("-fx-background-color: #457B9D");
        else if (taskFilter instanceof DurationTaskFilter && !choiceBox.getValue().equals("Duration"))
            root.setStyle("-fx-background-color: #457B9D");
        else if (taskFilter instanceof TypeTaskFilter && !choiceBox.getValue().equals("Type"))
            root.setStyle("-fx-background-color: #E63946");
        else root.setStyle("-fx-background-color: transparent");
    }
}
