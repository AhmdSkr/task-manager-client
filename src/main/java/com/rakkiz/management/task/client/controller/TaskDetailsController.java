package com.rakkiz.management.task.client.controller;

import com.rakkiz.management.task.client.TaskManagerApplication;
import com.rakkiz.management.task.client.data.DerbyTaskRepository;
import com.rakkiz.management.task.client.data.model.Task;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ResourceBundle;

public class TaskDetailsController implements Initializable {

    @FXML
    private TextField title;
    @FXML
    private TextArea description;
    @FXML
    private HBox filters;
    @FXML
    private Button pomodoroButton;
    private Task task;
    private Stage stage;
    private AnchorPane scaffoldContent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        title.setTextFormatter(new TextFormatter<>(change -> change.getControlNewText().length() > Task.NAME_LEN_MAX ? null : change));
        description.setTextFormatter(new TextFormatter<>(change -> change.getControlNewText().length() > Task.DESC_LEN_MAX ? null : change));
    }

    /**
     * Save task data when popUp closes
     *
     * @param stage popup stage
     */
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

    public void setAnchorPane(AnchorPane anchorPane) {
        this.scaffoldContent = anchorPane;
    }

    /**
     * Initial information of the task details
     *
     * @param task Model for setting values
     */
    public void setTaskDetails(Task task) {
        this.task = task;
        title.setText(task.getName());
        description.setText(task.getDescription());
        pomodoroButton.setDisable(task.getDuration() == 0);
    }

    /**
     * Add the input details for the task
     */
    public void addDetails() {
        try {
            addDetailInputs("Duration");
            addDetailInputs("Type");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void addDetailInputs(String type) throws IOException {
        FXMLLoader loader = new FXMLLoader(TaskManagerApplication.class.getResource("fxml/details.fxml"));
        Node node = loader.load();
        DetailFilterController detailFilterController = loader.getController();
        if (type.equals("Duration")) {
            detailFilterController.durationDetail(task.getDuration());
        } else if (type.equals("Type")) detailFilterController.typeDetail(task.getScheduledTime());
        filters.getChildren().add(node);
    }

    /**
     * Update task data
     *
     * @throws SQLException when accessing database
     */
    private void saveData() throws SQLException {
        task.setName(title.getText());
        task.setDescription(description.getText());

        ObservableList<Node> nodes = filters.getChildren();
        for (Node node : nodes) {
            HBox hbox = (HBox) node;
            Node lastChild = hbox.getChildren().get(hbox.getChildren().size() - 1);
            if (lastChild instanceof TextField) {
                int duration = Integer.parseInt(((TextField) lastChild).getText());
                task.setDuration(duration);
            } else if (lastChild instanceof DatePicker) {
                Instant date = ((DatePicker) lastChild).getValue().atStartOfDay(ZoneId.systemDefault()).toInstant();
                task.setScheduleTime(date);
            } else {
                task.setScheduleTime(null);
            }
        }

        DerbyTaskRepository taskRepository = DerbyTaskRepository.getInstance();
        taskRepository.update(this.task);
    }

    /**
     * Going back button
     *
     * @throws SQLException when saving data
     */
    @FXML
    private void goBack() throws SQLException {
        saveData();
        stage.close();
    }

    /**
     * Button to go to pomodoro
     *
     * @throws IOException  when changing to pomodoro
     * @throws SQLException when saving data
     */
    @FXML
    private void onPomodoroClick() throws IOException, SQLException {
        saveData();
        changeToPomodoro();
        stage.close();
    }

    public void changeToPomodoro() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(TaskManagerApplication.class.getResource("fxml/pomodoro.fxml"));
        Node root = fxmlLoader.load();

        PomodoroController pomodoroController = fxmlLoader.getController();
        pomodoroController.setTitle(task.getName());
        pomodoroController.setDescription(task.getDescription());
        pomodoroController.setTask(task);

        pomodoroController.addPomTimer(task.getDuration());

        Stage stage = (Stage) scaffoldContent.getScene().getWindow();
        ObservableList<Node> list = scaffoldContent.getChildren();
        list.remove(0);
        list.add(0, root);
        stage.show();
    }
}
