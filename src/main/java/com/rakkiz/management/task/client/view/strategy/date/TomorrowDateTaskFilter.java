package com.rakkiz.management.task.client.view.strategy.date;

import com.rakkiz.management.task.client.data.model.Task;

import java.time.LocalDate;
import java.time.ZoneId;

public class TomorrowDateTaskFilter extends DateTaskFilter {

    /**
     * Scheduled date should be tomorrow
     *
     * @param task Model for reference
     * @return boolean
     */
    @Override
    public boolean filter(Task task) {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        return task.getScheduledTime().atZone(ZoneId.systemDefault()).toLocalDate().isEqual(tomorrow);
    }
}
