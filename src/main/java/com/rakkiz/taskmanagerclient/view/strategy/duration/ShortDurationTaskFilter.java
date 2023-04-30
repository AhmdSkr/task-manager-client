package com.rakkiz.taskmanagerclient.view.strategy.duration;

import com.rakkiz.taskmanagerclient.data.model.Task;


public class ShortDurationTaskFilter extends DurationTaskFilter {

    /**
     * Task duration should be less than 10 cycles
     *
     * @param task Model for reference
     * @return boolean
     */
    @Override
    public boolean filter(Task task) {
        return task.getDuration() <= 10;
    }
}
