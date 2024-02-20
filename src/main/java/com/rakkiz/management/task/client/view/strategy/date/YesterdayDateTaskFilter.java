package com.rakkiz.management.task.client.view.strategy.date;

import com.rakkiz.management.task.client.data.model.Task;

import java.time.LocalDate;
import java.time.ZoneId;

public class YesterdayDateTaskFilter extends DateTaskFilter {

    /**
     * Scheduled date should be yesterday
     *
     * @param task Model for reference
     * @return boolean
     */
    @Override
    public boolean filter(Task task) {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        return task.getScheduledTime().atZone(ZoneId.systemDefault()).toLocalDate().isEqual(yesterday);
    }
}
