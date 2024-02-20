package com.rakkiz.management.task.client.view.strategy.duration;

import com.rakkiz.management.task.client.data.model.Task;
import com.rakkiz.management.task.client.view.strategy.TaskFilter;

public class DurationTaskFilter implements TaskFilter {

    /**
     * Task duration can be any value
     *
     * @param task Model for reference
     * @return boolean
     */
    @Override
    public boolean filter(Task task) {
        return true;
    }
}
