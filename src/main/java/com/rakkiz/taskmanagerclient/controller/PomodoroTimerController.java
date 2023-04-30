package com.rakkiz.taskmanagerclient.controller;

import com.rakkiz.taskmanagerclient.TaskManagerApplication;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.media.*;
import com.rakkiz.taskmanagerclient.data.model.PomodoroTimerModel;

import java.io.File;
import java.sql.SQLException;

public class PomodoroTimerController {
    @FXML
    private Label time;

    @FXML
    private Label pomCycles;

    @FXML
    private VBox Vbox;

    @FXML
    private ToggleButton SP;

    //private AudioClip sound;
    Media sound;



    PomodoroTimerModel model = new PomodoroTimerModel();
    PomodoroController pomodoroController;

    String timeString;

    public void setOriginalCycle(int duration){
        //model.setOriginalCycleCount(duration);
        model.setCycleCount(duration);
    }

    public void setPomodoroController(PomodoroController pomodoroController){
        this.pomodoroController = pomodoroController;
    }

    @FXML
    public void initialize() {
        //model = new PomodoroTimerModel();
        sound = new Media(TaskManagerApplication.class.getResource("sounds/sound.wav").toString());
        model.setWorkState();
        time.setText(model.getOriginalWorkTimeInSeconds());
        Vbox.setStyle("-fx-background-color: rgba(248, 196, 200, 0.5); -fx-background-radius: 50%;");
        pomCycles.setText(""+model.getCycleCount());

        // Listener for workTimeSeconds
        model.workTimeInSecondsProperty().addListener((obs, oldCount, newCount) -> {
            int minutes = newCount.intValue() / 60;
            int seconds = newCount.intValue() % 60;
            timeString= String.format("%02d:%02d", minutes, seconds);
            time.setText(timeString);
            if (newCount.intValue() == 0){
                playSound();
                Vbox.setStyle("-fx-background-color: rgba(199, 215, 226, 0.5); -fx-background-radius: 50%;");
                if(model.getCycleCount()!=1) {
                    time.setText("" + model.getOriginalBreakTimeInSeconds());
                }
            }
        });
        // Listener for breakTimeSeconds
        model.breakTimeInSecondsProperty().addListener((obs, oldCount, newCount) -> {
            int minutes = newCount.intValue() / 60;
            int seconds = newCount.intValue() % 60;
            timeString= String.format("%02d:%02d", minutes, seconds);
            time.setText(timeString);
            if (newCount.intValue() == 0){
                playSound();
                Vbox.setStyle("-fx-background-color: rgba(248, 196, 200, 0.5); -fx-background-radius: 50%;");
            }
        });
        // Listener for cycleCount
        model.cycleCountProperty().addListener((obs, oldCount, newCount) ->{
            pomCycles.setText(""+newCount.intValue());
            System.out.println("THE NEW DURATION: "+newCount.intValue());
            try {
                pomodoroController.updateTaskDuration(newCount.intValue());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            if(newCount.intValue() == 0){
                Vbox.setStyle("-fx-background-color: rgba(188, 226, 186, 0.5); -fx-background-radius: 50%;");
                time.setText("DONE!");
                SP.setDisable(true);
                SP.setVisible(false);
            }
        });

        SP.setOnAction(event -> {
            if(SP.isSelected()){
                startTimer();
                SP.setText("pause");
            }else {
                stopTimer();
                SP.setText("start");
            }
        });

    }

    public void playSound() {
        MediaPlayer player = new MediaPlayer(sound);
        player.setVolume(0.5);
        player.play();
    }


    @FXML
    private void startTimer() {
        switch (model.getCurrentState()) {
            case WORK -> model.startWorkTimer();
            case BREAK -> model.startBreakTimer();
        }
    }

    @FXML
    private void stopTimer() {
        switch (model.getCurrentState()) {
            case WORK -> model.stopWorkTimer();
            case BREAK -> model.stopBreakTimer();
        }
    }
}
