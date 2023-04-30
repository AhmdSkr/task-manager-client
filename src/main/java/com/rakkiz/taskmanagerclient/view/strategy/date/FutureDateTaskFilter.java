package com.rakkiz.taskmanagerclient.view.strategy.date;

import com.rakkiz.taskmanagerclient.data.model.Task;

import java.time.LocalDate;
import java.time.ZoneId;

public class FutureDateTaskFilter extends DateTaskFilter {

    /**
     * Scheduled date should be greater than tomorrow
     *
     * @param task Model for reference
     * @return boolean
     */
    @Override
    public boolean filter(Task task) {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        return task.getScheduledTime().atZone(ZoneId.systemDefault()).toLocalDate().isAfter(tomorrow);
    }
}
