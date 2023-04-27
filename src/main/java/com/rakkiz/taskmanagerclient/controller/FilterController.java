package com.rakkiz.taskmanagerclient.controller;

import com.rakkiz.taskmanagerclient.TaskManagerApplication;
import com.rakkiz.taskmanagerclient.data.model.Task;
import com.rakkiz.taskmanagerclient.view.strategy.DateTaskFilter;
import com.rakkiz.taskmanagerclient.view.strategy.DurationTaskFilter;
import com.rakkiz.taskmanagerclient.view.strategy.TaskFilter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FilterController implements Initializable {
    private TaskFilter taskFilter;
    private HBox root;

    public void setTaskFilter(TaskFilter taskFilter) {
        this.taskFilter = taskFilter;
    }

    public void addDatePicker() {
        // Create a new DatePicker
        DatePicker datePicker = new DatePicker();

        // Set the position of the DatePicker to the mouse click location
        datePicker.setMaxWidth(0);
        datePicker.setPrefWidth(0);
        datePicker.setEditable(false);

        // Add the DatePicker to the HBox
        datePicker.setVisible(false);
        root.getChildren().add(datePicker);

        datePicker.setOnAction(event2 -> {
            // When the user picks a date, update the label and hide the DatePicker
            label.setText(datePicker.getValue().toString());
            datePicker.setVisible(false);
        });

        root.setOnMouseClicked(event -> {
            // Set focus to the DatePicker so it's ready to receive input
            datePicker.show();
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        root = (HBox) arrow.getParent().getParent();
    }

    @FXML
    private ImageView image, arrow;
    @FXML
    private Label label;

    public void setLabel(String text) {
        this.label.setText(text);
    }

    public void setImage(Image imageValue) {
        image.setImage(imageValue);
    }

    @FXML
    public void setNormal() {
        arrow.setImage(new Image(TaskManagerApplication.class.getResource("images/filters/arrow/arrow.png").toString()));
        if (taskFilter instanceof DateTaskFilter) {
            image.setImage(new Image(TaskManagerApplication.class.getResource("images/filters/date/calendar.png").toString()));
            root.setStyle("-fx-background-color: transparent");
        } else if (taskFilter instanceof DurationTaskFilter) {
            image.setImage(new Image(TaskManagerApplication.class.getResource("images/filters/duration/duration.png").toString()));
            root.setStyle("-fx-background-color: transparent");
        }
    }

    @FXML
    public void toHover() {
        arrow.setImage(new Image(TaskManagerApplication.class.getResource("images/filters/arrow/arrow-hover.png").toString()));
        if (taskFilter instanceof DateTaskFilter) {
            image.setImage(new Image(TaskManagerApplication.class.getResource("images/filters/date/calendar-hover.png").toString()));
            root.setStyle("-fx-background-color: #457B9D");
        } else if (taskFilter instanceof DurationTaskFilter) {
            image.setImage(new Image(TaskManagerApplication.class.getResource("images/filters/duration/duration-hover.png").toString()));
            root.setStyle("-fx-background-color: #E63946");
        }
    }

    public void filterTasks(List<Task> tasks) {
        List<Task> filteredTasks = taskFilter.filter(tasks);

    }


}
