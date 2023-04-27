package com.rakkiz.taskmanagerclient.controller;

import com.rakkiz.taskmanagerclient.TaskManagerApplication;
import com.rakkiz.taskmanagerclient.data.DerbyTaskRepository;
import com.rakkiz.taskmanagerclient.data.model.Task;
import com.rakkiz.taskmanagerclient.view.factory.Filter.ConcreteFilterViewFactory;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TaskDetailsController implements Initializable {
    private Task task;
    private Stage stage;
    @FXML
    private TextField title;
    @FXML
    private TextArea description;
    @FXML
    private HBox filters;

    private final ConcreteFilterViewFactory filterViewFactory;

    public TaskDetailsController() {
        this.task = new Task();
        filterViewFactory = new ConcreteFilterViewFactory();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        title.setTextFormatter(new TextFormatter<>(change -> change.getControlNewText().length() > Task.NAME_LEN_MAX ? null : change));
        description.setTextFormatter(new TextFormatter<>(change -> change.getControlNewText().length() > Task.DESC_LEN_MAX ? null : change));

        // Add the necessary filters
        try {
            filterViewFactory.addFilters(filters);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        stage.setOnCloseRequest(event -> {
            try {
                saveData();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    // show the taskDetails
    public void setTaskDetails(Task task) {
        this.task = task;
        title.setText(task.getName());
        description.setText(task.getDescription());
        System.out.println("setTaskDetails Complete");
    }

    // save changed data to model
    private void saveData() throws SQLException {
        task.setName(title.getText());
        task.setDescription(description.getText());

        DerbyTaskRepository taskrepo = DerbyTaskRepository.getInstance();
        taskrepo.update(this.task);
        System.out.println("saveData Complete");
    }

    // Button for going back
    @FXML
    private void goBack() throws SQLException {
        saveData();
        stage.close();
    }

    // Go to pomodoro
    private AnchorPane content;

    public void setAnchorPane(AnchorPane anchorPane) {
        this.content = anchorPane;
    }

    @FXML
    private void onPomodoroClick() throws IOException, SQLException {
        this.changeToPomodoro();
        stage.close();
    }

    public void changeToPomodoro() throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(TaskManagerApplication.class.getResource("fxml/pomodoro.fxml"));
        Node root = fxmlLoader.load();
        PomodoroController pomodoroController = fxmlLoader.getController();
        saveData();
        pomodoroController.setTitle(task.getName());
        pomodoroController.setDescription(task.getDescription());
        Stage stage = (Stage) content.getScene().getWindow();
        ObservableList<Node> list = content.getChildren();
        list.remove(0);
        list.add(0, root);
        stage.show();
    }
}
