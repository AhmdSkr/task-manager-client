package com.rakkiz.management.task.client.controller;

import com.rakkiz.management.task.client.data.model.Task;
import javafx.fxml.FXML;
import lombok.Setter;

import java.util.function.Consumer;

/**
 * This class can control any JavaFx UI component that could be used to trigger a task creation.<br/>
 * It does not handle the storage or display of the created task, instead it is responsible for task
 * instantiation and custom initialization.<br/>
 * Any other responsibilities can be handled through a created task {@link Consumer} that can be
 * set through {@link #setOnCreation(Consumer)}
 */
@Setter
public class AddTaskController {
    private Consumer<Task> onCreation;

    @FXML
    public final void addTask() {
        Task task = new Task();
        initialize(task);
        if (onCreation != null) onCreation.accept(task);
    }

    /**
     * This method can be extended in any custom task creation button to modify
     * the task data and structure.
     *
     * @param task to be edited
     */
    public void initialize(Task task) {
        task.setName("Untitled");
        task.setDescription("Break down your task. reflect on you progress or experience");
        task.setDuration(1);
        task.setScheduleTime(null);
    }
}
