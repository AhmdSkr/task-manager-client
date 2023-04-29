package com.rakkiz.taskmanagerclient.view.strategy.duration;

import com.rakkiz.taskmanagerclient.data.model.Task;
import com.rakkiz.taskmanagerclient.view.strategy.duration.DurationTaskFilter;

public class MediumDurationTaskFilter extends DurationTaskFilter {
    @Override
    public boolean filter(Task task) {
        if(task.getDuration()>5 && task.getDuration()<15) return true;
        return false;
    }
}
