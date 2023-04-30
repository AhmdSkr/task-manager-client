package com.rakkiz.taskmanagerclient.view.strategy;

import com.rakkiz.taskmanagerclient.data.model.Task;

public interface TaskFilter {

    /**
     * Checks if a task meets the condition of a filter
     *
     * @param task Model for reference
     * @return boolean
     */
    boolean filter(Task task);
}
