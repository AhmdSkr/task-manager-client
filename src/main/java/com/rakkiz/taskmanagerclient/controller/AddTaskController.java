package com.rakkiz.taskmanagerclient.controller;

import com.rakkiz.taskmanagerclient.TaskManagerApplication;
import com.rakkiz.taskmanagerclient.data.DerbyTaskRepository;
import com.rakkiz.taskmanagerclient.data.model.Task;
import com.rakkiz.taskmanagerclient.view.factory.TaskCardFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.SQLException;

public class AddTaskController {

    DerbyTaskRepository taskrepo = DerbyTaskRepository.getInstance();

    public AddTaskController() throws SQLException {
    }

    @FXML
    public void addTask() throws SQLException, IOException {
        Task task = new Task();
        taskrepo.create(task);


        // TODO: show the taskDetails of the card


        // TODO: add the taskCard to the taskSack

        System.out.println("addTask Complete");
    }
}
