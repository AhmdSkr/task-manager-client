package com.rakkiz.taskmanagerclient.view.strategy.duration;

import com.rakkiz.taskmanagerclient.data.model.Task;

public class LongDurationTaskFilter extends DurationTaskFilter {

    /**
     * Task duration should be greater than 20 cycles
     *
     * @param task Model for reference
     * @return boolean
     */
    @Override
    public boolean filter(Task task) {
        return task.getDuration() > 20;
    }
}
