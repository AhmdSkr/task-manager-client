package com.rakkiz.management.task.client.view.strategy.date;

import com.rakkiz.management.task.client.data.model.Task;

import java.time.LocalDate;
import java.time.ZoneId;

public class TodayDateTaskFilter extends DateTaskFilter {

    /**
     * Scheduled date should be today
     *
     * @param task Model for reference
     * @return boolean
     */
    @Override
    public boolean filter(Task task) {
        LocalDate today = LocalDate.now();
        return task.getScheduledTime().atZone(ZoneId.systemDefault()).toLocalDate().isEqual(today);
    }
}
