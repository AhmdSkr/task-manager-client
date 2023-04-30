package com.rakkiz.taskmanagerclient.view.strategy.duration;

import com.rakkiz.taskmanagerclient.data.model.Task;

public class MediumDurationTaskFilter extends DurationTaskFilter {

    /**
     * Task duration should be between 10 & 20 cycles
     *
     * @param task Model for reference
     * @return boolean
     */
    @Override
    public boolean filter(Task task) {
        return task.getDuration() > 10 && task.getDuration() <= 20;
    }
}
