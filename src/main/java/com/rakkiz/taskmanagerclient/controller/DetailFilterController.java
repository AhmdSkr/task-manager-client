package com.rakkiz.taskmanagerclient.controller;

import com.rakkiz.taskmanagerclient.TaskManagerApplication;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Objects;

public class DetailFilterController {
    @FXML
    private HBox root;
    @FXML
    private ImageView image;

    /**
     * Customized the filter according to duration type
     *
     * @param duration initial value of the textField
     */
    public void durationDetail(Integer duration) {
        image.setImage(new Image(Objects.requireNonNull(TaskManagerApplication.class.getResource("images/filters/duration/duration.png")).toString()));

        // Add an integer-only textField
        TextField textField = new TextField();
        textField.setStyle("-fx-background-color: transparent; -fx-text-fill: #C7C7C7; -fx-font-size: 20px;");
        textField.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("\\D", ""));
            }
        }));
        textField.setMaxWidth(100);
        textField.setText(duration + "");
        root.getChildren().add(textField);
        root.setOnMouseClicked(event -> textField.setFocusTraversable(true));

        // style the root hover effects
        root.setOnMouseEntered(event -> {
            image.setImage(new Image(Objects.requireNonNull(TaskManagerApplication.class.getResource("images/filters/duration/duration-hover.png")).toString()));
            root.setStyle("-fx-text-fill: #F6F6F6; -fx-background-color: #457B9D ");
            textField.setStyle("-fx-background-color: transparent; -fx-text-fill: #F6F6F6; -fx-font-size: 20px;");
        });

        root.setOnMouseExited(event -> {
            image.setImage(new Image(Objects.requireNonNull(TaskManagerApplication.class.getResource("images/filters/duration/duration.png")).toString()));
            root.setStyle("-fx-text-fill: #C7C7C7; -fx-background-color: transparent");
            textField.setStyle("-fx-background-color: transparent; -fx-text-fill: #C7C7C7; -fx-font-size: 20px;");
        });
    }

    /**
     * Customized the filter according to type of task
     *
     * @param date initial value of a scheduled task
     */
    public void typeDetail(Instant date) {
        image.setImage(new Image(Objects.requireNonNull(TaskManagerApplication.class.getResource("images/filters/type/type.png")).toString()));

        // add choiceBox to indicate type
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.setStyle("-fx-background-color: transparent; -fx-text-fill: #C7C7C7;");
        choiceBox.getItems().addAll("Scheduled", "Unscheduled");
        root.getChildren().add(choiceBox);

        // add date if the task is scheduled
        if (date == null) {
            choiceBox.setValue("Unscheduled");
        } else {
            choiceBox.setValue("Scheduled");
            dateDetail(date);
        }
        root.setOnMouseClicked(event -> choiceBox.show());

        // style root hover effects
        root.setOnMouseEntered(event -> {
            image.setImage(new Image(Objects.requireNonNull(TaskManagerApplication.class.getResource("images/filters/type/type-hover.png")).toString()));
            root.setStyle("-fx-text-fill: #F6F6F6; -fx-background-color: #E63946 ");
            choiceBox.setStyle("-fx-background-color: transparent; -fx-text-fill: #F6F6F6;");
        });

        root.setOnMouseExited(event -> {
            image.setImage(new Image(Objects.requireNonNull(TaskManagerApplication.class.getResource("images/filters/type/type.png")).toString()));
            root.setStyle("-fx-text-fill: #C7C7C7; -fx-background-color: transparent");
            choiceBox.setStyle("-fx-background-color: transparent; -fx-text-fill: #C7C7C7;");
        });

        // assign actions whenever choiceBox changes value
        choiceBox.setOnAction(event -> {
            if (choiceBox.getValue().equals("Scheduled")) {
                dateDetail(date);
            } else {
                Node node = root.getChildren().get(root.getChildren().size() - 1);
                if (node instanceof DatePicker) root.getChildren().remove(node);
            }
        });
    }

    /**
     * Adds a datepicker whenever a task is set to scheduled
     *
     * @param date initial value of the datepicker
     */
    public void dateDetail(Instant date) {
        DatePicker datePicker = new DatePicker();
        datePicker.setStyle("-fx-background-color: transparent; -fx-text-fill: #C7C7C7;");
        if (date != null) datePicker.setValue(date.atZone(ZoneId.systemDefault()).toLocalDate());
        root.getChildren().add(datePicker);
        root.setOnMouseClicked(event -> datePicker.show());
    }
}
