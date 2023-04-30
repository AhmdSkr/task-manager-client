package com.rakkiz.taskmanagerclient.controller;

import com.rakkiz.taskmanagerclient.TaskManagerApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import javafx.scene.media.*;
import com.rakkiz.taskmanagerclient.data.model.PomodoroTimerModel;
import java.sql.SQLException;
import java.util.Objects;

public class PomodoroTimerController {
    @FXML
    private Label time;

    @FXML
    private Label pomCycles;

    @FXML
    private VBox Vbox;

    @FXML
    private ToggleButton SP;

    private Media sound;



    PomodoroTimerModel model = new PomodoroTimerModel();
    PomodoroController pomodoroController;

    String timeString;

    /**
     * sets the cycleCount in the timer model according to the task given
     * @param duration duration of task (number of cycles)
     */
    public void setOriginalCycle(int duration){
        model.setCycleCount(duration);
    }

    public void setPomodoroController(PomodoroController pomodoroController){
        this.pomodoroController = pomodoroController;
    }

    @FXML
    public void initialize() {
        sound = new Media(Objects.requireNonNull(TaskManagerApplication.class.getResource("sounds/sound.wav")).toString());
        model.setWorkState();
        time.setText(formatTime(model.getOriginalWorkTimeInSeconds()));
        Vbox.setStyle("-fx-background-color: rgba(248, 196, 200, 0.5); -fx-background-radius: 50%;");
        pomCycles.setText(""+model.getCycleCount());


        /*
         * Listener for workTimeSeconds
         * on every change to the work time the time FXML is changed accordingly
         * and when the work time hits 0 a sound is played notifying the user
         * VBox color is changed
         */
        model.workTimeInSecondsProperty().addListener((obs, oldCount, newCount) -> {
            timeString = formatTime(newCount.intValue());
            time.setText(timeString);
            if (newCount.intValue() == 0){
                playSound();
                Vbox.setStyle("-fx-background-color: rgba(199, 215, 226, 0.5); -fx-background-radius: 50%;");
                if(model.getCycleCount()!=1) {
                    time.setText("" + formatTime(model.getOriginalBreakTimeInSeconds()));
                }
            }
        });


        /*
         * Listener for breakTimeSeconds
         * on every change to the break time the time FXML is changed accordingly
         * and when the work time hits 0 a sound is played notifying the user
         * VBox color is changed
         */
        model.breakTimeInSecondsProperty().addListener((obs, oldCount, newCount) -> {
            timeString = formatTime(newCount.intValue());
            time.setText(timeString);
            if (newCount.intValue() == 0){
                playSound();
                Vbox.setStyle("-fx-background-color: rgba(248, 196, 200, 0.5); -fx-background-radius: 50%;");
            }
        });


        /*
         * Listener for cycleCount
         * on every change to the cycleCount the time FXML is changed accordingly
         * and when the cycleCount hits 0 the button is disabled and hidden
         * VBox color is changed
         * "DONE!" is displayed instead of the timer
         */
        model.cycleCountProperty().addListener((obs, oldCount, newCount) ->{
            pomCycles.setText(""+newCount.intValue());
            try {
                if(pomodoroController != null) pomodoroController.updateTaskDuration(newCount.intValue());
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

        /*
         * changes function and button text based on if the timer is running or stopped
         */
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

    /**
     * plays as sound
     */
    public void playSound() {
        MediaPlayer player = new MediaPlayer(sound);
        player.setVolume(0.5);
        player.play();
    }


    /**
     * starts timer according to current state
     */
    @FXML
    private void startTimer() {
        switch (model.getCurrentState()) {
            case WORK -> model.startWorkTimer();
            case BREAK -> model.startBreakTimer();
        }
    }

    /**
     * stops timer according to current state
     */
    @FXML
    private void stopTimer() {
        switch (model.getCurrentState()) {
            case WORK -> model.stopWorkTimer();
            case BREAK -> model.stopBreakTimer();
        }
    }


    /**
     * returns the work or break timer in the mm:ss format
     *
     * @param timeInSeconds time that needs to be formatted
     * @return returns a string in a mm:ss format
     */
    private String formatTime(int timeInSeconds) {
        int minutes = timeInSeconds / 60;
        int seconds = timeInSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
