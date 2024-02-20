package com.rakkiz.management.task.client.view.strategy.date;

import com.rakkiz.management.task.client.data.model.Task;

import java.time.LocalDate;
import java.time.ZoneId;

public class PastDateTaskFilter extends DateTaskFilter {

    /**
     * Scheduled date should be less than yesterday
     *
     * @param task Model for reference
     * @return boolean
     */
    @Override
    public boolean filter(Task task) {
        LocalDate yesterdayDate = LocalDate.now().minusDays(1);
        return task.getScheduledTime().atZone(ZoneId.systemDefault()).toLocalDate().isBefore(yesterdayDate);
    }
}
