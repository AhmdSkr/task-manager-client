package com.rakkiz.management.task.client.view.strategy;

import com.rakkiz.management.task.client.data.model.Task;

public interface TaskFilter {

    /**
     * Checks if a task meets the condition of a filter
     *
     * @param task Model for reference
     * @return boolean
     */
    boolean filter(Task task);
}
