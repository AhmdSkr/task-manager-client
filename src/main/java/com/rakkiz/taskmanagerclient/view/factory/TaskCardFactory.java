package com.rakkiz.taskmanagerclient.view.factory;

import com.rakkiz.taskmanagerclient.TaskManagerApplication;
import com.rakkiz.taskmanagerclient.data.model.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

public class TaskCardFactory {
    private FXMLLoader loader;
    public TaskCardFactory(){
        loader = new FXMLLoader();
        loader.setLocation(TaskManagerApplication.class.getResource("fxml/task-card.fxml"));
    }

    public AnchorPane create(Task task) {
        AnchorPane taskCard = null;
        try {
            taskCard = loader.load();
//            TaskView taskCardController = loader.getController();
//            taskCardController.setTitle(task.getName());
//            taskCardController.setDescription(task.getDescription());
            taskCard.lookup("#title");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return taskCard;
    }

}
