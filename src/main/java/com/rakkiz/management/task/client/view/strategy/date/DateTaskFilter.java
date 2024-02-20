package com.rakkiz.management.task.client.view.strategy.date;

import com.rakkiz.management.task.client.data.model.Task;
import com.rakkiz.management.task.client.view.strategy.TaskFilter;

public class DateTaskFilter implements TaskFilter {

    /**
     * Scheduled date can be any value
     *
     * @param task Model for reference
     * @return boolean
     */
    @Override
    public boolean filter(Task task) {
        return true;
    }
}
