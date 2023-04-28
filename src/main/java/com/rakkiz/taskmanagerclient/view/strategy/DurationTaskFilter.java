package com.rakkiz.taskmanagerclient.view.strategy;

import com.rakkiz.taskmanagerclient.data.model.Task;

public class DurationTaskFilter implements TaskFilter {

    private int duration;

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public boolean filter(Task task) {
        return task.getDuration().equals(this.duration);
    }
}
