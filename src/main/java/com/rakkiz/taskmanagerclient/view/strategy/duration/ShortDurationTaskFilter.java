package com.rakkiz.taskmanagerclient.view.strategy.duration;

import com.rakkiz.taskmanagerclient.data.model.Task;


public class ShortDurationTaskFilter extends DurationTaskFilter {

    @Override
    public boolean filter(Task task) {
        return task.getDuration() < 5;
    }
}
