package com.rakkiz.taskmanagerclient.data.model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.util.Duration;


public class PomodoroTimerModel{


    private Timeline workTimer;
    private Timeline breakTimer;
    private final int originalWorkTimeInSeconds = 25 * 60; // 25 minutes
    private final int originalBreakTimeInSeconds = 5 * 60; // 5 minutes



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



    public enum TimerState {WORK, BREAK}
    private TimerState currentState;


    public void setWorkState(){
        currentState = TimerState.WORK;
    }

    public TimerState getCurrentState(){
        return currentState;
    }

    public int getOriginalWorkTimeInSeconds(){
        return originalWorkTimeInSeconds;
    }


    public int getOriginalBreakTimeInSeconds(){
        return originalBreakTimeInSeconds;
    }


    public int getCycleCount(){
        return cycleCount.get();
    }

    public void setCycleCount(int cycle) {cycleCount.set(cycle);}


    /**
     * Starts the work timer
     * every second it decrements the work time
     * when the work time hits 0 the work timer stops and calls the
     * startBreakTimer() to start the break Timer
     * and decrements the cycle count (duration of the task).
     * if cycleCount hits 0 all the timers are stopped.
     */
    public void startWorkTimer() {
        workTimer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            decrementWorkTime();
            if (getWorkTimeInSeconds() == 0) {
                stopWorkTimer();
                resetBreakTimer();
                startBreakTimer();
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

    /**
     * Stops the work Timer
     */
    public void stopWorkTimer() {
        workTimer.stop();
        currentState = TimerState.WORK;
    }

    /**
     * Starts the break Timer
     * decrements the break timer every second
     * when the work time hits 0 the break timer stops and calls the
     * startWorkTimer() to start the work Timer
     */
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

    /**
     * stops break timer
     */
    public void stopBreakTimer() {
        breakTimer.stop();
        currentState = TimerState.BREAK;
    }

    /**
     * resets work timer to initial time 25 minutes
     */
    private void resetWorkTimer() {
        workTimeInSeconds.set(originalWorkTimeInSeconds);
    }

    /**
     * resets break timer to initial time 5 minutes
     */
    private void resetBreakTimer() {
        breakTimeInSeconds.set(originalBreakTimeInSeconds);
    }

    /**
     * stops the timer that is running
     */
    private void stopTimers() {
        if (workTimer != null) {
            workTimer.stop();
        }
        if (breakTimer != null) {
            breakTimer.stop();
        }
    }

    /**
     * returns the work or break timer in the mm:ss format
     *
     * @param timeInSeconds
     * @return
     */

}
