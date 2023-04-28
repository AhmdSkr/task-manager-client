package com.rakkiz.taskmanagerclient.controller;

import com.rakkiz.taskmanagerclient.TaskManagerApplication;
import com.rakkiz.taskmanagerclient.data.DerbyTaskRepository;
import com.rakkiz.taskmanagerclient.data.model.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.sql.SQLException;

public class AddTaskController {

    @FXML
    private AnchorPane anchorRoot;
    DerbyTaskRepository taskrepo = DerbyTaskRepository.getInstance();

    public AddTaskController() throws SQLException {
    }

    @FXML
    public void addTask() throws SQLException, IOException {
        Task task = new Task();
        taskrepo.create(task);

        // add the taskCard to the taskSack
        FXMLLoader loader = new FXMLLoader(TaskManagerApplication.class.getResource("fxml/task-card.fxml"));
        AnchorPane taskAnchor = loader.load();
        TaskCardController controller = loader.getController();
        controller.setTaskModel(task);

        Scene scene = anchorRoot.getScene();
        GridPane gridPane = (GridPane) scene.lookup("#allTasks");
        int vboxSize = gridPane.getChildren().size();
        if (vboxSize == 0) {
            gridPane.add(taskAnchor, 0, 0);
        } else {
            Node lastNode = gridPane.getChildren().get(gridPane.getChildren().size() - 1);
            int colIndex = gridPane.getColumnIndex(lastNode);
            int rowIndex = gridPane.getRowIndex(lastNode);
            int colCount = gridPane.getColumnCount();
            colIndex++;
            if (colIndex == colCount) {
                rowIndex++;
                colIndex = 0;
            }
            gridPane.add(taskAnchor, colIndex, rowIndex);
        }
        // show the taskDetails of the card
        controller.goToDetails();

        System.out.println("addTask Complete");
    }
}
