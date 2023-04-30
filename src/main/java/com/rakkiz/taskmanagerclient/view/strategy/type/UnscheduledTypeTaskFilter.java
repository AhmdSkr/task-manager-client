package com.rakkiz.taskmanagerclient.view.strategy.type;

import com.rakkiz.taskmanagerclient.data.model.Task;

public class UnscheduledTypeTaskFilter extends TypeTaskFilter {

    /**
     * Task should be unscheduled
     *
     * @param task Model for reference
     * @return boolean
     */
    @Override
    public boolean filter(Task task) {
        return !task.isScheduled();
    }
}
