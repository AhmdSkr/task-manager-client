package com.rakkiz.taskmanagerclient.controller;

import com.rakkiz.taskmanagerclient.data.model.Task;
import javafx.fxml.FXML;

public class AddTaskController {

    @FXML
    public void addTask(){
        Task task = new Task();
        TaskDetailsController taskDetailsController = new TaskDetailsController(task);
        taskDetailsController.setDetails();
    }
}
