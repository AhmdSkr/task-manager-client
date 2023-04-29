package com.rakkiz.taskmanagerclient.view.strategy.duration;

import com.rakkiz.taskmanagerclient.data.model.Task;

public class LongDurationTaskFilter extends DurationTaskFilter {
    @Override
    public boolean filter(Task task) {
        if(task.getDuration()>15) return true;
        else return false;
    }
}
