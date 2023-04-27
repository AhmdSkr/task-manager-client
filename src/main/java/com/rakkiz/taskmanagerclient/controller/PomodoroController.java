package com.rakkiz.taskmanagerclient.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PomodoroController {
    @FXML
    private Label title, description;

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setDescription(String description) {
        this.description.setText(description);
    }
}
