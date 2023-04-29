package com.rakkiz.taskmanagerclient.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.time.Instant;
import java.time.ZoneId;

public class DetailFilterController {
    @FXML
    private HBox root;

    public void durationDetail(Integer duration) {
        TextField textField = new TextField();
        textField.setStyle("-fx-background-color: transparent; -fx-text-fill: #C7C7C7; -fx-font-size: 20px;");
        textField.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        }));

        textField.setMaxWidth(100);
        textField.setText(duration + "");

        root.getChildren().add(textField);
        root.setOnMouseClicked(event -> textField.setFocusTraversable(true));
    }

    public void typeDetail(Instant date) {
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.setStyle("-fx-background-color: transparent; -fx-text-fill: #C7C7C7;");
        choiceBox.getItems().addAll("Scheduled", "Unscheduled");

        root.getChildren().add(choiceBox);
        if (date == null) {
            choiceBox.setValue("Unscheduled");
        } else {
            choiceBox.setValue("Scheduled");
            dateDetail(date);
        }
        root.setOnMouseClicked(event -> choiceBox.show());

        choiceBox.setOnAction(event -> {
            if (choiceBox.getValue().equals("Scheduled")) {
                dateDetail(date);
            } else {
                Node node = root.getChildren().get(root.getChildren().size() - 1);
                if (node instanceof DatePicker) root.getChildren().remove(node);
            }
        });
    }

    public void dateDetail(Instant date) {
        DatePicker datePicker = new DatePicker();
        datePicker.setStyle("-fx-background-color: transparent; -fx-text-fill: #C7C7C7;");
        if (date != null) datePicker.setValue(date.atZone(ZoneId.systemDefault()).toLocalDate());
        root.getChildren().add(datePicker);
        root.setOnMouseClicked(event -> datePicker.show());
    }
}
