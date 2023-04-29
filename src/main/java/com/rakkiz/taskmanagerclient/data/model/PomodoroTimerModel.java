package com.rakkiz.taskmanagerclient.data.model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.util.Duration;


public class PomodoroTimerModel{


    private Timeline workTimer;
    private Timeline breakTimer;
    private final int originalWorkTimeInSeconds = 1 * 10; // 25 minutes
    private final int originalBreakTimeInSeconds = 1 * 5; // 5 minutes
    //private int originalCycleCount;


//    public void setOriginalCycleCount(int cycles){
//        originalCycleCount = cycles;
//    }


    //TEST
    private IntegerProperty workTimeInSeconds = new SimpleIntegerProperty(originalWorkTimeInSeconds);



    private IntegerProperty breakTimeInSeconds = new SimpleIntegerProperty(originalBreakTimeInSeconds);



    private IntegerProperty cycleCount = new SimpleIntegerProperty();



    public int getWorkTimeInSeconds() {
        return workTimeInSeconds.get();
    }

    public IntegerProperty workTimeInSecondsProperty() {
        return workTimeInSeconds;
    }


    public int getBreakTimeInSeconds() {
        return breakTimeInSeconds.get();
    }

    public IntegerProperty breakTimeInSecondsProperty() {
        return breakTimeInSeconds;
    }

    public IntegerProperty cycleCountProperty(){
        return cycleCount;
    }


    public void decrementWorkTime() {
        workTimeInSeconds.set(workTimeInSeconds.get() - 1);
    }

    public void decrementBreakTime() {
        breakTimeInSeconds.set(breakTimeInSeconds.get() - 1);
    }

    public void decrementCycleCount() { cycleCount.set(cycleCount.get() - 1);}


    //TEST

    public enum TimerState {WORK, BREAK};
    private TimerState currentState;


    public void setWorkState(){
        currentState = TimerState.WORK;
    }
    public void setBreakState(){
        currentState = TimerState.BREAK;
    }

    public TimerState getCurrentState(){
        return currentState;
    }

    public String getOriginalWorkTimeInSeconds(){
        return formatTime(originalWorkTimeInSeconds);
    }

//    public void setOriginalWorkTimeInSeconds(int seconds){
//        this.workTimeInSeconds = seconds;
//    }


    public String getOriginalBreakTimeInSeconds(){
        return formatTime(originalBreakTimeInSeconds);
    }

//    public void setOriginalBreakTimeInSeconds(int seconds){
//        this.breakTimeInSeconds = seconds;
//    }

    public int getCycleCount(){
        return cycleCount.get();
    }

    public void setCycleCount(int cycle) {cycleCount.set(cycle);}




    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void startWorkTimer() {
        workTimer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            decrementWorkTime();
            if (getWorkTimeInSeconds() == 0) {
                stopWorkTimer();
                resetBreakTimer();
                startBreakTimer();
                //originalCycleCount--;
                decrementCycleCount();
                if (cycleCount.get() == 0) {
                    stopTimers();
                }
            }
        }));
        workTimer.setCycleCount(Timeline.INDEFINITE);
        workTimer.play();
        currentState = TimerState.WORK;

    }

    public void stopWorkTimer() {
        workTimer.stop();
        currentState = TimerState.WORK;
    }

    public void startBreakTimer() {
        breakTimer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            decrementBreakTime();
            if (getBreakTimeInSeconds() == 0) {
                stopBreakTimer();
                resetWorkTimer();
                startWorkTimer();
            }
        }));
        breakTimer.setCycleCount(Timeline.INDEFINITE);
        breakTimer.play();
        currentState = TimerState.BREAK;
    }

    public void stopBreakTimer() {
        breakTimer.stop();
        currentState = TimerState.BREAK;
    }

    private void resetWorkTimer() {
        workTimeInSeconds.set(originalWorkTimeInSeconds);
    }

    private void resetBreakTimer() {
        breakTimeInSeconds.set(originalBreakTimeInSeconds);
    }

    private void stopTimers() {
        if (workTimer != null) {
            workTimer.stop();
        }
        if (breakTimer != null) {
            breakTimer.stop();
        }
        //cycleCount.set(originalCycleCount);
        //resetWorkTimer();
        //resetBreakTimer();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////



//    public Background getBackgroundColor(String color) {
//        switch (color) {
//            case "red":
//                return backgroundRed;
//            //    break;
//            case "blue":
//                return backgroundBlue;
//             //   break;
//            case "green":
//                return backgroundGreen;
//              //  break;
//        }
//        return backgroundRed;
//    }


    private String formatTime(int timeInSeconds) {
        int minutes = timeInSeconds / 60;
        int seconds = timeInSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

}
